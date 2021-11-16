import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Stream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;

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

    public void pwd() {
        output = path.toString();
        System.out.println(output);
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
        try {
            path = Paths.get(input[0]);
            Files.createDirectory(path);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.err.println("Failed to create directory!" + e.getMessage());
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

    public void bo2samaka(String output,String path){

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
// cd
// mkdir
// touch
// cp
// cp -r
// cat
// Exit

// Omar
// pwd
// ls
// ls -r
// rmdir
// rm
// >
// >>
// choose command
//
