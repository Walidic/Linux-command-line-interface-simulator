import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.Scanner;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;
import java.nio.file.StandardOpenOption;
import java.nio.file.StandardCopyOption;

class Parser {
    String commandName;
    String[] args;

    public boolean parse(String input) {
        String[] ParsedData = input.split(" ");
        if (ParsedData.length > 0) {
            commandName = ParsedData[0];
            if (ParsedData.length > 1) {
                int size = ParsedData.length;
                args = new String[size - 1];
                int j = 0;
                for (int i = 1; i < size; i++) {
                    args[j] = ParsedData[i];
                    j++;
                }
            }
        }
        return true;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public void clear() {
        commandName = "";
        args = null;
    }
}

public class Terminal {
    static Parser parser = new Parser();
    String output = new String();
    Path path = Paths.get("E:\\linux_file_system");

    // Functions
    // 1-Echo
    public void echo(String[] input) {
        int option = 0;
        int filePathindex = 0;
        if (input == null || input.length > 0) {// check input is not null
            for(int i = 0; i < input.length; i++) {
                if (input[i].equals(">")){
                    option = 1;
                    filePathindex = i+1;
                }
                else if(input[i].equals(">>")]){
                    option = 2;
                    filePathindex = i+1;
                }
            }
            if(option==0){
                int size = input.length;
                output = input[0];
                for (int i = 1; i < size; i++) {
                    output = output + " " + input[i];
                }
                System.out.println(output);
            }
            else if (option==1){
                int size = input.length;
                output = input[0];
                for (int i = 1; i < size; i++) {
                    output = output + " " + input[i];
                }
                overwriteFileWithOutput(output, input[filePathindex]);
            }
            else if(option ==2){
                int size = input.length;
                output = input[0];
                for (int i = 1; i < size; i++) {
                    output = output + " " + input[i];
                }
                appendFileWithOutput(output, input[filePathindex]);
            }
        } else {
            System.out.println("Please enter an argument");
        }
    }

    // 2-pwd
    public void pwd(String[] input) {
        if (input == null) {
            output = path.toString();
            System.out.println(output);
        } else if (input.length == 2) {
            if (input[0].equals(">")) {
                output = path.toString();
                overwriteFileWithOutput(output, input[1]);
            } else if (input[0].equals(">>")) {
                output = path.toString();
                appendFileWithOutput(output, input[1]);
            } else {
                System.out.println("wrong argument");
            }
        } else {
            System.out.println("wrong number of arguments");
        }
    }

    // 3-cd
    public void cd(String[] input) {
        if (input == null) { // check that there is NO arguments
            path = Paths.get("E:\\linux_file_system\\home"); // default path
        } else if (input.length == 1) {
            if (input[0].equals("..")) { // cd ..
                if (!path.toString().equals("E:\\")) {
                    // .. return to home directory
                    path = path.getParent();
                } else {
                    System.out.println("Reached root");
                }
            } else { // absolute or realtive path
                     // check given path is here
                if (!Paths.get(input[0]).isAbsolute()) {
                    String currentPath = path.toString();
                    String fullPath = currentPath + "\\" + input[0];
                    if (Files.exists(Paths.get(fullPath.toString()))) {
                        path = Paths.get(fullPath.toString());
                    } else {
                        System.out.println("No directory found ");
                    }
                } else // absolute
                {
                    path = Paths.get(input[0]);
                }
            }
        } else {
            System.out.println("Too many arguments");
        }
    }

    // 4-ls & ls-r
    public void ls(String[] input) {
        if (input == null) {
            try (Stream<Path> subPath = Files.walk(path, 1)) {
                subPath.forEach(System.out::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (input.length == 1) {
            Stack<Path> stack = new Stack<Path>();
            if (input[0].equals("-r")) {
                try (Stream<Path> subPath = Files.walk(path, 1)) {
                    subPath.forEach(stack::push);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (!stack.empty()) {
                    System.out.println(stack.pop().toString());
                }
            } else {
                System.out.println("Wrong argument");
            }
        }
        else if (input.length ==2){
            if (input[1].equals(">")){
                List<Path> output = new ArrayList<Path>();
                try (Stream<Path> subPath = Files.walk(path, 1)) {
                    subPath.forEach(output::add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String outputString = "";
                for(Path temp:output){
                    outputString = outputString+temp.toString()+"\n";
                }
                overwriteFileWithOutput(outputString, input[2])
            }
            else if (input[1].equals(">>")){
                List<Path> output = new ArrayList<Path>();
                try (Stream<Path> subPath = Files.walk(path, 1)) {
                    subPath.forEach(output::add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String outputString = "";
                for(Path temp:output){
                    outputString = outputString+temp.toString()+"\n";
                }
                appendFileWithOutput(outputString, input[2]);
            }
            else{
                System.out.println("wrong arguments");
            }
        }
        else if (input.length == 4){
            Stack<Path> stack = new Stack<Path>();
            if (input[0].equals("-r")) {
                if(input[2]==">"){
                    try (Stream<Path> subPath = Files.walk(path, 1)) {
                        subPath.forEach(stack::push);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String output = "";
                    while (!stack.empty()) {
                        output = output+ stack.pop().toString()+"\n";
                    }
                    overwriteFileWithOutput(output, input[3]);
                }
                else if(input[2]==">>"){
                    try (Stream<Path> subPath = Files.walk(path, 1)) {
                        subPath.forEach(stack::push);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String output = "";
                    while (!stack.empty()) {
                        output = output+ stack.pop().toString()+"\n";
                    }
                    appendFileWithOutput(output, input[3]);
                }
                else{
                    System.out.println("wrong argument");
                }
            } else {
                System.out.println("Wrong argument");
            }
        }
        else {
            System.out.println("Too many arguments");
        }
    }

    // 5-mkdir
    public void mkdir(String[] input) {
        // loop on all arguments
        String currentPath = path.toString();
        if (input == null) {// check if there is input
            System.out.println("please pass parameters");
        } else {
            for (int i = 0; i < input.length; i++) {
                // check full or realtive path
                if (Paths.get(input[i]).isAbsolute()) {// full path
                    try {
                        path = Paths.get(input[i]);
                        Files.createDirectory(path);
                    } catch (IOException e) {
                        System.err.println("Failed to create directory!" + e.getMessage());
                    }
                } else { // realitve
                    try {
                        String fullpath = "";
                        fullpath = currentPath + "\\" + input[i];
                        path = Paths.get(fullpath.toString());
                        Files.createDirectory(path);
                    } catch (Exception e) {
                        System.err.println("Failed to create directory!" + e.getMessage());
                    }
                }
            }
        }
    }

    // 6-rmdir
    public void rmdir(String[] input) {
        if (input == null) {
            System.out.println("No arguments given");
        } else if (input.length == 1) {
            if (input[0].equals("*")) {
                try { // sorted to prevent removal of non-empty directory
                    Files.walk(path).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Path destination = Paths.get(input[0]);
                if (destination.isAbsolute()) {
                    if (Files.exists(destination)) {
                        if (destination.getNameCount() == 0) {
                            try {
                                Files.delete(destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("directory is not empty");
                        }
                    }
                    System.out.println("directory doesnt exist");
                } else {
                    destination = Paths.get(path.toString() + "\\" + input[0]);
                    if (Files.exists(destination)) {
                        if (destination.getNameCount() == 0) {
                            try {
                                Files.delete(destination);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            System.out.println("directory is not empty");
                        }
                    } else {
                        System.out.println("directory doesnt exist");
                    }
                }
            }
        } else {
            System.out.println("too many arguments");
        }
    }

    // 7-touch
    public void touch(String[] input) {
        String currentPath = path.toString();
        if (input == null) { // check input is not null
            System.out.println("please pass parameters");
        } else {
            // check full or realtive path
            if (Paths.get(input[0]).isAbsolute()) { // full path
                try {
                    Path current = path; // so it stays in the same path
                    Files.createFile(Paths.get(input[0]));
                    path = current;
                } catch (Exception e) {
                    System.err.println("Failed to create file!" + e.getMessage());
                }
            } else {// realtive path
                try {
                    Path current = path;
                    String fullpath = "";
                    fullpath = currentPath + "\\" + input[0];
                    path = Paths.get(fullpath.toString());
                    Files.createFile(path);
                    path = current;
                } catch (Exception e) {
                    System.err.println("Failed to create file!" + e.getMessage());
                }
            }
        }
    }

    // 8-cp & cp-r
    public void cp(String input[]) {
        if (input == null) { // check if there is parameters
            System.out.println("please pass parameters");
        } else if (input.length > 1) {
            if (input[0].equals("-r")) { // cp-r
                if (Files.exists(Paths.get(input[1]))) {
                    Path src = Paths.get(input[1]);
                    Path dest = Paths.get(input[2]);
                    try {
                        Files.walk(src).forEach(source -> { // walk in each directory
                            try {
                                Files.copy(source, dest.resolve(src.relativize(source)),
                                        StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("cannot copy the directory");
                }
            } else {
                if (Files.exists(Paths.get(input[0]))) { // cp
                    try {
                        Files.copy(Paths.get(input[0]), Paths.get(input[1]));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("cannot copy the file");
                }
            }
        } else {
            System.out.println("Too many arguments");
        }
    }

    // 9-rm
    public void rm(String[] input) {
        if (input == null) {
            System.out.println("no arguments entered");
        } else if (input.length == 1) {
            Path pathTodelete = Paths.get(path.toString() + "\\" + input[0]);
            if (Files.exists(pathTodelete)) {
                try {
                    Files.delete(pathTodelete);
                } catch (IOException e) {
                    System.err.println("File dosn't exist " + e.getMessage());
                }
            } else {
                System.out.println("File doesnt exist");
            }
        } else {
            System.out.println("Too many arguments");
        }
    }

    // 10-cat
    public void cat(String input[]) {
        if (input == null) { // check file exists
            System.out.println("please pass parameters");
        } else if (input.length == 2) { // 2 arguments
            if (Files.exists(Paths.get(input[0]))) {// file exists
                try { // read first file
                    List<String> read1 = Files.readAllLines(Paths.get(input[0]));
                    Files.write(Paths.get(input[1]), read1, StandardOpenOption.APPEND); // write to file
                    List<String> read2 = Files.readAllLines(Paths.get(input[1]));
                    System.out.println(read2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cannot open the file"); // file doesnot exist
            }
        } else if (input.length == 1) {// 1 argument
            if (Files.exists(Paths.get(input[0]))) {
                try {
                    List<String> read = Files.readAllLines(Paths.get(input[0]));// read file
                    System.out.println(read);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Cannot open the file");
            }
        } else {//
            System.out.println("too many arguments");
        }
    }

    // 11- >
    public void overwriteFileWithOutput(String output, String path) {
        Path destination = Paths.get(path);
        if (destination.isAbsolute()) {
            if (Files.exists(destination)) {
                try {
                    Files.write(destination, output.getBytes());
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("given file doesnt exist");
            }
        } else {
            String currentPath = path.toString();
            String fullPath = currentPath + "\\" + path;
            Path finalPath = Paths.get(fullPath);
            if (Files.exists(finalPath)) {
                try {
                    Files.write(finalPath, output.getBytes());
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("given file doesnt exist2");
            }
        }
    }

    // 12- >>
    public void appendFileWithOutput(String output, String path) {
        Path destination = Paths.get(path);
        if (destination.isAbsolute()) {
            if (Files.exists(destination)) {
                try {
                    Files.write(destination, output.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("given file doesnt exist");
            }
        } else {
            String currentPath = path.toString();
            String fullPath = currentPath + "\\" + path;
            Path finalPath = Paths.get(fullPath);
            if (Files.exists(finalPath)) {
                try {
                    Files.write(finalPath, output.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } else {
                System.out.println("given file doesnt exist");
            }
        }
    }

    // command picker
    public void chooseCommandAction(String command) {
        switch (command) {
        case "pwd":
            pwd(parser.getArgs());
            break;
        case "echo":
            echo(parser.getArgs());
            break;
        case "cd":
            cd(parser.getArgs());
            break;
        case "ls":
            ls(parser.getArgs());
            break;
        case "mkdir":
            mkdir(parser.getArgs());
            break;
        case "touch":
            touch(parser.getArgs());
            break;
        case "cp":
            cp(parser.getArgs());
            break;
        case "cat":
            cat(parser.getArgs());
            break;
        case "rm":
            rm(parser.getArgs());
            break;
        case "rmdir":
            rmdir(parser.getArgs());
            break;
        default:
            System.out.println("command not recognized");
            break;
        }
    }

    public static void main(String[] args) throws Exception {
        Terminal terminal = new Terminal();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String enteredCommand = sc.nextLine();
            parser.parse(enteredCommand);
            if (parser.getCommandName().equals("exit") || parser.getCommandName().equals("Exit")) {
                break;
            }
            String command = parser.getCommandName();
            terminal.chooseCommandAction(command);
            parser.clear();
        }
    }
}