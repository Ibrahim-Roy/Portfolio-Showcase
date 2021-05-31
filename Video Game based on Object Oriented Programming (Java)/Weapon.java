import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
//All weapons can have Quirks which give you an edge over certain enemies
public abstract class Weapon extends WieldItems implements ActionListener{
    private String quirk;
    private JFrame window = new JFrame();
    private String[] allQuirks = new String[]{
        "FIRE","WATER","WIND","EARTH"
    };
    private JLabel l = new JLabel("Please choose the quirk");
    private ArrayList<JButton> quirkButtons = new ArrayList<JButton>();
    private String type;
    
    public void setType(String ttype){
        type=ttype;
    }
    
    public String getType(){
        return type;
    }
    
    //Weapons can have quirks which make it easier to target some NPC's
    public void setQuirk(String qquirk){
        quirk = qquirk;
    }
    
    public String getQuirk(){
        return quirk;
    }
    
    //Implements GUI which lets you select a quirk for your weapon
    public void chooseQuirk(){
        window.setSize(1500,800);
        window.setLayout(new FlowLayout());
        window.add(l);
        for(int i=0; i<allQuirks.length; i++){
            quirkButtons.add(new JButton(allQuirks[i]));
            window.add(quirkButtons.get(i));
            quirkButtons.get(i).addActionListener(this);
        }
        window.setVisible(true);
        window.addWindowListener(new WindowCloser());
    }
    
    public void actionPerformed(ActionEvent evt){
       Object source = evt.getSource();
       if(quirkButtons.contains(source)){
           int indexInArray = quirkButtons.indexOf(source);
           quirk = allQuirks[indexInArray];
           window.dispose();
       }
    }
}