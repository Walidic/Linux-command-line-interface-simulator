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
        // fills the argument array
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
    public static void main(String[] args) throws Exception {

    }
}
