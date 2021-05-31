import javax.swing.*;
import java.util.*;

class alienPetProgram2
{
    public static void main (String[] p)
    {
        Welcome();
        ArrayList<pets> allAdoptedPets = newPet();
        allAdoptedPets = sortArray(allAdoptedPets);
        pets petID = chooseFromAdoptedPets(allAdoptedPets);
        System.out.println(petID);
        System.exit(0);
    }

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

    public static ArrayList<pets> newPet()
    {
        pets pet1 = new pets();
        pets pet2 = new pets();
        pets pet3 = new pets();
        pets pet4 = new pets();
        pets pet5 = new pets();
        pets pet6 = new pets();
        ArrayList<pets> allAdoptedPets = new ArrayList<pets>();
        allAdoptedPets.add(pet1);
        allAdoptedPets.add(pet2);
        allAdoptedPets.add(pet3);
        allAdoptedPets.add(pet4);
        allAdoptedPets.add(pet5);
        allAdoptedPets.add(pet6);
        int userInput = 0;
        int petPositionInArray = -1;
        pets petID;
        while ((userInput == 0) && (petPositionInArray < 5))
        {
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
        return allAdoptedPets;
    }

    public static void selectPetType(pets petID)
    {
        Object[] buttonNames = {"Loth Cat", "Loth Wolf"};
        int userInput = buttonInput(buttonNames, "Great!\nWhat kind of alien pet"
        + " would you like to adopt?", "Choose Pet Type");
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

    public static void generateRandomHunger(pets petID)
    {
        int petHunger = random(6);
        setPetHunger(petID, petHunger);
        return;
    }

    public static void generateRandomThirst(pets petID)
    {
        int petThirst = random(6);
        setPetThirst(petID, petThirst);
        return;
    }

    public static void generateRandomTiredness(pets petID)
    {
        int petTiredness = random(6);
        setPetTiredness(petID, petTiredness);
        return;
    }

    public static void calculateMoodScore(pets petID)
    {
        int petHunger = getHunger(petID);
        int petThirst = getThirst(petID);
        int petTiredness = getTiredness(petID);
        int petMoodScore = petHunger + petThirst + petTiredness;
        setPetMoodScore(petID, petMoodScore);
        return;
    }

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

    public static int adoptMoreQuestion()
    {
        int userInput = confirmButtonInput("Would you like to adopt another"
        + "alien pet?", "Alien Pet Adoption Center");
        return userInput;
    }

    public static ArrayList<pets> sortArray(ArrayList<pets> allAdoptedPets)
    {
        for(int i = 0; i < allAdoptedPets.size(); i++)
        {
            if (allAdoptedPets.get(i) == null)
            {
                allAdoptedPets.remove(i);
            }
        }
        return allAdoptedPets;
    }

    public static pets chooseFromAdoptedPets(ArrayList<pets> allAdoptedPets)
    {
        Object[] buttonNames = new String[allAdoptedPets.size()];
        for(int i = 0; i < allAdoptedPets.size(); i++)
        {
            buttonNames[i] = allAdoptedPets.get(i).name;
        }
        int userInput = buttonInput(buttonNames, "Which alien pet would you"
        + " like to care for right now?", "Choose From Adopted Alien Pets");
        pets petID = allAdoptedPets.get(userInput);
        return petID;
    }

    public static void setPetType(pets petID, String petType)
    {
        petID.type = petType;
        return;
    }

    public static void setPetGender(pets petID, String petGender)
    {
        petID.gender = petGender;
        return;
    }

    public static void setPetName(pets petID, String petName)
    {
        petID.name = petName;
        return;
    }

    public static void setPetHunger(pets petID, int petHunger)
    {
        petID.hunger = petHunger;
        return;
    }

    public static void setPetThirst(pets petID, int petThirst)
    {
        petID.thirst = petThirst;
        return;
    }

    public static void setPetTiredness(pets petID, int petTiredness)
    {
        petID.tiredness = petTiredness;
        return;
    }

    public static void setPetMoodScore(pets petID, int petMoodScore)
    {
        petID.moodScore = petMoodScore;
        return;
    }

    public static void setPetMood(pets petID, String petMood)
    {
        petID.mood = petMood;
        return;
    }

    public static String getType(pets petID)
    {
        String petType = petID.type;
        return petType;
    }

    public static String getGender(pets petID)
    {
        String petGender = petID.gender;
        return petGender;
    }

    public static int getHunger(pets petID)
    {
        int petHunger = petID.hunger;
        return petHunger;
    }

    public static int getThirst(pets petID)
    {
        int petThirst = petID.thirst;
        return petThirst;
    }

    public static int getTiredness(pets petID)
    {
        int petTiredness = petID.tiredness;
        return petTiredness;
    }

    public static int getMoodScore(pets petID)
    {
        int petMoodScore = petID.moodScore;
        return petMoodScore;
    }

    public static String input(String question)
    {
        String userInput = JOptionPane.showInputDialog(question);
        return userInput;
    }

    public static void print(String message)
    {
        JOptionPane.showMessageDialog(null, message);
        return;
    }

    public static int random(int range)
    {
        int randomNumber = (int)((Math.random()*range) + 1);
        return randomNumber;
    }

    public static int buttonInput(Object[] namesOfButtons, String question,
    String title)
    {
        int userInput = JOptionPane.showOptionDialog(null, question, title,
        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
        namesOfButtons, namesOfButtons[0]);
        return userInput;
    }

    public static int confirmButtonInput(String question, String title)
    {
        int userInput = JOptionPane.showConfirmDialog(null, question, title,
        JOptionPane.YES_NO_OPTION);
        return userInput;
    }


}

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
