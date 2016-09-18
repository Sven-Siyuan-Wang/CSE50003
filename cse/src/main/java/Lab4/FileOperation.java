package Lab4;

/**
 * Created by WSY on 24/2/16.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class FileOperation {
    private static File currentDirectory = new File(System.getProperty("user.dir"));
    public static void main(String[] args) throws java.io.IOException {

        String commandLine;

        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        while (true) {
            // read what the user entered
            System.out.print("jsh>");
            commandLine = console.readLine();

            // clear the space before and after the command line
            commandLine = commandLine.trim();

            // if the user entered a return, just loop again
            if (commandLine.equals("")) {
                continue;
            }
            // if exit or quit
            else if (commandLine.equalsIgnoreCase("exit") | commandLine.equalsIgnoreCase("quit")) {
                System.exit(0);
            }

            // check the command line, separate the words
            String[] commandStr = commandLine.split(" ");
            ArrayList<String> command = new ArrayList<String>();
            for (int i = 0; i < commandStr.length; i++) {
                command.add(commandStr[i]);
            }
            String action = command.get(0);

            // TODO: implement code to handle create here
            if(action.equals("create")){

                Java_create(currentDirectory,command.get(1));
            }

            // TODO: implement code to handle delete here\
            else if(action.equals("delete")) {

                Java_delete(currentDirectory,command.get(1));
            }

            // TODO: implement code to handle display here
            else if(action.equals("display")) Java_cat(currentDirectory,command.get(1));

            // TODO: implement code to handle list here
            else if(action.equals("list")){
                if(command.size()==1) Java_ls(currentDirectory,"none","none");
                else if(command.size()==2){
                    if(command.get(1).equals("property")) Java_ls(currentDirectory,"property","none");
                    else System.out.println("Invalid command. Try again");
                }
                else if(command.size()==3){
                    if(command.get(1).equals("property")){
                        if(command.get(2).equals("name") ||command.get(2).equals("size") || command.get(2).equals("time")){
                            Java_ls(currentDirectory,"property",command.get(2));
                        }

                    }
                    else System.out.println("The second word should be property.");

                }

            }

            // TODO: implement code to handle find here
            else if(command.get(0).equals("find")){
                System.out.println("Found: " + Java_find(currentDirectory, command.get(1)));
            }

            // TODO: implement code to handle tree here

            else if(action.equals("tree")){
                if(command.size()==1){
                    Java_tree(currentDirectory,10,"none");
                }
                else if(command.size()==2){
                    Java_tree(currentDirectory,Integer.parseInt(command.get(1)),"none");
                }
                else if(command.size()==3){
                    if(command.get(2).equals("name") ||command.get(2).equals("size") || command.get(2).equals("time")){
                        Java_tree(currentDirectory,Integer.parseInt(command.get(1)),command.get(2));
                     }
                }
            }

            // other commands
            else{
                ProcessBuilder pBuilder = new ProcessBuilder(command);
                pBuilder.directory(currentDirectory);
                try{
                    Process process = pBuilder.start();
                    // obtain the input stream
                    InputStream is = process.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    // read what is returned by the command
                    String line;
                    while ( (line = br.readLine()) != null)
                        System.out.println(line);

                    // close BufferedReader
                    br.close();
                }
                // catch the IOexception and resume waiting for commands
                catch (IOException ex){
                    System.out.println(ex);
                    continue;
                }
            }

        }
    }

    /**
     * Create a file
     * @param dir - current working directory
     * @param command - name of the file to be created
     */
    public static void Java_create(File dir, String name) throws IOException {
        // TODO: create a file
        File file = new File(dir, name);
        file.createNewFile();
    }

    /**
     * Delete a file
     * @param dir - current working directory
     * @param name - name of the file to be deleted
     */
    public static void Java_delete(File dir, String name) {
        // TODO: delete a file
        File file = new File(dir, name);
        if(file.exists()) file.delete();
        else System.out.println("This file does not exist.");
    }

    /**
     * Display the file
     * @param dir - current working directory
     * @param name - name of the file to be displayed
     */
    public static void Java_cat(File dir, String name) throws IOException {
        // TODO: display a file
        File file = new File(dir, name);
        FileReader fileReader = new FileReader(file);
        BufferedReader in = new BufferedReader(fileReader); String line;
        while((line = in.readLine())!= null){
            System.out.println(line);
        }
        in.close();
    }

    /**
     * Function to sort the file list
     * @param list - file list to be sorted
     * @param sort_method - control the sort type
     * @return sorted list - the sorted file list
     */
    private static File[] sortFileList(File[] list, String sort_method) {
        // sort the file list based on sort_method
        // if sort based on name
        if (sort_method.equalsIgnoreCase("name")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return (f1.getName()).compareTo(f2.getName());
                }
            });
        }
        else if (sort_method.equalsIgnoreCase("size")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.length()).compareTo(f2.length());
                }
            });
        }
        else if (sort_method.equalsIgnoreCase("time")) {
            Arrays.sort(list, new Comparator<File>() {
                public int compare(File f1, File f2) {
                    return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
                }
            });
        }
        return list;
    }

    /**
     * List the files under directory
     * @param dir - current directory
     * @param function - control the list type
     * @param sort_method - control the sort type
     */
    public static void Java_ls(File dir, String display_method, String sort_method) {
        // TODO: list files

        File[] list = dir.listFiles();
        if(!sort_method.equals("none")){
            list = sortFileList(list,sort_method);
        }
        for(File file: list){
            if(display_method.equals("property"))
                System.out.printf("Name: %-20s Size: %-10s Last Modified:  %-10s\n" ,file.getName(), file.length(), new Date(file.lastModified()));
            else System.out.println(file.getName());
        }


    }

    /**
     * Find files based on input string
     * @param dir - current working directory
     * @param name - input string to find in file's name
     * @return flag - whether the input string is found in this directory and its subdirectories
     */
    public static boolean Java_find(File dir, String name) {
        boolean flag = false;

        int found = recur_find(dir,name);
        if(found>0) flag = true;

        return flag;
    }

    public static int recur_find(File dir, String name){
        // TODO: find files
        int count = 0;
        if(dir.isDirectory()){
            File[] subDirs = dir.listFiles();
            for(File subDir: subDirs){
                count += recur_find(subDir,name);
            }
        }
        else{
            if(dir.getName().contains(name)) {
                System.out.println(dir.getAbsolutePath());
                count = 1;
            }

        }
        return count;
    }
    /**
     * Print file structure under current directory in a tree structure
     * @param dir - current working directory
     * @param depth - maximum sub-level file to be displayed
     * @param sort_method - control the sort type
     */
    public static void Java_tree(File dir, int depth, String sort_method) {
        // TODO: print file tree
        File[] subDir = sortFileList(dir.listFiles(), sort_method);
        for(File file: subDir){
            System.out.print(recurTree(file, 0, depth, sort_method));
        }

    }

    public static String recurTree(File dir, int indent, int depth, String sort_method){
        if(indent+1==depth || !dir.isDirectory()){
            if(indent==0) return dir.getName()+"\n";
            return spacing(indent)+"|-"+dir.getName()+"\n";
        }
        else{
            File[] subDirs = sortFileList(dir.listFiles(),sort_method);
            String tree;
            if(indent==0) tree = dir.getName()+"\n";
            else tree = spacing(indent)+"|-"+dir.getName()+"\n";

            for(File file: subDirs){
                tree += recurTree(file, indent + 1, depth, sort_method);
            }

            return tree;
        }



    }

    public static String spacing(int n){
        String space = "";
        for(int i=0; i<n; i++) space += "  ";
        return space;
    }

    // TODO: define other functions if necessary for the above functions

}
