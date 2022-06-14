using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[System.Serializable]
public class GameData
{
    public float[] playerPosition;
    public float[] playerDestinationPosition;
    public int playerEquippedItemNumber;
    public int playerWood;
    public int playerStone;
    public int playerRawFish;
    public int playerRawMeat;
    public int playerArrows;
    public int playerFish;
    public int playerMeat;
    public bool craftFire;
    public int progressCounter;

    public GameData (GameMasterMainWorld gameMaster)
    {
        playerEquippedItemNumber = gameMaster.getEquippedItem();
        playerWood = gameMaster.getWood();
        playerStone = gameMaster.getStone();
        playerRawFish = gameMaster.getRawFish();
        playerRawMeat = gameMaster.getRawMeat();
        playerArrows = gameMaster.getArrows();
        playerFish = gameMaster.getFish();
        playerMeat = gameMaster.getMeat();
        craftFire = gameMaster.craftFireRequired();
        progressCounter = gameMaster.getProgressCounter();
        playerPosition = new float[2];
        playerPosition[0] = gameMaster.getPlayerPosition().x;
        playerPosition[1] = gameMaster.getPlayerPosition().y;
        playerDestinationPosition = new float[2];
        playerDestinationPosition[0] = gameMaster.getPlayerDestinationPosition().x;
        playerDestinationPosition[1] = gameMaster.getPlayerDestinationPosition().y;
    }
}
