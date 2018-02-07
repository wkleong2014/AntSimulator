//Resources (like the bank storage)
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage{
  private static int resources;

  public Storage(){
    resources = 100;
  }

  public static void incResource(int amount){
    resources += amount;
  }

  public static void decResource(int amount){
    resources -= amount;
  }

  public static int getResources(){return resources;}

}
