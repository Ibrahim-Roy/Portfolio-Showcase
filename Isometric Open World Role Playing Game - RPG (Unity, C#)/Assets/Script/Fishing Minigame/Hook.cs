using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.EventSystems;

public class Hook : MonoBehaviour
{
    private Rigidbody2D hook_Rigidbody;
    private Collider2D hook_Collider;

    public Transform startPos;

    [Range(1, 3)]
    public int difficulty; //If difficulties are added 
    float difficulty_speed;

    private AudioManager audioController;

    void Start()
    {
        //Fetch the Rigidbody from the GameObject with this script attached
        hook_Rigidbody = GetComponent<Rigidbody2D>();
        hook_Collider = GetComponent<BoxCollider2D>();
        audioController = GetComponent<AudioManager>();

        // if (difficulty == 1)
        //     difficulty_speed = 500f;
        // else if (difficulty == 2)
        //     difficulty_speed = 300f;      
        // else if (difficulty == 3)
        //     difficulty_speed = 200f;
    }

    // Update is called once per frame
    void Update()
    {
        if (hook_Rigidbody.velocity.y == 0 && Input.GetMouseButtonDown(0) && !EventSystem.current.IsPointerOverGameObject())//Check if stationary and wait for mouse click, not over hud
            descend();
        else if (transform.position.y <= -4.0 && hook_Rigidbody.velocity.y < 4f)//Accelerate up
            ascend();
        if(hook_Rigidbody.velocity.y > 0 && transform.position.y > 4.0)
            resetHook();
    }

    void ascend()//Accelerate upwards
    {
        hook_Rigidbody.velocity = new Vector2(0, 4);
        FindObjectOfType<AudioManager>().Play("reel");
    }
    void descend()//Accelerate downwards
    {
        hook_Rigidbody.velocity = new Vector2(0, -4);
        FindObjectOfType<AudioManager>().Play("splash");
    }

    void resetHook()//Sets the hook to starting position
    {
        hook_Rigidbody.velocity = new Vector2(0, 0);
        transform.position = startPos.position;
        hook_Collider.enabled = true;
        FindObjectOfType<AudioManager>().Stop("reel");
    }

    void OnTriggerEnter2D(Collider2D col)//When catches fish
    {
        if (col.gameObject.tag != "Player")
        {
            hook_Collider.enabled = false;
            hook_Rigidbody.velocity = new Vector2(0f, 3.5f);
            FindObjectOfType<AudioManager>().Play("reel");
        }
    }
}