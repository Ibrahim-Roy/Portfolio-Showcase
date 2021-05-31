public final class Ogre extends NPC{
    //A NPC subclass
    public Ogre(String name, double value, Armor armor, Weapon weapon){
        super.initialise(name, value, value, value, armor, weapon);
        super.setType("OGRE");
        super.setAntiQuirk("FIRE");
    }
}