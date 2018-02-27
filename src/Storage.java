//Resources (like the bank storage)
import java.util.concurrent.locks.ReentrantLock;

public class Storage{
  private int resources;
  public ReentrantLock storageLock = new ReentrantLock();
  public static final int STARTING_RESOURCES = 150;

  public Storage(){
    resources = STARTING_RESOURCES;
  }

  public void incResource(int amount){
    try{
      storageLock.lock();
      resources += amount;
    }catch(Exception e){
    }
    finally{
      storageLock.unlock();
    }
  }

  public void decResource(int amount){
    try{
      storageLock.lock();
      resources -= amount;
    }catch(Exception e){
    }
    finally{
      storageLock.unlock();
    }
  }

  public int getResources(){
    return resources;
  }

}
