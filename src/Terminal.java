import java.util.Scanner;
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
            for(int i = 1; i <ParsedData.length; i++) {
                // ParsedData;
            }
            else {
            System.out.println("no input entered");
        }
        return true;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
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

                // if(Files.exists(Paths.get(input[0])))
                // {
                // path=Paths.get(input[0]);
                // }
                // else
                // {
                // System.out.println("No directory found ");
                // }
                // realtive
                if (!Paths.get(input[0]).isAbsolute()) {
                    String currentPath = path.toString();
                    String fullPath = currentPath + "\\" + input[0];
                    System.out.println(fullPath);
                    if (Files.exists(Paths.get(fullPath.toString()))) {
                        path = Paths.get(fullPath.toString());
                    } else {
                        System.out.println("No directory found ");
                    }
                }
            }
        } else {
            System.out.println("Too many arguments");
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
            terminal.parser.parse(enteredCommand);
            String command = parser.getCommandName();
            terminal.chooseCommandAction(command);
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
