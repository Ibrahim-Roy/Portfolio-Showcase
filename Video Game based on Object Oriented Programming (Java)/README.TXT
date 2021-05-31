Student name: Rai Muhammad Ibrahim Badar
Student number: 190071703

LEVEL YOU WISH THE MINI-PROJECT TO BE MARKED FOR: 3
LEVEL THREE

[Explain in three or four sentences how your code responds to a button being pressed (or other user interaction) in the GUI. Give file names and line numbers of where the code is that responds to user interaction.]
    
    In the class named "Run" the actionListener will execute
    
        The main game window again which will generate new enemies if any of 
        the map tiles are pressed, this allows the player to roam around on 
        the map and find new enemies (Line 309-312)
        
        The attack algorithm in the PC class if the the attck button is
        pressed (Line 323-350)
        
        The wieldArmor method in the class PC if the button "Change Armor" is
        pressed or the wieldWeapon button if "Change Weapon" is pressed, this
        allows the user to choose different armor or weapons as the name
        suggests (Line 351-354 & 355-360)
        
        The saveGame method in class IO if the button with the player Icon is
        pressed (Line 362-374)
        
        There are many other cases please browse the code files if interested
        
        
[List file names and line numbers of where exception handling is implemented.]

    The class PlayerDiedException is my self defined checked exception
    which handles the scenario where the player dies. In the class
    Players in the attck method (Line 197-203) in case the player is
    killed by a NPC the attack method throws a new PlayerDiedException
    This also carries the details of the players death as its argument.
    This checked exception must be caught which is done in the Run class
    (Line 327-344) here the program handles the GUI displayed when the
    player dies.
    
    As my game uses IO to allow players to save games it also deals with
    IOExceptions as showed in the class IO (Line 14,48,77,118-123) in the
    last example the actionListener is an inherited method which does
    not allow a checked exception to be throwed so instead it is caught
    and then throwed as a Runtime Exception. This is also used in other
    classes.

[List file names and line numbers of where Collection classes are used.]
    
    In the class IO I used ArrayLists for my GUI buttons as they need to
    be declared in the class so the actionListener has access to them.
    I use ArrayLists rather than arrays as all saved games are shown as
    buttons and the number of games saved may differ over time
    (Line 8,9,59-68)
    
    I use more ArrayList in other classes aswell such as Run, as number
    of enemies and number of map tiles can differ over time
    (Line 13,16,132-183)
    ETC

[Explain in no more than three sentences how your code implements file I/O in order to save its complete state.]
    
    When the player Icon is clicked the save methon in class IO is
    executed. This intiates an output stream to a new or existing file
    (which is overwritten) in the players name where all the variables
    relevant to the players state are saved line by line in an order
    which the input stream can also use to reopen the saved game. To
    save data to the file I used the OutputStream's write method and
    hence converted all data to bytes aswell.

ANYTHING ELSE

[If there is anything else that you wish the marker to be aware of, please describe if (briefly) here.]
    
    PLEASE DO NOT RESIZE WINDOW!!
    Please read the guide which is displayed when you run the game to
    understand all the controls and rules. At start you won't be able
    to wield good weapons because you need to level up first. The
    enemies you encounter may seem all a very high level and may keep
    killing you this is because they are programmed to appear randomly,
    please keep clicking the grass tiles and scouting enemies because
    low level ones do appear. Do not exit the game on any window as it
    will quit without saving. There is no autosave so click the player
    icon to save.