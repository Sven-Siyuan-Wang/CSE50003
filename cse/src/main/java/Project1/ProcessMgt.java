package Project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by WSY on 25/2/16.
 */
public class ProcessMgt {

    public final static String directory = "/Users/WangSiyuan/Dropbox/Term5/50.005-CSE/OS-Project/SampleInput/";
    public final static String fileName = "testproc.txt";
    public final int INELIGIBLE = 0;
    public final int READY = 1;
    public final int RUNNING = 2;
    public final int FINISHED = 3;
    public static ArrayList<Node> nodes;

    public static void main(String[] args) throws IOException, InterruptedException {

        nodes = parseNodes();
        // tranverse all nodes and determine their parents

        for(int i=0; i<nodes.size(); i++){
            for(int childID: nodes.get(i).getChildren()){
                System.out.println(i+"->"+childID);
                nodes.get(childID).addParent(i);

            }
        }

        while(true){
            // check if there is unfinished node to run
            boolean allDone = true;
            for(Node node: nodes){
                if(!node.finished()) {
                    allDone = false;
                    break;
                }
            }
            if(allDone) break;

            ArrayList<Node> runnables = new ArrayList<>();
            for(Node node: nodes)
                if(node.runnable()) {
                    runnables.add(node);
                    //System.out.println("A runnable");
            }

            ProcessThread[] processes = new ProcessThread[runnables.size()];

            for(int i=0; i<runnables.size(); i++){
                ProcessThread pt = new ProcessThread(runnables.get(i));
                processes[i] = pt;
                pt.start();
            }
            for(ProcessThread pt: processes){
                pt.join();

            }

            for(Node node: runnables){
                node.setStatus(3);
            }


        }

    }

    private static ArrayList<Node> parseNodes() throws IOException {
        File input = new File(directory+fileName);
        FileInputStream fis = new FileInputStream(input);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        ArrayList<Node> nodes = new ArrayList<>();
        int id = 0;
        while(br.ready()) {
            String line = br.readLine();
            String[] cmds = line.split(":");
            String prog = cmds[0].trim();

            String inputFile = cmds[2].trim();
            String outputFile = cmds[3].trim();


            int[] childIds = {};
            if(!cmds[1].equals("none")){
                String children[] = cmds[1].split(" ");
                childIds = new int[children.length];
                int i=0;
                for(String child: children){
                    childIds[i++] = Integer.parseInt(child);
                }
            }

            nodes.add(new Node(id++, prog, childIds, inputFile, outputFile));



        }


        return nodes;
    }

}


class ProcessThread extends Thread{
    private Node node;
    public ProcessThread(Node node){
        this.node = node;

    }

    public void run() {
        ProcessBuilder pb = new ProcessBuilder(node.getProg().split(" "));

        pb.directory(new File(ProcessMgt.directory));
        if(!node.getInput().equals("stdin")) {
            File input = new File(ProcessMgt.directory+node.getInput());
            pb.redirectInput(input);
        }
        if(!node.getOutput().equals("stdout")) {
            File output = new File(ProcessMgt.directory+node.getOutput());
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pb.redirectOutput(output);
        }
        Process p = null;
        try {
            p = pb.start();
            System.out.println("Node "+node.getId()+" started.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
