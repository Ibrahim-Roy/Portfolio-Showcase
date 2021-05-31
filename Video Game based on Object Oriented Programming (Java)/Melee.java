public final class Melee extends Weapon{
    //Subclass of weapon
    private final double strength;
    
    public Melee(String name, double value, int level){
        super.setName(name);
        super.setLevel(level);
        strength = value;
        setType("Melee");
    }
    
    //Overriden Method
    public String printDetails(){
        return ("<html>Type: Meele<br>"+ super.printDetails() +
        "<br>strength: +" + strength  + "<br>Weapon Quirk: "
        + (super.getQuirk()) + "</html>");
    }
    
    public double getTrait(){
        return strength;
    }
}
