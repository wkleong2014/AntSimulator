
import java.util.concurrent.ConcurrentHashMap;

public class Expedition{
  private int id;
  private final int MAX_PROGRESS = 100;
  private ConcurrentHashMap<Integer, Integer> progress = new ConcurrentHashMap<Integer, Integer>();
  private int counter = 0;

  public Expedition(int id){
    this.id = id;
    progress.put(id, 0);
  }

  public void addCounter(){
    counter++;
  }

  public int getCounter(){
    return counter;
  }

  public int getID(){return id;}

  public int getProgress(int id){
      return progress.get(id);
  }

  public void addProgress(int id, int amount){
      progress.put(id, amount);
  }

}
