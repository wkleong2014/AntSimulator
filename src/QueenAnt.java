//Queen Ant
import java.lang.*;

public class QueenAnt extends Ant implements Runnable{
  private int no_of_eggs_laid = 0;
  private final int DEATH = 10;

  public QueenAnt(String name, int height, int weight){
    super(name, height, weight);
  }

  // public String toString(){
  //   return "" + super.getID() + " | " + test;
  // }

  public void layEgg(){
    WorkerAnt ant = new WorkerAnt("0001", 20, 20);
    Thread t1 = new Thread(ant);
    t1.start();
  }

  @Override
  public void run(){
    System.out.println("QueenAnt is Running...");
    //Timer for QueenAnt to lay eggs
    for(int i=0;i<10;i++){
      System.out.println("QueenAnt is laying Egg for the " + i + " times");
      layEgg();
    }

    //layEgg()
  }

}
