/* AUTHOR: RAI MUHAMMAD IBRAHIM BADAR
THIS IS A PROGRAM THAT LETS YOU ADOPT AND TAKE CARE OF ALIEN PETS*/

import javax.swing.*;

class alienPetProgram
{

    public static void main (String[] p)
    {
        Welcome();
        pets[] allPetNames = new pets[6];
        int arrayPosition = -1;
        allPetNames = newPet(allPetNames, arrayPosition);
        pets id = chooseFromAdoptedPets(allPetNames);
        displayInfo(id);
        petCare(id);
        /////////////////////////////////////////////////////
        System.exit(0);
    }

    public static pets chooseFromAdoptedPets(pets[] allPetNames)
    {
        int userInput = buttons(allPetNames, "Which pet would you like to care"
        + " for right now?", "Welcome Home!");
        return allPetNames[userInput];
    }
    /*
    main calls all functions that ask the user to select a pet type,
    then a gender is generated randomly, next the user is told about the
    gender and asked to name there pet. Then a random number is generated
    that determines the pets hunger level. This random integer then also
    decides the pets mood state
    */
    public static pets newPet(pets[] allPetNames, int arrayPosition)
    {
        String petType = selectPetType();
        String petGender = randomGender();
        String petName = namePet(petGender, petType);
        int hungerLevel = randomHunger();
        int thirstLevel = randomThirst();
        int tirednessLevel = randomTiredness();
        int moodScore = calculateMoodScore(hungerLevel, thirstLevel,
        tirednessLevel);
        String moodState = calculateMood(moodScore);
        allPetNames[arrayPosition + 1] = saveInfo(petType, petGender, petName, hungerLevel,
        thirstLevel, tirednessLevel, moodScore, moodState);
        int morePets = JOptionPane.showConfirmDialog(null,
        "Would you like to adopt another alien"
        + " pet?", "Alien Pet Adoption Center", JOptionPane.YES_NO_OPTION);
        if (morePets == JOptionPane.YES_OPTION)
        {
            newPet(allPetNames, arrayPosition);
        }
        else if (morePets == JOptionPane.CANCEL_OPTION)
        {
            System.exit(0);
        }
        return allPetNames;

    }

    /*
    This method defines input which allows users to send inputs that can be
    detected. This makes the program more efficient as just this method is
    to be called, it also makes it easier to change all input methods.
    */
    public static String input(String question)
    {
        String userInput = JOptionPane.showInputDialog(question);
        return userInput;
    }
    /*
    This method defines print, it makes the code more efficient and allows
    all print methods to be changed easily if required.
    */
    public static void print(String message)
    {
        JOptionPane.showMessageDialog(null, message);
        return;
    }
    /*
    This defines a method random which generates a random number between a
    given range. This is used to generate pet gender and hunger level.
    */
    public static int random(int range)
    {
        int randomNum = (int)((Math.random()*range) + 1);
        return randomNum;
    }

    public static int buttons(Object[] buttonNames, String question,
    String title)
    {
        int userInput = JOptionPane.showOptionDialog(null, question, title,
        JOptionPane.YES_NO_CANCEL_OPTION ,JOptionPane.QUESTION_MESSAGE, null,
        buttonNames, buttonNames[0]);
        return userInput;
    }

    public static void Welcome()
    {
        int userInput1 = JOptionPane.showConfirmDialog(null,
        "Welcome to APAC, the Alien Pet Adoption Center! \nOur goal is to " +
        "ensure all our pets are in the right hands!\nIt can be hard taking" +
        " care of an alien!\nAre you up to the task?",
        "Alien Pet Adoption Center", JOptionPane.YES_NO_OPTION);
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

    public static String selectPetType()
    {
        Object[] buttonNames = {"Loth Cat", "Loth Wolf"};
        int userInput2 = buttons(buttonNames, "Great!\nWhat kind of alien pet"
        + " would you like to adopt?", "Choose Pet Type");
        String petType;
        if (userInput2 == 0)
        {
            petType = "Loth Cat";
        }
        else if (userInput2 == 1)
        {
            petType = "Loth Wolf";
        }
        else
        {
            petType = null;
            System.exit(0);
        }
        return petType;
    }
    /*
    This method introduces the user to the game, creating the scene and asking
    if the user wants to adopt a pet. If not the code exits otherwise it
    continues. It then asks user to select a pet type from the given options.
    If the user don't select they are given 3 chances to choose again after
    which they are kicked out of the game. At this point the user can only
    choose one pet. The type they choose is returned to be stored in a variable.
    */
    /*public static String selectPetType()
    {
        String wannaAdopt = input("Welcome to APAC, the Alien Pet Adoption" +
        " Center! \nOur goal is to ensure all our pets are in the right hands!"
        + "\nIt can be hard taking care of an alien!\nAre you up to the task?");
        String wannaAdoptL = wannaAdopt.toLowerCase();
        if (wannaAdoptL.contains("no") || wannaAdoptL.contains("nope")
        || wannaAdoptL.contains("nah"))
        {
            print("Okay, thanks for visiting!\nPlease come back when" +
            "you are ready to adopt!");
            System.exit(0);
        }
        print("Great!\nWhat kind of alien pet would you like to adopt:");
        for (int i=0; i<4; i++)
        {
            String petType = input("Loth Cat\nLoth Wolf");
            String petTypeL= petType.toLowerCase();
            if (petTypeL.contains("cat") && !petTypeL.contains("wolf"))
            {
                petType = "Loth Cat";
                return petType;
            }
            else if (petTypeL.contains("wolf") && !petTypeL.contains("cat"))
            {
                petType = "Loth Wolf";
                return petType;
            }
            else if ((petTypeL.contains("cat") && petTypeL.contains("wolf"))
            || petTypeL.contains("both"))
            {
                print("Please choose one for now!");
            }
            else
            {
                if (i!=3)
                {
                    print("That type of alien pet is currently unavailable");
                    print("Please choose from:");
                }
            }
        }
        print("You think this is a joke!\nStop wasting my time!"
        + "\n****GUARDS AT APAC THROW YOU OUT****");
        return null;
    }*/
    /*
    This randomly decides the gender of the pet and returns it.
    */
    public static String randomGender()
    {
        int randomNum = random(2);
        if (randomNum == 1)
        {
            String gender = "Female";
            return gender;
        }
        else
        {
            String gender = "Male";
            return gender;
        }
    }
    /*
    The user is displayed the type and gender, then they are asked to name
    the pet. The program then prints a congrajulations message.
    */
    public static String namePet(String gender, String type)
    {
        print("Nice! We have the perfect " + gender.toLowerCase()
        + " " + type + " available for you to adopt!");
        String petName;
        if (gender.equals("Female"))
        {
            petName = input("What would you like to call her?");
        }
        else
        {
            petName = input("What would you like to call him");
        }
        print("Congrajulations, Enjoy your new home " + petName + " !");
        print("You and your new " + gender.toLowerCase() + " " + type + " "
        + petName + ", take of to your home!");
        print("Welcome Home!");
        return petName;
    }
    /*
    This randomly creates the hunger level of the pet and returns it to be
    stored in a variable.
    */
    public static int randomHunger()
    {
        int hungerLevel = random(6);
        return hungerLevel;
    }

    public static int randomThirst()
    {
        int thirstLevel = random(6);
        return thirstLevel;
    }

    public static int randomTiredness()
    {
        int tirednessLevel = random(6);
        return tirednessLevel;
    }

    public static int calculateMoodScore(int hunger, int thirst, int tiredness)
    {
        int moodScore = hunger + thirst + tiredness;
        return moodScore;
    }
    /*
    This calculates the mood of the pet depending on it's hunger level and
    returns it to be stored in a variable.
    */
    public static String calculateMood(int moodScore)
    {
        String mood;
        if (moodScore<7 && moodScore>0)
        {
            mood = "Happy";
        }
        else if (moodScore<13 && moodScore>6)
        {
            mood = "Calm";
        }
        else
        {
            mood = "Dangerous";
        }
        return mood;
    }
    /*
    This uses all the variables in main and displays the pets type, name,
    gender, hunger level and mood. If the hunger level is maximum a message
    is printed out telling the user about thier pets state.
    */
    public static void displayInfo(pets id)
    {
        print("Pet Details:\nType: " + id.type
        + "\nGender: " + id.gender
        + "\nName: " + id.name
        + "\nCurrent Hunger Level(1-6): " + id.hunger
        + "\nCurrent Thirst Level(1-6): " + id.thirst
        + "\nCurrent Tiredness Level(1-6): " + id.tiredness
        + "\nCurrent Mood: " + id.mood
        + "\nCurrent Mood Score(1-18): " + id.moodScore);
    }

    public static pets saveInfo(String type, String gender, String name,
    int hunger, int thirst, int tiredness, int moodScore, String mood)
    {
        pets id = (pets)Convert.ChangeType(name, typeof(id));
        pets id = new pets();
        id.type = type;
        id.name = name;
        id.gender = gender;
        id.hunger = hunger;
        id.thirst = thirst;
        id.tiredness = tiredness;
        id.moodScore = moodScore;
        id.mood = mood;
        return id;
    }

    public static void petCare(pets id)
    {
        print("Time to take care of your pet!");
        Object[] buttonNames = {"Feed it", "Give it a Drink", "Sing to it",
        "Play with it"};
        for (int i=1; i<3; i++)
        {
            int userInput = buttons(buttonNames,
            "What would you like to do to your pet?",
            "Pet Care (Level " + i + ")");
            if (userInput == 0)
            {
                setHungerTo(id, -1);
            }
            else if (userInput == 1)
            {
                setThirstTo(id, -1);
            }
            else if (userInput == 2)
            {
                setTirednessTo(id, -1);
            }
            else if (userInput == 3)
            {
                setHungerTo(id, +1);
                setThirstTo(id, +1);
                setTirednessTo(id, +1);
            }
            else
            {
                System.exit(0);
            }
            displayInfo(id);
        }
        return;
    }

    public static void setHungerTo(pets id, int change)
    {
        while ((id.hunger + change) >=0
        && (id.hunger + change) <=5 )
        {
            id.hunger = id.hunger + change;
            recalculateMoodScore(id);
            recalculateMood(id);
            return;
        }
    }

    public static void setThirstTo(pets id, int change)
    {
        while ((id.thirst + change) >=0
        && (id.thirst + change) <=5 )
        {
            id.thirst = id.thirst + change;
            recalculateMoodScore(petNumber);
            recalculateMood(petNumber);
            return;
        }
    }

    public static void setTirednessTo(pets petNumber, int change)
    {
        while ((id.tiredness + change) >=0
        && (id.tiredness + change) <=5 )
        {
            id.tiredness = id.tiredness + change;
            recalculateMoodScore(petNumber);
            recalculateMood(petNumber);
            return;
        }
    }

    public static void recalculateMoodScore(pets petNumber)
    {
        id.moodScore = calculateMoodScore(id.hunger,
        id.thirst, id.tiredness);
        return;
    }

    public static void recalculateMood(pets petNumber)
    {
        id.mood = calculateMood(id.moodScore);
        return;
    }

}

/*This is a class making a record type pet, it's meant to store all the pets
details. Currently not in use!
*/
class pets
{
  String type;
  String name;
  String gender;
  int hunger;
  int thirst;
  int tiredness;
  int moodScore;
  String mood;
}
