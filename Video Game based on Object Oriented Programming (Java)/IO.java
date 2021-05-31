import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public final class IO implements ActionListener{
    private ArrayList<JButton> fileButtons = new ArrayList<JButton>();
    private ArrayList<String> fileNames = new ArrayList<String>();
    private JFrame window;
    private JButton menu;
    
    //Method to save game as players name
    public void save(PC player) throws IOException{
        File folderPath = new File("Game Saves");
        if(!folderPath.exists() && !folderPath.isDirectory()){
            folderPath.mkdir();
        }
        File path = new File(player.getName());
        OutputStream output = new FileOutputStream(folderPath + "/" + path);
        output.write((player.getName() + "\n").getBytes());
        output.write((String.valueOf(player.getDefence() - (player.getArmor()).getTrait()) + "\n").getBytes());
        if(player.getWeapon() instanceof Melee){
            output.write((String.valueOf(player.getStrength() - (player.getWeapon()).getTrait()) + "\n").getBytes());
            output.write((String.valueOf(player.getRange()) + "\n").getBytes());
        }
        else if(player.getWeapon() instanceof Ranged){
            output.write((String.valueOf(player.getStrength()) + "\n").getBytes());
            output.write((String.valueOf((player.getRange())  - (player.getWeapon()).getTrait()) + "\n").getBytes());
        }
        WieldItems[] playerItems = new WieldItems[]{
            player.getWeapon(),
            player.getArmor()            
        };
        for(int i=0; i<playerItems.length; i++){
            if(playerItems[i] instanceof Weapon){
                output.write((((Weapon)playerItems[i]).getType() + "\n").getBytes());
                output.write((((Weapon)playerItems[i]).getQuirk() + "\n").getBytes());
            }
            output.write((playerItems[i].getName() + "\n").getBytes());
            output.write((playerItems[i].getTrait() + "\n").getBytes());
            output.write((playerItems[i].getLevel() + "\n").getBytes());
        }
        output.close();
    }
    
    //Opens directory whereand displays all saved games to choose from
    public void selectLoadGame() throws IOException{
        File folderPath = new File("Game Saves");
        window = new JFrame();
        if(!folderPath.exists() || (folderPath.list()).length==0){
            JLabel l = new JLabel("NO SAVED FILES FOUND");
            menu = new JButton("MENU");
            menu.addActionListener(this);
            window.add(l);
            window.add(menu);
        }
        else{
            String[] folderFiles = folderPath.list();
            for(String i : folderFiles){
                fileNames.add(i);
            }
            for(int i=0; i<fileNames.size(); i++){
                fileButtons.add(new JButton(fileNames.get(i)));
                fileButtons.get(i).addActionListener(this);
                window.add(fileButtons.get(i));
            }
        }
        window.setLayout(new FlowLayout());
        window.setSize(1500,800);
        window.setVisible(true);
        window.addWindowListener(new WindowCloser());
    }
    
    /*loads game by creating a new PC object and setting it's state
       to the PC player in the loaded game*/
    public void loadGame(String fileName) throws IOException{
        InputStream input = new FileInputStream("Game Saves/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String playerName = reader.readLine();
        double playerDefence = Double.valueOf(reader.readLine());
        double playerStrength = Double.valueOf(reader.readLine());
        double playerRange = Double.valueOf(reader.readLine());
        String weaponType = reader.readLine();
        String weaponQuirk = reader.readLine();
        Weapon playerWeapon = null;
        Armor playerArmor = null;
        for(int i=0; i<2; i++){
            String itemName = reader.readLine();
            double itemTrait = Double.valueOf(reader.readLine());
            int itemLevel = Integer.parseInt(reader.readLine());
            if(i==0){
                if(weaponType.equals("Melee")){
                    playerWeapon = new Melee(itemName, itemTrait, itemLevel);
                }
                else{
                    playerWeapon = new Ranged(itemName, itemTrait, itemLevel);
                }
                playerWeapon.setQuirk(weaponQuirk);
            }
            else{
                playerArmor = new Armor(itemName, itemTrait, itemLevel);
            }
        }
        input.close();
        PC player = new PC(playerName, playerWeapon, playerArmor);
        player.IStrength(playerStrength);
        player.IRange(playerRange);
        player.IDefence(playerDefence);
        new Run(player);
    }
    
    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(fileButtons.contains(source)){
            int posInList = fileButtons.indexOf(source);
            window.dispose();
            try{
                loadGame(fileNames.get(posInList));
            }
            catch(IOException ignore){
                throw new RuntimeException(ignore);
            }
        }
        else if(source==menu){
            window.dispose();
            new Menu();
        }
    }
}