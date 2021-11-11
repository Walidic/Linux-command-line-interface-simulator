import javax.sound.midi.Soundbank;

class Parser {
String commandName;
String[] args = new String[3];
// public Parser(){

// }

public boolean parse(String input){
    String [] Parsedinput=input.split(" ");
    if(Parsedinput[1].charAt(0)=='-'){
        commandName=Parsedinput[0]+" "+Parsedinput[1];
        if (Parsedinput[2].length()>0){
            args[0]=Parsedinput[2];
            if (Parsedinput[3].length()>0){
                args[1]=Parsedinput[3];
            }
        }
       
    }
    System.out.println(commandName);
    if(args[0].length()>0){
        System.out.println(args[0]);
        if(args[1].length()>0){
            System.out.println(args[1]);
        }
    }
    return true;
}
//   public String getCommandName(){

//  }
//   public String[] getArgs(){

// }
}

public class App {
    public static void main(String[] args) throws Exception {
       Parser p = new Parser();
       p.parse("omar -r a7a a7a");
    }
}
