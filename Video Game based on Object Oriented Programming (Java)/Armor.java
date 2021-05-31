public final class Armor extends WieldItems{
    //User can wield Armour to increase defence
    private final double defence;
    
    public Armor(String name, double value, int level){
        super.setName(name);
        super.setLevel(level);
        defence = value;
    }
    
    //Overriden Method
    public String printDetails(){
        return ("<html><br>Type: Armor<br>"
        + super.printDetails() + "<br>Defence: +" + defence
        +"<br></html>");
    }
    
    public double getTrait(){
        return defence;
    }
}
