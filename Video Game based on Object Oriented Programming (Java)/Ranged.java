public final class Ranged extends Weapon{
    //Subclass of weapon
    private final double range;
    
    public Ranged(String name, double value, int level){
        super.setName(name);
        super.setLevel(level);
        range = value;
        setType("Ranged");
    }
    
    //Overriden Method
    public String printDetails(){
        return ("<html>Type: Ranged<br>"+ super.printDetails() +
        "<br>Range: +" + range + "<br>Weapon Quirk: "
        + (super.getQuirk()) + "</html>");
    }
    
    public double getTrait(){
        return range;
    }
}
