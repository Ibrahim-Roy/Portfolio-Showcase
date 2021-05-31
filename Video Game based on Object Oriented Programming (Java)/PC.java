import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Playable Characters
public class PC extends Players implements ActionListener{
    //All Swing Components involved in action events are declared here
    private Armor wieldedArmor;
    private Weapon wieldedWeapon;
    private ArrayList<JButton> armorButtons = new ArrayList<JButton>();
    private Armor[] allArmor = new Armor[]{
        new Armor("FABRIC SHIRT", 5, 0),
        new Armor("LEATHER SHIRT", 10, 6),
        new Armor("BRONZE CHAINMAIL", 15, 11),
        new Armor("BRONZE PLATEBODY", 20, 16),
        new Armor("IRON CHAINMAIL", 25, 21),
        new Armor("IRON PLATEBODY", 30, 26),
        new Armor("STEEL CHAINMAIL", 35, 31),
        new Armor("STEEL PLATEBODY", 40, 36),
        new Armor("MITHRIL CHAINMAIL", 45, 41),
        new Armor("MITHRIL PLATEBODY", 50, 46),
        new Armor("ADAMANT CHAINMAIL", 55, 51),
        new Armor("ADAMANT PLATEBODY", 60, 56),
        new Armor("RUNE CHAINMAIL", 70, 66),
        new Armor("RUNE PLATEBODY", 80, 76),
        new Armor("DRAGON HIDE SHIRT", 90, 86)
    };
    private ArrayList<JButton> weaponButtons = new ArrayList<JButton>();
    Weapon[] allWeapon = new Weapon[]{
            new Ranged("WOODEN BOW", 5, 0),
            new Melee("WOODEN SWORD", 5, 0)
    };
    private JFrame window;
    private JLabel alert = new JLabel("");
    
    //Class for playable characters
    public PC(String name, Weapon weapon, Armor armor){
        super.initialise(name);
        wieldedWeapon = weapon;
        wieldedArmor = armor;
        super.setWeapon(wieldedWeapon);
        super.setArmor(wieldedArmor);
    }
    
    /*Shows a selection of weapons that can be wielded to
       temporary increase strength or range
       Weapons improve skills but won't effect the level*/
    public void wieldWeapon(){
        JLabel l = new JLabel("Please choose the weapon you'd like to wield");
        window = new JFrame();
        window.setSize(1500,800);
        window.setLayout(new FlowLayout());
        window.add(l);
        for(int i=0; i<allWeapon.length; i++){
                weaponButtons.add(new JButton(allWeapon[i].printDetails()));
                window.add(weaponButtons.get(i));
                weaponButtons.get(i).addActionListener(this);
        }
        window.setVisible(true);
        window.addWindowListener(new WindowCloser());
    }
    
    /*Shows a selection of armor that can be wielded to temporary increase defence
      Armor improves skills but won't effect the level*/
    public void wieldArmor(){
        JLabel l = new JLabel("Please choose the Armor you'd like to weild");
        window = new JFrame();
        window.setSize(1500, 800);
        window.setLayout(new FlowLayout());
        window.add(l);
        for(int i=0; i<allArmor.length; i++){
            armorButtons.add(new JButton(allArmor[i].printDetails()));
            window.add(armorButtons.get(i));
            armorButtons.get(i).addActionListener(this);
        }
        window.add(alert);
        window.setVisible(true);
        window.addWindowListener(new WindowCloser());
    }
    
    //Display's current players stats    
    public String scout(){
        return ("<html>Name: " + getName() + "<br>Level: " + getLevel() + 
        "<br>Armor: " +(getArmor()).getName()
        + "<br>Weapon: " + (getWeapon()).getName() +
        "<br>Strength: "  + getStrength() + "<br>Defence: " + getDefence()
        + "<br>Range: " + + getRange() + "<br>Health: " + getHealth());
    }
    
    
    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(armorButtons.contains(source)){
            int positionInArray = armorButtons.indexOf(source);
            if(getLevel()>=allArmor[positionInArray].getLevel()){
                DDefence(wieldedArmor.getTrait());
                wieldedArmor = allArmor[positionInArray];
                super.setArmor(wieldedArmor);
                window.dispose();
            }
            else{
                alert.setText("<html>Your level is too low to wield this"
                + "<br>Try Again!</html>");
            }
        }
        else if(weaponButtons.contains(source)){
            int positionInArray = weaponButtons.indexOf(source);
            if(getLevel()>=allWeapon[positionInArray].getLevel()){
                if(wieldedWeapon instanceof Melee){
                    DStrength(wieldedWeapon.getTrait());
                }
                else{
                    DRange(wieldedWeapon.getTrait());
                }
                wieldedWeapon = allWeapon[positionInArray];
                super.setWeapon(wieldedWeapon);
                window.dispose();
                wieldedWeapon.chooseQuirk();
            }
            else{
                alert.setText("<html>Your level is too low to wield this"
                + "<br>Try Again!</html>");
            }
        }
    }
}