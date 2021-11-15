import java.util.Scanner;

class Parser {
    String commandName;
    String[] args;

    public boolean parse(String input) {
        String[] ParsedData = input.split(" ");
        // check for option and fills command name
        int i;// index for the upcoming while loop
        if (ParsedData[1].charAt(0) == '-') {
            commandName = ParsedData[0] + " " + ParsedData[1];
            // start for the while loop
            i = 2;
        } else {
            commandName = ParsedData[0];
            // start for the while loop brdo
            i = 1;
        }
        // initializing the args array
        args = new String[ParsedData.length - i];
        // fills the argument array+
        int j = 0;
        while (i < ParsedData.length) {
            args[j] = ParsedData[i];
            i++;
            j++;
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

    public void pwd() {
        System.out.println("in pwd function");
    }

    public void chooseCommandAction(String command) {

        switch (command) {
        case "pwd":
            pwd();
        default:
            System.out.println("command not recognized");
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
