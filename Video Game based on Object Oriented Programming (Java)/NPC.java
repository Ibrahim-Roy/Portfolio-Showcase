public abstract class NPC extends Players{
    //Class for all enemies/Non Playable Character
    private String type;
    private String antiQuirk;
    
    //Method to print NPC details
    public String scout(){
        return(
            "<html>Type: " + getType() +
            "<br>Name: " + getName() +
            "<br>Level: " + getLevel() +
            "<br>Armor: " + (getArmor()).getName() +
            "<br>Weapon: " + (getWeapon().getName()) +
            "<br>Anti Quirk: " + getAntiQuirk()
        );
    }
    
    //Acts as a constructor to initialise all states of any subclass
    public void initialise(String nname, double strength,
    double range, double defence, Armor armor, Weapon weapon){
        super.initialise(nname);
        super.IStrength(strength);
        super.IRange(range);
        super.IDefence(defence);
        super.setArmor(armor);
        super.setWeapon(weapon);
        super.setLevel();
        super.setHealth();
    }
    
    //Type of NPC
    public void setType(String ttype){
        type = ttype;
    }
    
    public String getType(){
        return type;
    }
    
    //Weakness relevent to quirks of weapons
    public void setAntiQuirk(String Aquirk){
        antiQuirk = Aquirk;
    }
    
    public String getAntiQuirk(){
        return antiQuirk;
    }
}