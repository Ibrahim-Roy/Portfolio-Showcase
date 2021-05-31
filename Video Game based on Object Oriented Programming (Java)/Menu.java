import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Menu implements ActionListener{
    private JButton newGame;
    private JButton continueGame;
    private JFrame window;
    private JTextField username;
    private String name;
    private JButton submitName;
    
    //Main Method
    public static void main(String[] args){
        new Menu();
    }
    
    //Constructor for Main Menu
    //Uses different panels for layout
    //Allows a choice between new game or a load game
    public Menu(){
        JLabel gameName = new JLabel("ARC OF REVENGE", JLabel.CENTER);
        gameName.setSize(100,20);
        JPanel p = new JPanel();
        p.add(gameName);
        p.setLayout(new FlowLayout());
        newGame = new JButton("NEWGAME");
        newGame.setSize(100,40);
        newGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        newGame.addActionListener(this);
        continueGame = new JButton("CONTINUE GAME");
        continueGame.setSize(100,40);
        continueGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueGame.addActionListener(this);
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        p1.add(newGame);
        p1.add(continueGame);
        window = new JFrame();
        window.setSize(1500,800);
        window.setLayout(new GridLayout(3,1));
        window.add(p);
        window.add(p1);
        window.addWindowListener(new WindowCloser());
        window.setVisible(true);
    }
    
    //Initialises NewGame
    public void getName(){
        username = new JTextField("Please type your character's name!",30);
        submitName = new JButton("Submit");
        submitName.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitName.addActionListener(this);
        window = new JFrame();
        window.setLayout(new FlowLayout());
        window.setSize(1500,800);
        window.add(username);
        window.add(submitName);
        window.addWindowListener(new WindowCloser());
        window.setVisible(true);
    } 
    
    public void actionPerformed(ActionEvent evt){
            Object source = evt.getSource();
            if(source==newGame){
                window.dispose();
                getName();
            }
            else if(source==continueGame){
                window.dispose();
                try{
                    (new IO()).selectLoadGame();
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
            }
            else if(source==submitName){
                window.dispose();
                //New game player starts with basic equipment
                name = username.getText();
                PC player = new PC(
                name,
                new Melee("WOODEN SWORD", 5, 0),
                new Armor("FABRIC SHIRT", 5, 0)
                );
                (player.getWeapon()).setQuirk("FIRE");
                new Run(player);
            }
    }
}

