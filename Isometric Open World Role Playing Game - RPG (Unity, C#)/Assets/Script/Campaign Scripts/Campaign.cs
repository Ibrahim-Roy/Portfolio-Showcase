using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Playables;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class Campaign : MonoBehaviour
{
    public Text dialogBox;
    public Text tutorialBox;
    public GameObject tools;
    public GameObject enemyDependent;
    public GameObject preyDependent;
    public GameObject sheepPrefab;

    private GameMasterMainWorld gameMaster;
    private Player player;
    private PlayableDirector cutscene;
    [SerializeField] private int campaignSequenceNumber;
    private bool running = false;
    private HostileNPC enemyDependentScript;
    private Prey preyDependentScript;

    private void Start()
    {
        gameMaster = GameObject.FindGameObjectWithTag("Game Master").GetComponent<GameMasterMainWorld>();
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<Player>();
        cutscene = GetComponent<PlayableDirector>();
        if(enemyDependent != null)
        {
            enemyDependentScript = enemyDependent.GetComponent<HostileNPC>();
        }
        if(preyDependent != null)
        {
            preyDependentScript = preyDependent.GetComponent<Prey>();
        }
    }

    private void Update()
    {
        int progressCounter = gameMaster.getProgressCounter();
        if (progressCounter == campaignSequenceNumber)
        {
            if(enemyDependentScript != null)
            {
                if(!enemyDependentScript.getAliveStatus())
                {
                    if(campaignSequenceNumber == 4)
                    {
                        if(!running)
                        {
                            StartCoroutine(cutscene4());
                        }
                    }
                }
            }
            if(preyDependentScript != null)
            {
                if(!preyDependentScript.getAliveStatus())
                {
                    if(campaignSequenceNumber == 7)
                    {
                        if(!running)
                        {
                            StartCoroutine(cutscene6());
                        }
                    }
                }
                else
                {
                    if(campaignSequenceNumber == 7)
                    {
                        gameMaster.setPlayerDestinationPosition(new Vector2(preyDependent.transform.position.x, preyDependent.transform.position.y));
                    }
                }
            }
            else
            {
                if(campaignSequenceNumber == 7)
                {
                    preyDependent = Instantiate(sheepPrefab, new Vector3(-0.25f, 20.33f, 0f), Quaternion.identity);
                    preyDependentScript = preyDependent.GetComponent<Prey>();
                }
            }
            if(campaignSequenceNumber == 8)
            {
                if(player.getRawMeat() > 0)
                {
                    if(!running)
                    {
                        StartCoroutine(cutscene7());
                    }
                }
            }
            // if(campaignSequenceNumber == 9)
            // {
            //     if(player.getMeat() > 0)
            //     {
            //         if(!running)
            //         {
            //             StartCoroutine(cutscene8());
            //         }
            //     }
            // }
        }
    }

    private void OnTriggerEnter2D(Collider2D other) {
        int progressCounter = gameMaster.getProgressCounter();
        if(other.gameObject.CompareTag("Player"))
        {
            if(progressCounter == campaignSequenceNumber)
            {
                if(campaignSequenceNumber == 0)
                {
                    if(!running)
                    {
                        StartCoroutine(cutscene1());
                    }
                }
                else if(campaignSequenceNumber == 1)
                {
                    gameMaster.setPlayerPosition(new Vector2(-16f,-13.5f));
                    SceneManager.LoadScene("Cave Scene");
                    savePlayerState();
                    Destroy(gameObject);
                }
                else if(campaignSequenceNumber == 2)
                {
                    if(!running)
                    {
                        StartCoroutine(cutscene2());
                    }
                }
                else if(campaignSequenceNumber == 3)
                {
                    if(!running)
                    {
                        StartCoroutine(cutscene3());
                    }
                }
                else if(campaignSequenceNumber == 5)
                {
                    gameMaster.setPlayerPosition(new Vector2(-6.5f, 21.5f));
                    SceneManager.LoadScene("Main World");
                    savePlayerState();
                    Destroy(gameObject);
                }
                else if(campaignSequenceNumber == 6)
                {
                    if(!running)
                    {
                        StartCoroutine(cutscene5());
                    }
                }
            }
        }
    }

    private void savePlayerState()
    {
        gameMaster.incrementProgressCounter();
        gameMaster.setPlayerDestinationPosition(player.getDestinationPosition());
        gameMaster.setEquippedItem(player.getEquippedItem());
        gameMaster.setHealth(player.getHealth());
        gameMaster.setHunger(player.getHunger());
        gameMaster.setWood(player.getWood());
        gameMaster.setStone(player.getStone());
        gameMaster.setRawFish(player.getRawFish());
        gameMaster.setRawMeat(player.getRawMeat());
        gameMaster.setArrows(player.getArrows());
        gameMaster.setFish(player.getFish());
        gameMaster.setMeat(player.getMeat());
        gameMaster.updateCraftFire(false);
    }

    private IEnumerator cutscene1()
    {
        running = true;
        yield return new WaitForSecondsRealtime(2f);
        cutscene.Play();
        dialogBox.text = "Where am I?";
        yield return new WaitForSecondsRealtime(1.5f);
        dialogBox.text = "What is this place?";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "I can't remember anything!";
        FindObjectOfType<AudioManager>().Play("howl");
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Is that a wolf howling!";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "I must find someplace to hide!";
        yield return new WaitForSecondsRealtime(3.5f);
        dialogBox.text = "That cave seems like a good place!";
        FindObjectOfType<AudioManager>().Play("quest");
        tutorialBox.text = "-Use WASD or Arrow keys to move.\n-The player faces towards the direction of your mouse cursor.\n-Follow the compass on the top right of the screen to go to the next objective.";
        yield return new WaitForSecondsRealtime(5f);
        gameMaster.setPlayerDestinationPosition(new Vector2(-6.5f, 21.5f));
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene2()
    {
        running = true;
        tools.SetActive(true);
        cutscene.Play();
        dialogBox.text = "Should be safe from the wolves here for a while.";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Huh, those tools look like they are in good condition.";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "I should pick them up, they should help me survive this place, whatever it is.";
        //FindObjectOfType<AudioManager>().Play("quest");
        tutorialBox.text = "-To pick up items in the world walk over them.";
        yield return new WaitForSecondsRealtime(3f);
        gameMaster.setPlayerDestinationPosition(new Vector2(-6.5f, 21.5f));
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene3()
    {
        running = true;
        tools.SetActive(false);
        player.setArrows(3);
        cutscene.Play();
        dialogBox.text = "How dare you touch my stuff";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Now you must die";
        tutorialBox.text = "-To equip the bow or the sword click the button on the right side of the HUD.\n-You can also use key 1 or 2 if shortcut keys are enabled in settings.\n-Aim the weapon with your mouse and shoot or swing with left mouseclick.\n-Keep an eye on your arrows displayed bottom left on the HUD.\nStay away from melee enemies to avoid taking damage.\n-Enemy health is displayed on top of them when they take damage.";
        Time.timeScale = 0;
        yield return new WaitForSecondsRealtime(3f);
        gameMaster.setPlayerDestinationPosition(new Vector2(-6.5f, 21.5f));
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene4()
    {
        running = true;
        cutscene.Play();
        dialogBox.text = "What kind of world is this!?";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Skeletons coming to life and all";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "I need to find some people and figure out how to get out of this place";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "But first I'm starving, I need to get some food!\nI should get out of this cave";
        tutorialBox.text = "-The player has basic hunger requirements that you need to satisfy by eating meat and fish\n-Meat can be hunted from animals and fish can be caught from rivers";
        yield return new WaitForSecondsRealtime(3f);
        gameMaster.setPlayerDestinationPosition(new Vector2(-16f,-13.3f));
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene5()
    {
        running = true;
        cutscene.Play();
        dialogBox.text = "Huh, what's that paper flying in the air";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "It's coming from the same direction I woke up at";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Maybe it followed the same path into this world as me\nI should check it out";
        yield return new WaitForSecondsRealtime(2f);
        dialogBox.text = "Hmm what does it say...\n\"O Traveller! You must find the willow tree if you wish to ever see your world again!\"";
        yield return new WaitForSecondsRealtime(4f);
        dialogBox.text = "What!\nIs this for me!";
        yield return new WaitForSecondsRealtime(1f);
        dialogBox.text = "I must find this willow tree!";
        yield return new WaitForSecondsRealtime(1f);
        dialogBox.text = "But first I'm starving\nI should find some food before hunger starts to take a toll on my health";
        yield return new WaitForSecondsRealtime(3f);
        dialogBox.text = "Was that a sheeps sound?\nIt sounded like it came from the east";
        yield return new WaitForSecondsRealtime(3f);
        dialogBox.text = "I should craft some arrows and hunt that sheep";
        FindObjectOfType<AudioManager>().Play("quest");
        tutorialBox.text = "-Consumable objects can be crafted from different resources\n-Wood and stone resources can be chopped and mined from trees and rocks respectively\n-To chop trees use the axe or to mine rocks use the pickaxe\n-The axe and pickaxe can be equipped by clicking on the right side of the HUD or by using the keys 3 and 4\n-Hover over the craft button of consumables to see their crafting recipes and click the button to craft them" ;
        yield return new WaitForSecondsRealtime(3f);
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene6()
    {
        running = true;
        cutscene.Play();
        dialogBox.text = "I should skin the sheep and take it's meat";
        FindObjectOfType<AudioManager>().Play("quest");
        tutorialBox.text = "-To skin meat from animals you need to go near them and interact using the E key\n-Then walk over raw meat to collect it";
        yield return new WaitForSecondsRealtime(3f);
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    private IEnumerator cutscene7()
    {
        running = true;
        cutscene.Play();
        dialogBox.text = "I better make a fire and cook this meat";
        FindObjectOfType<AudioManager>().Play("quest");
        tutorialBox.text = "-To cook raw resources you need to craft a fire\n-When around the fire you will see a text pop-up and thats when you can cook/craft raw food\n-Crafting fires also works as checkpoints to save the game\nExplore the world many adventure await\nIndulge yourself in fishing by the river and explore the ancient pyramid bu portalling via the willow tree";
        yield return new WaitForSecondsRealtime(3f);
        savePlayerState();
        running = false;
        Destroy(gameObject);
    }

    // private IEnumerator cutscene8()
    // {
    //     running = true;
    //     cutscene.Play();
    //     FindObjectOfType<AudioManager>().Play("scream");
    //     dialogBox.text = "Is that a woman shouting?";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "I should go check it out";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "Maybe she can help me find the willow tree";
    //     yield return new WaitForSecondsRealtime(2f);
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(20.12f, 7.05f));
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene9()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "That must be the woman I heard...";//Show woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "SHES IN DANGER!";//Show wolves
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "I need to help her";
    //     yield return new WaitForSecondsRealtime(2f);
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET DESTINATION
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene10()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "That must be the woman I heard...";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "SHES IN DANGER!";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "I need to help her";
    //     yield return new WaitForSecondsRealtime(2f);
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET DESTINATION
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene11()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "*Phew* That is the last of them";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "Are you okay?";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "WOMAN: Yes, I had this under control"; //Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "The attacks have been getting worse ever since the purge destroyed thecrystal island\n and forced them out of their habitat"; //Woman
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "WOMAN: So I can very well take care of myself."; //Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "The crystal isl-?\nWell okay sorry for interrupting...";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "I was wondering if you could help me out\nI was looking for-";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WOMAN: Oh so you come in swinging your sword and now I owe you help!?";//Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "Wha?-\nLook I was just looking for some willow tree";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WOMAN: ...";//Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WOMAN: You're looking for the willow tree?";//Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WOMAN: You're not from around here are you?";//Woman
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WOMAN: Yes..";//Woman
    //     yield return new WaitForSecondsRealtime(1f);
    //     dialogBox.text = "WEIRD WOMAN: YES!";//Woman
    //     yield return new WaitForSecondsRealtime(1f);
    //     dialogBox.text = "WEIRD WOMAN: THE TREE, ITS JUST SOUTH FROM HERE, FOLLOW THE PATH SOUTHWEST AND IT WILL LEAD YOU RIGHT TO IT";//Woman
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET DESTINATION
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "WEIRD WOMAN: Anyway I have to be off on my way now!\n Thanks! Bye.";//Woman
    //     //Woman runs off north
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "What a strange woman...why did she care I was looking for the tree?\nAnd why did she act so differently when she found out?\nWhat was that about the purge and some crystals?";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "Ugh all of this is making my head spin.\n";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "No matter, I have a feeling finding the tree is more important now";//Woman
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene12()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "That must be the willow tree";
    //     yield return new WaitForSecondsRealtime(2f);
    //     //SHOW TREE
    //     dialogBox.text = "It's beautiful";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "What's this?";
    //     yield return new WaitForSecondsRealtime(2f);
    //     //ANOTHER NOTE
    //     dialogBox.text = "NOTE: A century ago these lands were a place of peace and people\ncame here seeking shelter from the cruel outside world.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: The natural resources within the world were infused with magical\nproperties which scared away the evil forces.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: However, as they always do, the humans took what they had for granted\nand abused the resources.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: At first they started to steal the reserves of the crystals for their own tools use.\nBut eventually they started digging them up just to prevent\nanyone else from getting them.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: As the minerals started to run out evil forces from\n outside managed to slither their way into the lands.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: They completely destroyed the island and only left\n those as corrupt as themselves in the barren wasteland.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: Only the Ancient Tree was able to prevent the dark corruption.";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "NOTE: A prophecy foretold that they would seek to take over the remainder of the lands,\nand once the Ancient Willow is at its weakest,\nand corruption begins to spread to the ones who live in the nearby lands the willow will call out for a saviour\nfrom a faraway land who will have to survive against the corruption\nand destroy it once and for all.";
    //     yield return new WaitForSecondsRealtime(6f);
    //     dialogBox.text = "NOTE: He will go to the Pyramid and purge it of darkness, however he must beware";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "This note feels strangely targeted at me";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "NOTE: So go forth hero, you are our last chance at survival,\nin our hour of need we beg of you to go to the wasteland\nand destroy the evil lurking within the pyramid";
    //     yield return new WaitForSecondsRealtime(4f);
    //     dialogBox.text = "...";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "WHAT!?";
    //     yield return new WaitForSecondsRealtime(1f);
    //     dialogBox.text = "I DONT EVEN KNOW WHO OR WHERE I AM AND NOW I HAVE TO BE A HERO!?";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "I guess, I have no other option... Who doesnt want to be a hero anyway?";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "I guess I will have find a way to stop the evil";
    //     yield return new WaitForSecondsRealtime(3f);
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET DESTINATION
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene13()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "FISHERMAN: WHO ARE YE?";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "I am the hero thats here to save you from the dark corruption";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "FISHERMAN: Sure and I'm Michael Jackson.";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "...\nDo you know how I can get to the Pyramid?";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "FISHERMAN: Hmmm catch 10 fish for me and then I might know";
    //     yield return new WaitForSecondsRealtime(3f);
    //     dialogBox.text = "Aren't you the fisherman..?";
    //     yield return new WaitForSecondsRealtime(2f);
    //     dialogBox.text = "NOT FISHERMAN: Mind your business";
    //     yield return new WaitForSecondsRealtime(2f);
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET FISHING GAME
    //     //FISHING GAME TIME
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    // }

    // private IEnumerator cutscene14()
    // {
    //     running = true;
    //     cutscene.Play();//MAKE CUTSCENE
    //     dialogBox.text = "FISHERMAN: Thanks, so: the pyramid is in the desert. It's like a big triangle,\n crazy I know.";
    //     yield return new WaitForSecondsRealtime(2f);
    //     FindObjectOfType<AudioManager>().Play("quest");
    //     gameMaster.setPlayerPosition(new Vector2(0f, 0f));//SET PYRAMID
    //     savePlayerState();
    //     running = false;
    //     Destroy(gameObject);
    //}

    //CUTSCENE 15 YOU WIN!!!
}
