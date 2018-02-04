import java.lang.*;

public class AntSimulator{
  public static void main(String[] args){

    QueenAnt queenAnt = new QueenAnt(1, "Queen Ant 1");

    //can replace Thread with ExecutorService
    Thread t1 = new Thread(queenAnt);
    t1.start();

    // System.out.println(queenAnt);
    // System.out.println("***** Simulation Begun *****");

  }
}
