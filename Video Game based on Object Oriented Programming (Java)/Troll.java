public final class Troll extends NPC{
    //A NPC subclass
    public Troll(String name, double value, Armor armor, Weapon weapon){
        super.initialise(name, value, value, value, armor, weapon);
        super.setType("FIRE TROLL");
        super.setAntiQuirk("WATER");
    }
}