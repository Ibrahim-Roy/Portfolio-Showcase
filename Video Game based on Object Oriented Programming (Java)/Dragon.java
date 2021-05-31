public final class Dragon extends NPC{
    //A NPC subclass
    public Dragon(String name, double value, Armor armor, Weapon weapon){
        super.initialise(name, value, value, value, armor, weapon);
        super.setType("DRAGON");
        super.setAntiQuirk("WIND");
    }
}