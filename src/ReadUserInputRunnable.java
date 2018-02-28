import java.util.Scanner;

public class ReadUserInputRunnable implements Runnable {
  private static volatile boolean running = true; //Ensures that there is only one memory of running so that thread can be immediately killed when method is called
  private Scanner user_input;
  private Colony colony;

  public ReadUserInputRunnable(Colony colony){
    user_input = new Scanner(System.in);
    this.colony = colony;
  }

  public void run() {
    //Will continue running until all WorkerAnt threads have ended
    while(running) {
      String input = user_input.nextLine();
      //if condition is used because to continue main after this thread has been killed, the last nextLine() could trigger the colony.printInfo() or Incorrect Input
      if(running){
        switch(input.toLowerCase()){
          case "c":
            colony.printInfo();
            break;
          default:
            System.out.println("Incorrect Input!!");
        }
      }
    }
  }

  public void kill() {
    running = false;
    System.out.println("Press any key to see final results . . .");
    user_input.close();
  }
}
