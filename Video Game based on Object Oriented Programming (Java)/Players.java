import java.util.Random;
import javax.swing.*;

public abstract class Players{
    private String name;
    private int health;
    private double strength;
    private double range;
    private double defence;
    private int level = 0;
    private Armor wieldedArmor;
    private Weapon wieldedWeapon;
    private JLabel messageLabel;
    
    //Abstract class so can't have instances therefore acts as a constructor
    public void initialise(String nname){
        name = nname;
        strength = 0;
        defence = 0;
        range = 0;
        setLevel();
        setHealth();
    }
    
    //Players health is set according to there level
    public void setHealth(){
        health = ((level+10)>100)? 100 : level+10;
    }
        
    /*Algorithm to calculate player level depending on strength, range
      and defence. The traits of the weapons don't effect the level*/
    public void setLevel(){
        double sstrength;
        double rrange;
        if(wieldedWeapon instanceof Melee){
            sstrength = (wieldedWeapon!=null)? strength - wieldedWeapon.getTrait() : strength;
            rrange = range;
        }
        else{
            sstrength = strength;
            rrange = (wieldedWeapon!=null)? range - wieldedWeapon.getTrait() : range;
        }
        double ddefence = (wieldedArmor!=null)? defence - wieldedArmor.getTrait() : defence;
        if(level<=100){
            level = (int) (ddefence+sstrength+rrange)/3;   
        }
        else{
            level = 100;
        }
    }
    
    //Sets player name
    public void setName(String nname){
        name = nname;
    }
    
    //Returns player name
    public String getName(){
        return name;
    }
    
    //Returns player level
    public int getLevel(){
        return level;
    }
    
    //Returns player strength
    public double getStrength(){
        return strength;
    }
    
    //Returns player defence
    public double getDefence(){
        return defence;
    }
    
    //Returns player range
    public double getRange(){
        return range;
    }

    //Increment Strength
    public void IStrength(double value){
        strength += value;
    }

    //Increment defence
    public void IDefence(double value){
        defence += value;
    }

    //Increment range
    public void IRange(double value){
        range += value;
    }
    
    //Increment Health
    public void IHealth(int value){
        health += value;
    }
    
    //Decrement Strength
    public void DStrength(double value){
        strength -= value;
    }

    //Decrement defence
    public void DDefence(double value){
        defence -= value;
    }

    //Decrement range
    public void DRange(double value){
        range -= value;
    }

    //Decrement Health
    public void DHealth(int value){
        health -= value;
    }
    
    //returns player health
    public int getHealth(){
        return health;
    }
    
    //Set equipped Armor and change player stats accordingly
    public void setArmor(Armor item){
        wieldedArmor = item;
        defence += wieldedArmor.getTrait();
    }
    
    //Set equipped Weapon and change player stats accordingly
    public void setWeapon(Weapon item){
        wieldedWeapon = item;
        if(wieldedWeapon instanceof Ranged){
            range += wieldedWeapon.getTrait();
        }
        else{
            strength += wieldedWeapon.getTrait();
        }
    }
    
    //Get equipped Armor
    public Armor getArmor(){
        return wieldedArmor;
    }
    
    //Get equipped Weapon
    public Weapon getWeapon(){
        return wieldedWeapon;
    }
    
    /*attack algorithm players hits can be a range on numbers that
       depend on players stats, dieing throws an exception. Winning
       improve players stats permanently. Player level is also improved
       which then increases players maximum health and also unlocks new
       Wieldable items*/ 
    public String attack(Players target) throws PlayerDiedException{
        String message = "";
        double power, tPower, defence, tDefence;
        power = (getWeapon() instanceof Ranged) ? getRange():getStrength();
        tPower = (target.getWeapon() instanceof Ranged)? target.getRange():target.getStrength();
        defence = getDefence();
        tDefence = target.getDefence();
        NPC ttarget = (NPC) target;
        if((target.getWeapon() instanceof Ranged)&&(getWeapon() instanceof Ranged)){
            power = power/2;
        }
        if(target instanceof NPC){
            if(((getWeapon()).getQuirk()).equals(ttarget.getAntiQuirk())){
                tDefence = tDefence*(90/100);
            }
        }
        double[] randomHit = new double[] {0.40,0.50,0.60};
        Random random = new Random();
        int hit;
        int tHit;
        while(getHealth()>=0){
            hit = (int) (power - (tDefence*randomHit[random.nextInt(3)]));
            tHit = (int) (tPower - (defence*randomHit[random.nextInt(3)]));
            hit = (hit>0)? hit : 1;
            tHit = (tHit>0)? tHit : 1;
            target.DHealth(hit);
            message += (getName() + " hit " + target.getName()
            + " the " + ttarget.getType() + " for " + hit);
            if(target.getHealth()<=0){
                break;
            }
            message += ("<br>"
            + target.getName() + " the " + ttarget.getType()
            + "'s health is now " + target.getHealth());
            DHealth(tHit);
            message += ("<br>" 
            + target.getName() + " the " + ttarget.getType() +
            " hit " + getName() + " for " + tHit);
            if(getHealth()<=0){
                //PlayerDiedException
                throw new PlayerDiedException( message + "<br>"
                + target.getName() + " the " + ttarget.getType() +
                " killed " + getName() + "<br>" +
                "You're Dead!<br>Game Over!");
            }
            message += ("<br>"
            + getName() + "'s health is now " + getHealth() + "<br>");
        }
        message += ("<br>"
        + getName() + " killed " + target.getName() + " the "
        + ttarget.getType());
        if(target.getWeapon() instanceof Ranged){
            IRange(0.10*tPower);
            message += ("<br>"
            + "+" + (0.10*tPower) + " Range");
        }
        else{
            IStrength(0.10*tPower);
            message += ("<br>"
            + "+" + (0.10*tPower) + " Strength");
        }
        IDefence(0.10*tDefence);
        message += ("<br>"
        + "+" + (0.10*tDefence) + " Defence");
        setLevel();
        setHealth();
        return message;
    }
    
    /*Abstract all players can be scouted but each scout method is
      different I declared it as abstract here so I can use this method
      with polymorphism*/
    public abstract String scout();
}
