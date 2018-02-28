import java.util.concurrent.locks.ReentrantLock;

public class Storage{
  private int resources;
  private ReentrantLock storageLock = new ReentrantLock();
  public static final int STARTING_RESOURCES = 150;         //Sets the starting resources of each colony's storage

  public Storage(){
    resources = STARTING_RESOURCES;
  }

  //The getter method do not lock storageLock as it is already locked before calling this method
  public int getResources(){return resources;}

  public int incResource(int amount){
    try{
      storageLock.lock();
      resources += amount;
    }catch(Exception e){
    }
    finally{
      storageLock.unlock();
      //returning resources prevents a race condition when printing the updated resources as it allows us to store the returned value with a temporary variable
      return resources;
    }
  }

  public int decResource(int amount){
    try{
      storageLock.lock();
      resources -= amount;
    }catch(Exception e){
    }
    finally{
      storageLock.unlock();
      //returning resources prevents a race condition when printing the updated resources as it allows us to store the returned value with a temporary variable
      return resources;
    }
  }

  public void lockStorage(){
    storageLock.lock();
  }

  public void unlockStorage(){
    storageLock.unlock();
  }
}
