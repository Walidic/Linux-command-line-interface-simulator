import java.util.Scanner;

class Parser {
    String commandName;
    String[] args;

    public boolean parse(String input) {
        String[] ParsedData = input.split(" ");
        if (ParsedData.length > 0) {
            commandName = ParsedData[0];
            if (ParsedData.length > 1) {
                int size = ParsedData.length;
                args = new String[size];
                int j = 0;
                for (int i = 1; i < size; i++) {
                    args[j] = ParsedData[i];
                    System.out.println(ParsedData[i]);
                    j++;
                }
            }
        } else {
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

    public void pwd() {
        System.out.println("in pwd function");
    }

    public void echo(String[] input) {
        int size = input.length;
        for (int i = 0; i < size; i++) {
            output = output + " " + input[i];
        }
        System.out.println(output);

    }

    public void chooseCommandAction(String command) {

        switch (command) {
        case "pwd":
            pwd();
            break;
        case "echo":
            echo(parser.getArgs());
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
// echo

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
