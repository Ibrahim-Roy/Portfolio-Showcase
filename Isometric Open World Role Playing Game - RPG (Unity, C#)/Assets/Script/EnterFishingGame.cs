using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;
using UnityEngine.SceneManagement;

public class EnterFishingGame : MonoBehaviour
{
    private Collider2D hook_Collider;

    void Start()
    {
        hook_Collider = GetComponent<BoxCollider2D>();
    }

    void OnTriggerEnter2D(Collider2D col)//When catches fish
    {
        if (col.gameObject.tag == "Player")
        {
            SceneManager.LoadScene("Fishing Minigame");
        }
    }
}