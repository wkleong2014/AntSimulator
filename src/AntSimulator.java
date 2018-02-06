import java.lang.*;

public class AntSimulator{
  public static void main(String[] args){

    QueenAnt queenAnt = new QueenAnt("Queen Ant", 20, 20);

    //can replace Thread with ExecutorService
    Thread t1 = new Thread(queenAnt);
    try{
      t1.start();
      t1.join();
    } catch(InterruptedException e){

    }

    // System.out.println(queenAnt);
    // System.out.println("***** Simulation Begun *****");

  }
}
