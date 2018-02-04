//Queen Ant
import java.lang.*;

public class QueenAnt extends Ant implements Runnable{
  private String test;

  public QueenAnt(int id, String test){
    super(id);
    this.test = test;
  }

  public String toString(){
    return "" + super.getID() + " | " + test;
  }

  @Override
  public void run(){
    System.out.println("Queen Ant has been created...");
    System.out.println("Testing.. toString() method: " + toString());
  }

}
