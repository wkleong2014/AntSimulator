//Resources (like the bank storage)
public class Storage{
  private int resources;

  public Storage(){
    resources = 100;
  }

  public void incResource(int amount){
    resources += amount;
  }

  public void decResource(int amount){
    resources -= amount;
  }

}
