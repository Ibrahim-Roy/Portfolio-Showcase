import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import javax.imageio.ImageIO;

//The main class which implements the GUI
class Run implements ActionListener{
    //All GUI components that are part of an action event are declared here 
    private JFrame window;
    private ArrayList<JButton> gameTiles = new ArrayList<JButton>();
    private NPC[] enemy = new NPC[4];
    private JButton playerCharacter;
    private ArrayList<JButton> enemyCharacters = new ArrayList<JButton>();
    private PC player;
    private JLabel enemyLabel;
    private JButton attack;
    private NPC currentEnemy;
    private JLabel messageLabel = new JLabel(
        "<html>WELCOME!<br>IN THIS GAME YOU ACT AS A HUNTER TRYING" +
        " TO EARN YOURSELF A NAME IN THE GUILD<br>YOUR JOURNEY PUTS" + 
        " YOU IN THE WILD LANDS WITH MANY " +
        "MONSTERS<br>YOU SEE THIS AS AN OPPORTUNITY TO TRAIN! " +
        "YOU NEED TO KILL THESE ENEMIES TO INCREASE YOUR LEVEL" + 
        "<br>SO YOU CAN PRESENT ALL YOUR TROPHIES IN THE GUILD" +
        "<br>GET TO LEVEL 100 TO BE GRANTED THE RANK OF GUILD MASTER" +
        "<br>YOUR JOURNEY NEVER ENDS THOUGH AS THERE ARE ALWAYS MORE" + 
        " MONSTERS<br>YOU START WITH VERY BASIC EQUIPMENT LEVEL UP TO " +
        "UPGRADE IT<br>CHECK THE BOTTOM RIGHT SECTION FOR ALL RULES" + 
        " AND CONTROLS</html>");
    private JButton changeArmor;
    private JButton changeWeapon;
    private JLabel armorLabel;
    private JLabel weaponLabel;
    private JButton menu;
    
    //Constructor
    public Run(PC pplayer){
        player = pplayer;
        execute();
    }
    
    //Main GUI created here
    public void execute(){
        //Substitutuion Principle
        window = new JFrame();
        window.setSize(1500,800);
        window.setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(250,800));
        leftPanel.setLayout(new GridLayout(3,1)); 
        JPanel armorPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Image image;
                try{
                    image = ImageIO.read(new File("armorBackground.png"));
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
                g.drawImage(image, 0,0, this.getWidth(), this.getHeight(), null);
            }
        };
        armorPanel.setBackground(Color.black);
        armorLabel = new JLabel((player.getArmor()).printDetails());
        armorLabel.setForeground(Color.white);
        armorPanel.add(armorLabel);
        changeArmor = new JButton("CHANGE ARMOR");
        changeArmor.addActionListener(this);
        armorPanel.add(changeArmor);
        JPanel weaponPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Image image;
                try{
                    image = ImageIO.read(new File("weaponBackground.png"));
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        weaponPanel.setBackground(Color.blue);
        weaponLabel = new JLabel((player.getWeapon()).printDetails());
        weaponLabel.setForeground(Color.white);
        weaponPanel.add(weaponLabel);
        changeWeapon = new JButton("CHANGE WEAPON");
        changeWeapon.addActionListener(this);
        weaponPanel.add(changeWeapon);
        JPanel playerPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Image image;
                try{
                    image = ImageIO.read(new File("playerBackground.png"));
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        playerPanel.setBackground(Color.black);
        JLabel playerLabel = new JLabel(player.scout());
        playerLabel.setForeground(Color.white);
        playerPanel.add(playerLabel);
        leftPanel.add(armorPanel);
        leftPanel.add(weaponPanel);
        leftPanel.add(playerPanel);
        JPanel centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(1000,800));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JPanel gamePanel = new JPanel();
        gamePanel.setPreferredSize(new Dimension(1000,500));
        gamePanel.setBackground(Color.black);
        gamePanel.setLayout(new GridLayout(5,9));
        Random random = new Random();
        int[] position = new int[4];
        position[0] = random.nextInt(10);
        position[1] = random.nextInt(20);
        position[2] = random.nextInt(30);
        position[3] = random.nextInt(40);
        createEnemies();
        gameTiles.clear();
        for(int i=0; i<45; i++){
            if(i==40){
                playerCharacter = new JButton(player.getName());
                gameTiles.add(null);
                playerCharacter.setBackground(Color.blue);
                playerCharacter.setForeground(Color.white);
                playerCharacter.addActionListener(this);
                playerCharacter.setBorder(null);
                playerCharacter.setIcon(new ImageIcon("hero.gif"));
                gamePanel.add(playerCharacter);
            }
            else if(i==position[0]){
                (enemyCharacters.get(0)).setBackground(Color.red);
                (enemyCharacters.get(0)).setForeground(Color.white);
                (enemyCharacters.get(0)).addActionListener(this);
                (enemyCharacters.get(0)).setBorder(null);
                gamePanel.add(enemyCharacters.get(0));
                gameTiles.add(null);
            }
            else if(i==position[1]){
                (enemyCharacters.get(1)).setBackground(Color.red);
                (enemyCharacters.get(1)).setForeground(Color.white);
                (enemyCharacters.get(1)).addActionListener(this);
                (enemyCharacters.get(1)).setBorder(null);
                gamePanel.add(enemyCharacters.get(1));
                gameTiles.add(null);
            }
            else if(i==position[2]){
                (enemyCharacters.get(2)).setBackground(Color.red);
                (enemyCharacters.get(2)).setForeground(Color.white);
                (enemyCharacters.get(2)).addActionListener(this);
                (enemyCharacters.get(2)).setBorder(null);
                gamePanel.add(enemyCharacters.get(2));
                gameTiles.add(null);
            }
            else if(i==position[3]){
                (enemyCharacters.get(3)).setBackground(Color.red);
                (enemyCharacters.get(3)).setForeground(Color.white);
                (enemyCharacters.get(3)).addActionListener(this);
                (enemyCharacters.get(3)).setBorder(null);
                gamePanel.add(enemyCharacters.get(3));
                gameTiles.add(null);
            }
            else{
                gameTiles.add(new JButton(""));
                (gameTiles.get(i)).setBackground(Color.green);
                (gameTiles.get(i)).addActionListener(this);
                (gameTiles.get(i)).setBorder(null);
                (gameTiles.get(i)).setIcon(new ImageIcon("grass.png"));
                gamePanel.add(gameTiles.get(i));
            }
        }
        JPanel messagePanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                Image image;
                try{
                    image = ImageIO.read(new File("messageBackground.png"));
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        messagePanel.setPreferredSize(new Dimension(1000,300));
        messagePanel.setBackground(Color.blue);
        messageLabel.setForeground(Color.white);
        messagePanel.add(messageLabel);
        centerPanel.add(gamePanel);
        centerPanel.add(new JScrollPane(messagePanel));
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(250,800));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JPanel enemyPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Image image;
                try{
                    image = ImageIO.read(new File("enemyBackground.png"));
                }
                catch(IOException ignore){
                    throw new RuntimeException(ignore);
                }
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
            }
        };
        enemyPanel.setLayout(new FlowLayout());
        enemyPanel.setPreferredSize(new Dimension(250,500));
        enemyPanel.setBackground(Color.black);
        enemyLabel = new JLabel("");
        enemyLabel.setForeground(Color.white);
        attack = new JButton("ATTACK");
        attack.setVisible(false);
        enemyPanel.add(enemyLabel);
        enemyPanel.add(attack);
        JPanel rulePanel = new JPanel();
        rulePanel.setPreferredSize(new Dimension(250,300));
        String[] rules = new String[]{
            "<html>1. CLICK ON ANY OF THE GRASS<br>TILES TO ROAM THE"
            + " WORLD AND<br>SEARCH FOR NEW MONSTERS</html>",
            "<html>2. CLICK ON THE MONSTERS TO<br>SCOUT THEM</html>",
            "<html>3. CLICK ON ATTACK IN SCOUT<br>PANEL" +
            " TO ATTACK MONSTER</html>",
            "<html>4. ONLY ATTACK MONSTERS YOU<br>HAVE A " + 
            "CHANCE TO KILL</html>",
            "<html>5. IF YOU DIE THERE YOU WILL<br>HAVE TO START" +
            " FROM YOUR<br>LAST SAVE</html>",
            "<html>6. EACH FIGHT YOU WIN<br>REFILLS YOUR HEALTH</html>",
            "<html>7. YOU CAN CHANGE YOUR WEAPONS<br>AND ARMOR BY" + 
            " PRSESSING THE<br>BUTTONS IN THEIR PANELS<br>" + 
            "IT TAKES TIME UNTIL THE NEW<br>WEAPONS ARE READY TO BE" + 
            "<br>WIELDED, SO ROAM AROUND AND<br>ONCE READY THEY WILL " + 
            "AUTOMATICALLY<br>SHOW IN THEIR PANELS</html>",
            "<html>8. YOUR WEAPONS HAVE QUIRKS<br>WHICH GIVE YOU AN" 
            + " EDGE<br>OVER CERTAIN ENEMIES</html>",
            "<html>9. CLICK ON YOUR PLAYER<br>ICON TO SAVE" + 
            " THE GAME</html>"
        };
        rulePanel.setLayout(new BoxLayout(rulePanel, BoxLayout.Y_AXIS));
        JLabel ruleLabel = new JLabel("GUIDE");
        ruleLabel.setAlignmentX(rulePanel.CENTER_ALIGNMENT);
        rulePanel.add(ruleLabel);
        JList ruleList = new JList(rules);
        rulePanel.add(new JScrollPane(ruleList));
        rightPanel.add(enemyPanel);
        rightPanel.add(rulePanel);
        window.add(leftPanel, BorderLayout.WEST);
        window.add(centerPanel, BorderLayout.CENTER);
        window.add(rightPanel, BorderLayout.EAST);
        window.setVisible(true);
    }
    
    /*Every time you roam in the game new enemies are created with
      different stats, levels, names and types*/
    public void createEnemies(){
        Random random = new Random();
        String[] names = new String[]{"OKA", "JUNU", "DEEK", "RULO"};
        enemyCharacters.clear();
        for(int i=0; i<4; i++){
            int x = random.nextInt(4);
            int y = random.nextInt(2);
            if(x==0){
                enemy[i] = new Ogre(names[i],random.nextInt(51),
                new Armor("CHAIN MAIL", 0,0),
                ((y==0)? new Melee("CLUB",0,0): new Ranged("BOW",0,0)));
                enemyCharacters.add(new JButton(enemy[i].getName()));
                enemyCharacters.get(i).setIcon(new ImageIcon("ogre.gif"));
            }
            else if(x==1){
                enemy[i] = new Troll(names[i],random.nextInt(51),
                new Armor("CHEST PLATE", 0,0),
                ((y==0)? new Melee("SPEAR",0,0): new Ranged("STONE THROWER",0,0)));
                enemyCharacters.add(new JButton(enemy[i].getName()));
                enemyCharacters.get(i).setIcon(new ImageIcon("troll.gif"));
            }
            else if(x==2){
                enemy[i] = new Drowner(names[i],random.nextInt(51),
                new Armor("LEATHER ARMOR", 0,0),
                ((y==0)? new Melee("SWORD",0,0): new Ranged("LASSO",0,0)));
                enemyCharacters.add(new JButton(enemy[i].getName()));
                enemyCharacters.get(i).setIcon(new ImageIcon("drowner.gif"));
            }
            else{
                enemy[i] = new Dragon(names[i],random.nextInt(51),
                new Armor("PLATEBODY", 0,0),
                ((y==0)? new Melee("CLAWS",0,0): new Ranged("FIRE",0,0)));
                enemyCharacters.add(new JButton(enemy[i].getName()));
                enemyCharacters.get(i).setIcon(new ImageIcon("dragon.gif"));
            }
        }
    }
    
    //This class's action listener
    public void actionPerformed(ActionEvent evt){
        Object source = evt.getSource();
        if(gameTiles.contains(source)){
            window.dispose();
            execute();
        }
        else if(enemyCharacters.contains(source)){
            int posInArray = enemyCharacters.indexOf(source);
            currentEnemy = enemy[posInArray];
            /*Polymorphism as all enemies have seperate classes with
              different scout methods, the method in the superclass
              is declared abstract*/
            enemyLabel.setText(currentEnemy.scout());
            attack.addActionListener(this);
            attack.setVisible(true);
        }
        else if(source==attack){
            String message = "";
            boolean alive = true;
            //PlayerDiedException
            try{
                message = player.attack(currentEnemy);
            }
            catch(PlayerDiedException playersDead){
                alive = false;
                window.dispose();
                window = new JFrame();
                message = (playersDead.toString()).replace("PlayerDiedException:","");
                JLabel l = new JLabel("<html>" + message + "</html>");
                window.setLayout(new FlowLayout());
                window.setSize(1500,800);
                window.add(l);
                menu = new JButton("MENU");
                menu.addActionListener(this);
                window.add(menu);
                window.setVisible(true);
                window.addWindowListener(new WindowCloser());
            }
            if(alive==true){
                messageLabel.setText("<html>" + message + "</html>");
                window.dispose();
                execute();
            }
        }
        else if(source==changeArmor){
            player.wieldArmor();
            armorLabel.setText((player.getArmor()).printDetails());
        }
        else if(source==changeWeapon){
            player.wieldWeapon();
            /*printDetails is also an example of polymorphism
              as printDetails in the subclasses overrides printDetails
              in the super class*/
            weaponLabel.setText((player.getWeapon()).printDetails());
        }
        else if(source==playerCharacter){
            try{
                (new IO()).save(player);
            }
            /*ActionListener doesn't allow an exception to be throwed
               so my IOException has to be throwed as a RunTimeException*/
            catch(IOException ignore){
                throw new RuntimeException(ignore);
            }
            window.dispose();
            messageLabel.setText("SAVED!");
            execute();
        }
        else if(source==menu){
            window.dispose();
            new Menu();
        }
    }
}