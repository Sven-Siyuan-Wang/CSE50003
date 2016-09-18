package Project1;

import java.util.ArrayList;

/**
 * Created by WSY on 23/2/16.
 */
public class Node {
    private int id;
    private String prog;
    private String input;
    private String output;
    private int[] children;
    private ArrayList<Integer> parents;
    private int num_children;
    private int status;
    public final int INELIGIBLE = 0;
    public final int READY = 1;
    public final int RUNNING = 2;
    public final int FINISHED = 3;


    public Node(int id, String prog,int[] children ,String input, String output){
        this.id = id;
        this.prog = prog;
        this.input = input;  // can be : filename, stdin
        this.output = output; // can be: filename, stdout
        this.children = children; // can be: 1 2, "none"
        status = INELIGIBLE;
        parents = new ArrayList<>();

//        if(!input.equals("stdin")) this.prog = prog+" "+input;
//        if(!output.equals("stdout")) this.prog += " > "+output;

    }

    public int getId() {
        return id;
    }

    public String getProg() {
        return prog;
    }

    public String getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }

    public int[] getChildren() {
        return children;
    }

    public int getNum_children() {
        return num_children;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public void addParent(int pid){
        parents.add(pid);
    }

    public ArrayList<Integer> getParents(){
        return parents;
    }

    public boolean runnable(){
        if(status==FINISHED) return false;
        if(status==READY) return true;
        for(int i: parents){
            if(!ProcessMgt.nodes.get(i).finished()) return false;
        }
        return true;
    }

    public boolean finished(){
        return status==FINISHED;
    }

}
