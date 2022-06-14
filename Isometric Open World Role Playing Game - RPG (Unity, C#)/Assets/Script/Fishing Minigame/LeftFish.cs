using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LeftFish : MonoBehaviour
{
    Rigidbody2D leftFish_Rigidbody;

    private GameObject player;
    private int damage = 0;
    private int foodValue = 0;
    private string audioClip = "";

    void Start()
    {
        leftFish_Rigidbody = GetComponent<Rigidbody2D>();
        player = GameObject.FindGameObjectWithTag("Player");

        fishDefine(); //Predetermine fish life

    }

    void move(float u)
    {
        leftFish_Rigidbody.velocity = new Vector2(u, 0);
    }
    
    void stopMove()
    {
        leftFish_Rigidbody.velocity = new Vector2(0, 0);
    }

    void OnTriggerEnter2D(Collider2D col)
    {
        if (col.gameObject.tag == "hook")//hooked fish
        {
            stopMove();
            FindObjectOfType<AudioManager>().Play("hooked");
            this.transform.parent = col.transform;      
            leftFish_Rigidbody.velocity = new Vector2(0f, 3.5f);
        }

        if (col.gameObject.tag == "Player")//Collect resource
        {
            if (gameObject.tag == "Raw Fish")
            {
                for (int i = 0; i < foodValue; i++)
                    player.GetComponent<Player>().collectResource("Raw Fish");
                FindObjectOfType<AudioManager>().Play(audioClip);
            }

            else if (gameObject.tag == "Bad Fish")
            {
                FindObjectOfType<AudioManager>().Play(audioClip);
                player.GetComponent<Player>().takeDamage(damage);
            }
            Destroy(gameObject);
        }

        if (col.gameObject.tag == "Spawner Right")//Despawn once offscreen
        {
            Destroy(gameObject);
        }
    }

    private void fishDefine()
    {
        if (gameObject.tag == "Raw Fish")
        {
            if (gameObject.name == "Fish(Clone)")
                defineNormalFish();
            else if (gameObject.name == "Nemo(Clone)")
            {
                defineNemo();
            }
            else if (gameObject.name == "Colourful(Clone)")
            {
                defineColourful();
            }
            else if (gameObject.name == "Golden(Clone)")
                defineGolden();
        }
        else if (gameObject.tag == "Bad Fish")
        {
            if (gameObject.name == "Bad Fish(Clone)")
                defineBadFish();
            else if (gameObject.name == "Swordfish(Clone)")
                defineSword();
            else if (gameObject.name == "Mine(Clone)")
                defineMine();
        }
    }

    //Size, speed and food values of food fish
    private void defineNormalFish()//Average fish
    {
        transform.localScale = RandomScale2D(0.15f, 0.4f);
        move(Random.Range(2f, 4f));
        foodValue = 1;
        audioClip = "normal";
    }
    private void defineNemo()//Small fish
    {
        transform.localScale = RandomScale2D(0.15f, 0.28f);
        move(Random.Range(2f, 4f));
        foodValue = 2;
        audioClip = "nemo";
    }
    private void defineColourful()//Small, very fast fish
    {
        transform.localScale = RandomScale2D(0.15f, 0.28f);
        move(Random.Range(5f, 6f));
        foodValue = 3;
        audioClip = "colourful";
    }
    private void defineGolden()//Very small, fast fish
    {
        transform.localScale = RandomScale2D(0.15f, 0.2f);
        move(Random.Range(4f, 6f));
        foodValue = 10;
        audioClip = "golden";
    }

    //Sizes, speed and damage values of damaging fish
    private void defineBadFish()//Average damaging fish
    {
        transform.localScale = RandomScale2D(0.2f, 0.4f);
        move(Random.Range(2f, 4f));
        damage = 1;
        audioClip = "bite";
    }
    private void defineSword()//Very Fast average fish
    {
        transform.localScale = RandomScale2D(0.2f, 0.4f);
        move(Random.Range(5f, 7f));
        damage = 3;
        audioClip = "slash";
    }
    private void defineMine()//Very Slow, large "fish"
    {
        transform.localScale = RandomScale2D(0.4f, 0.7f);
        move(Random.Range(1f, 2f));
        damage = 7;
        audioClip = "explosion";
    }
    
    private Vector2 RandomScale2D(float min, float max)//Values for scale
    {
        float scale = Random.Range(min, max);
        Vector2 scaler2D = new Vector2(scale, scale);
        return scaler2D;
    }
}