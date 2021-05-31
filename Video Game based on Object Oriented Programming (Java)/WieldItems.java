//Main class to define all items a player can weild
public abstract class WieldItems{
    
    //Instance Variables
    private String name;
    private int level;
    
    //Instance Methods
    public void setName(String nname){
        name = nname;
    }
    public void setLevel(int value){
        level = value;
    }
    public String printDetails(){
        return ("Name: " + name + "<br>Level: " + level);
    }
    public String getName(){
        return name;
    }
    public int getLevel(){
        return level;
    }
    
    /*All subclasses must define getTrait which gets
      the value of whatever skill they represent*/
    public abstract double getTrait();
}
