using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Campfire : MonoBehaviour
{
    public GameObject craftingGuidePanel;

    private GameObject playerObject;
    private Player player;
    private GameMasterMainWorld gameMaster;
    
    private void Awake()
    {
        gameMaster = GameObject.FindGameObjectWithTag("Game Master").GetComponent<GameMasterMainWorld>();
        playerObject = GameObject.FindGameObjectWithTag("Player");
        player = playerObject.GetComponent<Player>();   
    }

    private void Start()
    {
        gameMaster.setPlayerPosition(transform.position);
        gameMaster.setPlayerDestinationPosition(player.getDestinationPosition());
        gameMaster.setEquippedItem(player.getEquippedItem());
        gameMaster.setWood(player.getWood());
        gameMaster.setStone(player.getStone());
        gameMaster.setRawFish(player.getRawFish());
        gameMaster.setRawMeat(player.getRawMeat());
        gameMaster.setArrows(player.getArrows());
        gameMaster.setFish(player.getFish());
        gameMaster.setMeat(player.getMeat());
        Destroy(this.gameObject, 180f);
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if(other.gameObject.CompareTag(playerObject.tag))
        {
            craftingGuidePanel.SetActive(true);
            player.setCampfireStatus(true);
        }        
    }

    private void OnTriggerExit2D(Collider2D other)
    {
        if(other.gameObject.CompareTag(playerObject.tag))
        {
            if(craftingGuidePanel.activeSelf)
            {
                craftingGuidePanel.SetActive(false);
            }    
            player.setCampfireStatus(false);
        }  
    }
}
