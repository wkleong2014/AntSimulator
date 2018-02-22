import java.util.Scanner;

public class ReadUserInput implements Runnable {
    private static volatile boolean running = true;
    Scanner user_input;
    Colony colony;

    public ReadUserInput(Colony colony){
      user_input = new Scanner(System.in);
      this.colony = colony;
    }

    public void run() {
        while(running) {
            String input = user_input.nextLine();
            switch(input.toLowerCase()){
              case "c":
                System.out.println("ReadingInput Input Value: " + input);
                colony.printInfo();
                break;
              default:
                System.out.println("Incorrect Input!!");
            }
        }
    }

    public void kill() {
        running = false;
        System.out.println("User Input Thread has Ended");
    }
}