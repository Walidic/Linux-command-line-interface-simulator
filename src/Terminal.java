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
    String output = new String();

    public void pwd() {
        System.out.println("in pwd function");
    }

    public void echo(String[] input) {
        if(input==null || input.length>0)
        {
            int size = input.length;
            output=input[0];
            for(int i = 1; i < size; i++) { //1 to ignore the space
                output= output+" "+input[i];
            }
            System.out.println(output);
            output="";

        }
        else
        {
            System.out.println("Please enter an argument");
        }
    }

    public void cd(String[] input)
    {
        

    }

    public void chooseCommandAction(String command) {

        switch (command) {
        case "pwd":
            pwd();
            break;
        case"echo":
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
