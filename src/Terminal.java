import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

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
            // for (int i = 0; i < args.length; i++) {
            // if (args[i].charAt(0) == '"') {
            // for (int j = i + 1; j < args.length - (i + 1); j++) {
            // if (args[j].charAt(args[j].length() - 1) == '"') {
            // args[i] = args[i] + " " + args[j];
            // break;
            // } else {
            // args[i] = args[i] + " " + args[j];p
            // }
            // }
            // } else {
            // System.out.println("no input entered");
            // }
            // }
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

    public void pwd() {
        output = path.toString();
        System.out.println(output);
    }

    public void doubleBo2Samak(String output, String path) {
        Path destination = Paths.get(path);
        System.out.println(destination.toString());
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
                System.out.println("given file doesnt exist2");
            }
        }
    }

    public void bo2samaka(String output, String path) {
        Path destination = Paths.get(path);
        System.out.println(destination.toString());
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

    public void echo(String[] input) {
        if (input == null || input.length > 0) {
            int size = input.length;
            output = input[0];
            for (int i = 1; i < size; i++) {
                output = output + " " + input[i];
            }
            System.out.println(output);
            output = "";

        } else {
            System.out.println("Please enter an argument");
        }
    }

    public void cd(String[] input) {

        if (input == null) { // check that there is NO arguments
            path = Paths.get("E:\\linux_file_system\\home");
        } else if (input.length == 1) {
            if (input[0].equals("..")) {
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

    public void mkdir(String[] input) {
        // loop on all arguments
        String currentPath = path.toString();
        if (input == null) {
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
        } else {
            System.out.println("Too many arguments");
        }
    }

    public void touch(String[] input) {
        String currentPath = path.toString();
        if (input == null) {
            System.out.println("please pass parameters");
        } else {
            // check full or realtive path
            if (Paths.get(input[0]).isAbsolute()) { // full path
                try {
                    Files.createFile(Paths.get(input[0]));
                } catch (Exception e) {
                    System.err.println("Failed to create file!" + e.getMessage());
                }
            } else {// realtive path
                try {
                    String fullpath = "";
                    fullpath = currentPath + "\\" + input[0];
                    path = Paths.get(fullpath.toString());
                    Files.createFile(path);

                } catch (Exception e) {
                    System.err.println("Failed to create file!" + e.getMessage());
                }
            }
        }
    }

public void cp(String input[])
{
    if(input==null)
    {
        System.out.println("please pass parameters");
    }
    else if(input.length>1)
    {
        if(input[0].equals("-r"))
        {
            if(Files.exists(Paths.get(input[1])))
            {
                Path src=Paths.get(input[1]);
                Path dest=Paths.get(input[2]);
                try
                {
                    Files.walk(src)
                    .forEach(source ->{
                        try
                        {
                            Files.copy(source,dest.resolve(src.relativize(source)),StandardCopyOption.REPLACE_EXISTING);
                        }
                        catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    });
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                
            }
            else
            {
                System.out.println("cannot copy the directory");
            }
            
        }
        else
        {
            if(Files.exists(Paths.get(input[0])))
            {
                try
                {
                    Files.copy(Paths.get(input[0]), Paths.get(input[1]));
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("cannot copy the file");
            }
        }
    }
    else
    {
        System.out.println("Too many arguments");
    }
}
public void cat (String input[])
{
    if(input==null)
    {
        System.out.println("please pass parameters");
    }
    else if(input.length==2)
    { // 2 arguments
        if(Files.exists(Paths.get(input[0])))
        {//file exists
            try
            { // read first file
            List <String> read1= Files.readAllLines(Paths.get(input[0]));
            List <String> read2= Files.readAllLines(Paths.get(input[1])); 
            read2= Files.write(Paths.get(input[1]),read1.getBytes(),StandardOpenOption.APPEND);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Cannot open the file");
        }
    }
    else if(input.length==1)
    {// 1 argument
        if(Files.exists(Paths.get(input[0])))
        {
            try
            {
                List <String> read = Files.readAllLines(Paths.get(input[0])); 
                System.out.println(read);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Cannot open the file");
        }
    }
    else
    {// 
        System.out.println("too many arguments");
    }
}

    public void chooseCommandAction(String command) {
        switch (command) {
        case "pwd":
            pwd();
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
        String command = parser.getCommandName();
        terminal.chooseCommandAction(command);
        parser.clear();
        }
    }
}
// Adham
// echo done
// cd done
// mkdir done
// touch done
// cp
// cp -r
// cat
// Exit
// rm
// >
// >>
// choose command
