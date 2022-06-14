using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class inventoryAndCrafting : MonoBehaviour
{
    public void requestCrafting()
    {
        if(gameObject.tag == "Craft Arrow")
        {
            player.craftArrow();
        }
        else if(gameObject.tag == "Craft Campfire")
        {
            player.craftCampfire();
        }
        else if(gameObject.tag == "Craft Meat")
        {
            player.craftMeat();
        }
        else if(gameObject.tag == "Craft Fish")
        {
            player.craftFish();
        }
    }

    public void requestConsumeFish()
    {
        player.consumeFish();
    }

    public void requestConsumeMeat()
    {
        player.consumeMeat();
    }

    public void requestEquippedItemChanged(int value)
    {
        player.setEquippedItem(value);
    }

    private Player player;

    private void Awake()
    {
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<Player>();
    }
}
