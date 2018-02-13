//Resources (like the bank storage)
import java.util.concurrent.locks.ReentrantLock;

public class Storage{
  private int resources;
  public ReentrantLock storageLock = new ReentrantLock();
  public Storage(){
    resources = 150;
  }

  public void incResource(int amount){
    try{
      storageLock.lock();
      Thread.sleep(5); //increase chances of race condition
      resources += amount;
      Thread.sleep(5); //increase chances of race condition
    }catch(Exception e){
    }
    finally{
      storageLock.unlock();
    }
  }

  public void decResource(int amount){
    try{
      storageLock.lock();
      Thread.sleep(5); //increase chances of race condition
      resources -= amount;
      Thread.sleep(5); //increase chances of race condition
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
