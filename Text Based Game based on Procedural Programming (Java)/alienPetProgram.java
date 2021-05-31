/*AUTHOR: RAI MUHAMMAD IBRAHIM BADAR
This program simulates an alien adoption center called APAC. It allows users
to adopt upto six pets, all pets can be given name, the prgram then gives you
multiple activities to do with the pets selected. For example you can feed
them, give them a drink, put them to sleep or play with them. According to
the activities you do with your pet there traits change, for example hunger,
thirst and tiredness levels. The pets also have a mood that is dependent on
the hunger, thirst and tiredness levels.*/

import javax.swing.*;//Imports javax swing for JOptionPane's
import java.util.*;//Imports java util for Array lists
import java.io.*;//Imports java input and output streams for file IO

class alienPetProgram
{//Start of class alienPetProgram

    /*Main method runs the welcome procedure, it creates a list array where
    all the pet records will be stored, then it runs the game, where you
    can choose from existing pets and play with them. Once the game ends it
    asks the user if they would like to repeat the game. If the user says yes
    it reruns all the appropriate procedures and functions using a while loop*/
    public static void main (String[] p) throws IOException
    {
        Welcome();
        int gameType = chooseGameType();
        if (gameType == 0)
        {
            newGame();
        }
        else if (gameType == 1)
        {
            continueGame();
        }
        else
        {
            System.exit(0);
        }
        System.exit(0);
    }


    /*This procedure asks user if they would like to adopt a pet. If they say
    yes then the game continues, however if they say no the games shows an
    appropriate dialogue and quits*/
    public static void Welcome()
    {
        int userInput1 = confirmButtonInput(
        "Welcome to APAC, the Alien Pet Adoption Center! \nOur goal is to " +
        "ensure all our pets are in the right hands!\nIt can be hard taking" +
        " care of an alien!\nAre you up to the task?",
        "Alien Pet Adoption Center");
        if (userInput1 == JOptionPane.NO_OPTION)
        {
            print("Okay, thanks for visiting!\nPlease come back when you are" +
            " ready to adopt!");
            System.exit(0);
        }
        else if (userInput1 == JOptionPane.CLOSED_OPTION)
        {
            System.exit(0);
        }
        else
        {
            return;
        }
    }

    /*Allows user to choose if they want a new game or want to load an old game*/
    public static int chooseGameType()
    {
        String[] buttonNames = {"New Game", "Load Game"};
        int gameType = buttonInput(buttonNames, "Please select from the" +
        " following", "APAC");
        return gameType;
    }

    //Runs all methods in a new game
    public static void newGame() throws IOException
    {
        ArrayList<pets> allAdoptedPets = newPet();
        gameRun(allAdoptedPets);
        saveFile(allAdoptedPets, "");
        return;
    }

    //Runs all methods in a loaded game
    public static void continueGame() throws IOException
    {
        String selectedGame = SelectLoadGame();
        ArrayList<pets> allAdoptedPets = loadGame(selectedGame);
        gameRun(allAdoptedPets);
        saveFile(allAdoptedPets, selectedGame);
        return;
    }

    /*All methods in the game. Had to be a seperate ethod so it could be called
    for a new or loaded game*/
    public static void gameRun(ArrayList<pets> allAdoptedPets)
    {
        int userInput = 0;//while loop value set as 0 so it runs the first time
        while (userInput == 0)
        {
            pets[] allAdoptedPetsAngerSort =
            sortAccordingToAnger(allAdoptedPets);
            pets petID = chooseFromAdoptedPets(allAdoptedPetsAngerSort);
            displayPetInformation(petID);
            petCare(petID);
            userInput = questionTakeCareOfAnotherPet();
        }
        return;
    }

    /*This function creates 6 records of the abstract data type pets, this can
    be potentially used to stoe upto 6 pets information. All these records are
    stored inside an array list. An Array list is used over an array as it
    allows you to edit the data inside. This function asks the user to adopt
    a new pet. This runs multiple procedures to allow the user to select their
    pets characteristics. You can choose upto 6 pets at a time. After 6 pet
    slots have been filled the program lets the user know they can't adopt any
    more pets and continues to the game. It also calls the sortArray function
    and returns it's value back to the main*/
    public static ArrayList<pets> newPet()
    {
        pets pet1 = new pets();//New records of type pet
        pets pet2 = new pets();
        pets pet3 = new pets();
        pets pet4 = new pets();
        pets pet5 = new pets();
        pets pet6 = new pets();
        ArrayList<pets> allAdoptedPets = new ArrayList<pets>();//Array list
        allAdoptedPets.add(pet1);//Adds record to array list
        allAdoptedPets.add(pet2);
        allAdoptedPets.add(pet3);
        allAdoptedPets.add(pet4);
        allAdoptedPets.add(pet5);
        allAdoptedPets.add(pet6);
        int userInput = 0;
        int petPositionInArray = -1;
        pets petID;
        int numberOfPets = 0;
        while ((userInput == 0) && (petPositionInArray < 5))
        {
            numberOfPets = numberOfPets + 1;
            petPositionInArray = petPositionInArray + 1;
            petID = allAdoptedPets.get(petPositionInArray);
            selectPetType(petID);
            generateRandomGender(petID);
            namePet(petID);
            generateRandomHunger(petID);
            generateRandomThirst(petID);
            generateRandomTiredness(petID);
            calculateMoodScore(petID);
            calculateMood(petID);
            if(petPositionInArray < 5)
            {
                userInput = adoptMoreQuestion();
            }
        }
        if (petPositionInArray == 5)
        {
            print("You can not adopt anymore Alien pets!\n You already need"
            + " to take care of six!");
        }
        allAdoptedPets = sortArray(allAdoptedPets, numberOfPets);
        return allAdoptedPets;//returns array list
    }

    /*This function uses a sort Algorithm to sort all the pets into an array
    according to there mood scores. It then returns this array so it can be
    used in the chooseFromAdoptedPets function*/
    public static pets[] sortAccordingToAnger(ArrayList<pets> allAdoptedPets)
    {
        pets[] allAdoptedPetsAngerSort = new pets[allAdoptedPets.size()];
        for (int i=0; i<allAdoptedPets.size(); i++)
        {
            allAdoptedPetsAngerSort[i] = allAdoptedPets.get(i);
        }
        //Sort Algorithm
        for (int i=(allAdoptedPetsAngerSort.length-1); i>0; i--)
        {
            for (int j=0; j<i; j++)
            {
                if (allAdoptedPetsAngerSort[j].moodScore>
                allAdoptedPetsAngerSort[j+1].moodScore)
                {
                    pets temporaryStore = allAdoptedPetsAngerSort[j+1];
                    allAdoptedPetsAngerSort[j+1] = allAdoptedPetsAngerSort[j];
                    allAdoptedPetsAngerSort[j] = temporaryStore;
                }
            }
        }
        return allAdoptedPetsAngerSort;
    }

    /*This function puts all the pet names in an String string and uses it as
    buttons which the user can click on to choose which adopted pet they
    want to take care of first. It then gets the integer value of that button
    and returns the corresponding record assossiated ro that pet name, this is
    later passed on to other methods as an argument to be used.*/
    public static pets chooseFromAdoptedPets(pets[] allAdoptedPets)
    {
        String[] buttonNames = new String[allAdoptedPets.length];
        for(int i = 0; i < allAdoptedPets.length; i++)
        {
            buttonNames[i] = allAdoptedPets[i].name + "(" +
            allAdoptedPets[i].moodScore + ")";
        }
        int userInput = buttonInput(buttonNames, "Which alien pet would you"
        + " like to care for right now? \n (They are sorted by their mood" +
        " scores!)", "Choose From Adopted Alien Pets");
        pets petID = allAdoptedPets[userInput];
        return petID;//returns the name of record assossiated to users input
    }

    /*This uses the pet id to get all the information related to the pet chosen
    by the user and prints is out via JOptionPane*/
    public static void displayPetInformation(pets petID)
    {
        print("Pet Details!\nType: " + getType(petID)
        + "\nGender: " + getGender(petID)
        + "\nName: " + getName(petID)
        + "\nCurrent Hunger Level(1-6): " + getHunger(petID)
        + "\nCurrent Thirst Level(1-6): " + getThirst(petID)
        + "\nCurrent Tiredness Level(1-6): " + getTiredness(petID)
        + "\nCurrent Mood Score(1-18): " + getMoodScore(petID)
        + "\nCurrent Mood: " + getMood(petID));
    }

    /*This part of the code is where the user can actually start to care for
    their pet. The user can feed, give a drink, put to sleep or play with the
    pet. Each activity effects the pets hunger, thirst, tiredness and mood
    scores. It is possible to have a really good mood score but bad health
    scores if you keep playing with the pet*/
    public static void petCare(pets petID)
    {
        print("Time to take care of your pet!");
        String[] buttonNames = {"Feed it", "Give it a Drink", "Sing to it",
        "Play with it"};//Array stores names of buttons user can click on
        int hunger = getHunger(petID);
        int thirst = getThirst(petID);
        int tiredness = getTiredness(petID);
        int moodScore = getMoodScore(petID);
        for (int i=1; i<3; i++)
        {
            int userInput = buttonInput(buttonNames,
            "What would you like to do to your pet?",
            "Pet Care (Level " + i + ")");
            if (userInput == 0)
            {
                final String hungerMessage = "You feed your alien pet and" +
                " the hunger level decreased by 1!";
                print(hungerMessage);
                if (hunger > 1)//Makes sure the scores stay in range
                {
                    setPetHunger(petID, hunger - 1);
                    calculateMoodScore(petID);
                }
            }
            else if (userInput == 1)
            {
                final String thirstMessage = "You give your alien pet a drink"
                + " and the thirst level decreased by 1!";
                print(thirstMessage);
                if (thirst > 1)
                {
                    setPetThirst(petID, thirst - 1);
                    calculateMoodScore(petID);
                }
            }
            else if (userInput == 2)
            {
                final String tirednessMessage = "You sing to your alien pet"
                + " and it goes to sleep, the tiredness level decreased by 1!";
                print(tirednessMessage);
                if (tiredness > 1)
                {
                    setPetTiredness(petID, tiredness - 1);
                    calculateMoodScore(petID);
                }
            }
            else if (userInput == 3)
            {
                final String playMessage= "You play with your alien pet, "
                + "it seems much happier but playing seems to have used up"
                + " it's energy, the hunger, thirst and tiredness levels all"
                + " increase by 1";
                print(playMessage);
                if (hunger < 6)
                {
                    setPetHunger(petID, hunger + 1);
                }
                if (thirst < 6)
                {
                    setPetThirst(petID, thirst + 1);
                }
                if (tiredness < 6)
                {
                    setPetTiredness(petID, tiredness + 1);
                }
                if(moodScore > 3)
                {
                    setPetMoodScore(petID, moodScore - 3);
                }
                else if(moodScore > 2)
                {
                    setPetMoodScore(petID, moodScore - 2);
                }
                else if(moodScore > 1)
                {
                    setPetMoodScore(petID, moodScore - 1);
                }
            }
            else
            {
                System.exit(0);
            }
            calculateMood(petID);
            displayPetInformation(petID);
        }
        return;
    }

    /*Defines question called in main, asks if the user would like to continue
    the game and responds accordingly*/
    public static int questionTakeCareOfAnotherPet()
    {
        int userInput = confirmButtonInput("Would you like to take care of "
        + "another adopted pet?", "");
        return userInput;
    }

    /*This method saves the file. If the file is being resaved the fileName is passed as an
    argument. The method saves the number of pets first. Then line by line it writes all
    the fields in the record down. All files are saved in the Game Saves folder*/
    public static void saveFile(ArrayList<pets> allAdoptedPets, String fileName) throws IOException
    {
        int userInput = confirmButtonInput("Would you like to save the"
        + " game?", null);
        if (userInput == JOptionPane.NO_OPTION)
        {
            return;
        }
        else if (userInput == JOptionPane.CLOSED_OPTION)
        {
            System.exit(0);
        }
        else
        {
            File folderPath = new File("Game Saves");
            if((!folderPath.exists()) && (!folderPath.isDirectory()))
            {
                folderPath.mkdir();
            }
            File path = new File(fileName);
            if (fileName.equals(""))
            {
                fileName = input("What would you like to call this save?");
                path = new File(fileName);
                while(path.exists())
                {
                    fileName = input("Sorry this name is not available! Please "
                    + "choose another name!");
                    path = new File(fileName);
                }
            }
            OutputStream newSave = new FileOutputStream(folderPath + "/" + path);
            newSave.write(String.valueOf(allAdoptedPets.size()).getBytes());
            for(int i = 0; i<allAdoptedPets.size(); i++)
            {
                newSave.write(("\n").getBytes());
                newSave.write(allAdoptedPets.get(i).type.getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(allAdoptedPets.get(i).name.getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(allAdoptedPets.get(i).gender.getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(String.valueOf(allAdoptedPets.get(i).hunger).getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(String.valueOf(allAdoptedPets.get(i).thirst).getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(String.valueOf(allAdoptedPets.get(i).tiredness).getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(String.valueOf(allAdoptedPets.get(i).moodScore).getBytes());
                newSave.write(("\n").getBytes());
                newSave.write(allAdoptedPets.get(i).mood.getBytes());
            }
            newSave.close();
        }
    }

    /*This method allows user to select and load a previously saved game. First it reads all
    the names of the files in the game saves folder and puts them in an array. It uses this
    array as buttons to let the user choose. Then it returns the name of file selected*/
    public static String SelectLoadGame() throws IOException
    {
        File path = new File("Game Saves");
        String selectedGamesName = "";
        if ((!path.exists()) && (!path.isDirectory()))
        {
            int newGameQuestion = confirmButtonInput("Sorry!\nThere are no saved games!\nWould you"
            + " like to start a new game?\n(Selecting no will quit the game!)", "No saved games" +
            " found");
            if (newGameQuestion == JOptionPane.YES_OPTION)
            {
                newGame();
            }
            else
            {
                System.exit(0);
            }
        }
        else
        {
            String[] existingGameSaves = path.list();
            int selectedGame = buttonInput(existingGameSaves, "Which game would you like to load?",
            "Load Game");
            selectedGamesName = existingGameSaves[selectedGame];
        }
        return selectedGamesName;
    }

    /*This method loads the selcted file and then puts the data in the correct record fields.
    Firstly it reads the first line and uses the integer on the first line to determine the
    number of records. Using this it makes an array with the correct number of records inside
    it. Then it continues to read the file line by line and uses setter methods to save the
    correct data into the correct record and the correct field. It then returns the array list
    including all these records so the game can be run*/
    public static ArrayList<pets> loadGame(String selectedGame) throws IOException
    {
        pets pet1 = new pets();//New records of type pet
        pets pet2 = new pets();
        pets pet3 = new pets();
        pets pet4 = new pets();
        pets pet5 = new pets();
        pets pet6 = new pets();
        pets[] emptyPetArray = {pet1, pet2, pet3, pet4, pet5, pet6};
        InputStream input = new FileInputStream("Game Saves" + "/" + selectedGame);
        Reader reader = new InputStreamReader(input);
        int dataForNumberOfPets = reader.read();
        char dataCharactersForNumberOfPets = (char) dataForNumberOfPets;
        String dataStringForNumberOfPets = "";
        while (!(dataCharactersForNumberOfPets == '\n'))
        {
            dataStringForNumberOfPets = dataStringForNumberOfPets + dataCharactersForNumberOfPets;
            dataForNumberOfPets = reader.read();
            dataCharactersForNumberOfPets = (char) dataForNumberOfPets;
        }
        int numberOfPets = Integer.parseInt(dataStringForNumberOfPets);
        pets[] allAdoptedPetsArray = new pets[numberOfPets];
        for (int i = 0; i<numberOfPets; i++)
        {
            allAdoptedPetsArray[i] = emptyPetArray[i];
        }
        ArrayList<pets> allAdoptedPets = new ArrayList<pets>(Arrays.asList(allAdoptedPetsArray));
        for (int i = 0; i<numberOfPets; i++)
        {
            //Type
            int typeData = reader.read();
            char typeDataCharacters = (char) typeData;
            String typeDataString = "";
            while (!(typeDataCharacters == '\n'))
            {
                typeDataString = typeDataString + typeDataCharacters;
                typeData = reader.read();
                typeDataCharacters = (char) typeData;
            }
            setPetType(allAdoptedPets.get(i), typeDataString);
            //Name
            int nameData = reader.read();
            char nameDataCharacters = (char) nameData;
            String nameDataString = "";
            while (!(nameDataCharacters == '\n'))
            {
                nameDataString = nameDataString + nameDataCharacters;
                nameData = reader.read();
                nameDataCharacters = (char) nameData;
            }
            setPetName(allAdoptedPets.get(i), nameDataString);
            //Gender
            int genderData = reader.read();
            char genderDataCharacters = (char) genderData;
            String genderDataString = "";
            while (!(genderDataCharacters == '\n'))
            {
                genderDataString = genderDataString + genderDataCharacters;
                genderData = reader.read();
                genderDataCharacters = (char) genderData;
            }
            setPetGender(allAdoptedPets.get(i), genderDataString);
            //Hunger
            int hungerData = reader.read();
            char hungerDataCharacters = (char) hungerData;
            String hungerDataString = "";
            while (!(hungerDataCharacters == '\n'))
            {
                hungerDataString = hungerDataString + hungerDataCharacters;
                hungerData = reader.read();
                hungerDataCharacters = (char) hungerData;
            }
            int hungerDataInteger = Integer.parseInt(hungerDataString);
            setPetHunger(allAdoptedPets.get(i), hungerDataInteger);
            //Thirst
            int thirstData = reader.read();
            char thirstDataCharacters = (char) thirstData;
            String thirstDataString = "";
            while (!(thirstDataCharacters == '\n'))
            {
                thirstDataString = thirstDataString + thirstDataCharacters;
                thirstData = reader.read();
                thirstDataCharacters = (char) thirstData;
            }
            int thirstDataInteger = Integer.parseInt(thirstDataString);
            setPetThirst(allAdoptedPets.get(i), thirstDataInteger);
            //Tiredness
            int tirednessData = reader.read();
            char tirednessDataCharacters = (char) tirednessData;
            String tirednessDataString = "";
            while (!(tirednessDataCharacters == '\n'))
            {
                tirednessDataString = tirednessDataString + tirednessDataCharacters;
                tirednessData = reader.read();
                tirednessDataCharacters = (char) tirednessData;
            }
            int tirednessDataInteger = Integer.parseInt(tirednessDataString);
            setPetTiredness(allAdoptedPets.get(i), tirednessDataInteger);
            //Mood Score
            int moodScoreData = reader.read();
            char moodScoreDataCharacters = (char) moodScoreData;
            String moodScoreDataString = "";
            while (!(moodScoreDataCharacters == '\n'))
            {
                moodScoreDataString = moodScoreDataString + moodScoreDataCharacters;
                moodScoreData = reader.read();
                moodScoreDataCharacters = (char) moodScoreData;
            }
            int moodScoreDataInteger = Integer.parseInt(moodScoreDataString);
            setPetMoodScore(allAdoptedPets.get(i), moodScoreDataInteger);
            //Mood
            int moodData = reader.read();
            char moodDataCharacters = (char) moodData;
            String moodDataString = "";
            if (i == (numberOfPets - 1))
            {
                while (moodData != -1)
                {
                    moodDataString = moodDataString + moodDataCharacters;
                    moodData = reader.read();
                    moodDataCharacters = (char) moodData;
                }
            }
            else
            {
                while (!(moodDataCharacters == '\n'))
                {
                    moodDataString = moodDataString + moodDataCharacters;
                    moodData = reader.read();
                    moodDataCharacters = (char) moodData;
                }
            }
            setPetMood(allAdoptedPets.get(i), moodDataString);
        }
        reader.close();
        input.close();
        return allAdoptedPets;
    }

    /*Allows users to select the type of their pet, this function is called in
    the function newPet. It uses buttons as input for better input validation*/
    public static void selectPetType(pets petID)
    {
        String[] buttonNames = {"Loth Cat", "Loth Wolf"};
        int userInput = buttonInput(buttonNames, "Great!\nWhat kind of alien" +
        " pet would you like to adopt?", "Choose Pet Type");
        String petType;
        if (userInput == 0)
        {
            petType = "Loth Cat";
        }
        else if (userInput == 1)
        {
            petType = "Loth Wolf";
        }
        else
        {
            petType = null;
            System.exit(0);
        }
        setPetType(petID, petType);
        return;
    }

    /*Function called in method newPet. It generates a random gender when a new
    pet is adopted*/
    public static void generateRandomGender(pets petID)
    {
        int genderNumber = random(2);
        String petGender;
        if (genderNumber == 1)
        {
            petGender = "Female";
        }
        else
        {
            petGender = "Male";
        }
        setPetGender(petID, petGender);
        return;
    }

    /*Another function called in newPet, Allows the user to name their pet*/
    public static void namePet(pets petID)
    {
        String petGender = getGender(petID);
        String petType = getType(petID);
        print("Nice! We have the perfect " + petGender.toLowerCase()
        + " " + petType + " available for you to adopt!");
        String petName;
        if (petGender.equals("Female"))
        {
            petName = input("What would you like to call her?");
        }
        else
        {
            petName = input("What would you like to call him");
        }
        print("Congrajulations, Enjoy your new home " + petName + " !");
        setPetName(petID, petName);
        return;
    }

    /*Generates a random hunger score when a new pet is adopted*/
    public static void generateRandomHunger(pets petID)
    {
        int petHunger = random(6);
        setPetHunger(petID, petHunger);
        return;
    }

    /*Generates a random thirst score when a new pet is adopted*/
    public static void generateRandomThirst(pets petID)
    {
        int petThirst = random(6);
        setPetThirst(petID, petThirst);
        return;
    }

    /*Generates a random tiredness score when a new pet is adopted*/
    public static void generateRandomTiredness(pets petID)
    {
        int petTiredness = random(6);
        setPetTiredness(petID, petTiredness);
        return;
    }

    /*Uses the health scores and adds them all up to calculate the mood score*/
    public static void calculateMoodScore(pets petID)
    {
        int petHunger = getHunger(petID);
        int petThirst = getThirst(petID);
        int petTiredness = getTiredness(petID);
        int petMoodScore = petHunger + petThirst + petTiredness;
        setPetMoodScore(petID, petMoodScore);
        return;
    }

    /*Assigns an appropriate String value called mood to the pet depending on
    it's mood score*/
    public static void calculateMood(pets petID)
    {
        int petMoodScore = getMoodScore(petID);
        String petMood;
        if (petMoodScore<7 && petMoodScore>0)
        {
            petMood = "Happy";
        }
        else if (petMoodScore<13 && petMoodScore>6)
        {
            petMood = "Calm";
        }
        else
        {
            petMood = "Dangerous";
        }
        setPetMood(petID, petMood);
        return;
    }

    /*This function is also called during the method new pet. It run sif the
    amount of adopted pets are less than six. It allows the user to adopt more
    pets*/
    public static int adoptMoreQuestion()
    {
        int userInput = confirmButtonInput("Would you like to adopt another"
        + " alien pet?", "Alien Pet Adoption Center");
        return userInput;
    }

    /*In case the user doesn't adopt six pets I needed to make sure that all
    six records dont show up in users pet inventory. So this makes a new
    array list and adds the records of only those pets that were named by the
    user. This is done by calculating the number of times the user input a pet
    name and then using this integer in a loop to add that many existing
    records in the new array. This new array is then returned.*/
    public static ArrayList<pets> sortArray(ArrayList<pets> allAdoptedPets,
    int numberOfPets)
    {
        ArrayList<pets> allAdoptedPetsSorted = new ArrayList<pets>();
        for(int i = 0; i < numberOfPets; i++)
        {
            allAdoptedPetsSorted.add(allAdoptedPets.get(i));
        }
        return allAdoptedPetsSorted;
    }

    /*A type of accessor method. A setter method for pet type*/
    public static void setPetType(pets petID, String petType)
    {
        petID.type = petType;
        return;
    }

    /*A type of accessor method. A setter method for pet gender*/
    public static void setPetGender(pets petID, String petGender)
    {
        petID.gender = petGender;
        return;
    }

    /*A type of accessor method. A setter method for pet name*/
    public static void setPetName(pets petID, String petName)
    {
        petID.name = petName;
        return;
    }

    /*A type of accessor method. A setter method for pet hunger*/
    public static void setPetHunger(pets petID, int petHunger)
    {
        petID.hunger = petHunger;
        return;
    }

    /*A type of accessor method. A setter method for pet thirst*/
    public static void setPetThirst(pets petID, int petThirst)
    {
        petID.thirst = petThirst;
        return;
    }

    /*A type of accessor method. A setter method for pet tiredness*/
    public static void setPetTiredness(pets petID, int petTiredness)
    {
        petID.tiredness = petTiredness;
        return;
    }

    /*A type of accessor method. A setter method for pet mood score*/
    public static void setPetMoodScore(pets petID, int petMoodScore)
    {
        petID.moodScore = petMoodScore;
        return;
    }

    /*A type of accessor method. A setter method for pet mood*/
    public static void setPetMood(pets petID, String petMood)
    {
        petID.mood = petMood;
        return;
    }

    /*A type of accessor method. A getter method for pet type*/
    public static String getType(pets petID)
    {
        String petType = petID.type;
        return petType;
    }

    /*A type of accessor method. A getter method for pet gender*/
    public static String getGender(pets petID)
    {
        String petGender = petID.gender;
        return petGender;
    }

    /*A type of accessor method. A getter method for pet name*/
    public static String getName(pets petID)
    {
        String petName = petID.name;
        return petName;
    }

    /*A type of accessor method. A getter method for pet hunger*/
    public static int getHunger(pets petID)
    {
        int petHunger = petID.hunger;
        return petHunger;
    }

    /*A type of accessor method. A getter method for pet thirst*/
    public static int getThirst(pets petID)
    {
        int petThirst = petID.thirst;
        return petThirst;
    }

    /*A type of accessor method. A getter method for pet tiredness*/
    public static int getTiredness(pets petID)
    {
        int petTiredness = petID.tiredness;
        return petTiredness;
    }

    /*A type of accessor method. A getter method for pet mood score*/
    public static int getMoodScore(pets petID)
    {
        int petMoodScore = petID.moodScore;
        return petMoodScore;
    }

    /*A type of accessor method. A getter method for pet mood*/
    public static String getMood(pets petID)
    {
        String petMood = petID.mood;
        return petMood;
    }

    /*This function aloows a text input via JOptionPane*/
    public static String input(String question)
    {
        String userInput = JOptionPane.showInputDialog(question);
        return userInput;
    }

    /*This procedure allows output via the JOptionPane*/
    public static void print(String message)
    {
        JOptionPane.showMessageDialog(null, message);
        return;
    }

    /*This function calculates a random number between the range provided as
    an argument an returns it*/
    public static int random(int range)
    {
        int randomNumber = (int)((Math.random()*range) + 1);
        return randomNumber;
    }

    /*Allows buuton input via the JOptionPane. The name of the buttons, the
    question and the title of the JOptionPane window are passed in as
    arguments*/
    public static int buttonInput(String[] namesOfButtons, String question,
    String title)
    {
        int userInput = JOptionPane.showOptionDialog(null, question, title,
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
        namesOfButtons, namesOfButtons[0]);
        return userInput;
    }

    /*Allows yes or no button input via the JOptionPane. The question and the
    title of the window are passed inside as arguments*/
    public static int confirmButtonInput(String question, String title)
    {
        int userInput = JOptionPane.showConfirmDialog(null, question, title,
        JOptionPane.YES_NO_OPTION);
        return userInput;
    }
}//End class alienPetProgram

class pets//Creates an abstract data type called pets and stores records in it
{
    //All the fields of the records and their types are declared below
    String type;
    String name;
    String gender;
    int hunger;
    int thirst;
    int tiredness;
    int moodScore;
    String mood;
}
