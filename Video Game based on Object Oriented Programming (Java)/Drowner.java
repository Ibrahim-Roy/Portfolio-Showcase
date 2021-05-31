public final class Drowner extends NPC{
    //A NPC subclass
    public Drowner(String name, double value, Armor armor, Weapon weapon){
        super.initialise(name, value, value, value, armor, weapon);
        super.setType("DROWNER");
        super.setAntiQuirk("EARTH");
    }
}