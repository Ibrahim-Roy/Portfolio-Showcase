using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spawner : MonoBehaviour
{
    public Transform goodFish;
    public Transform nemo;
    public Transform colourful;
    public Transform golden;

    public Transform badFish;
    public Transform sword;
    public Transform mine;

    private float topX;
    private float topY;
    private float bottomX;
    private float bottomY;

    void Start()
    {
        topX = transform.position.x - (transform.localScale.x/2);//Top left X
        topY = transform.position.y + (transform.localScale.y/2);//Top left Y
        bottomX = transform.position.x + (transform.localScale.x/2);//Bottom right X
        bottomY = transform.position.y - (transform.localScale.y/2);//Bottom right Y

        StartCoroutine(RandomSpawn(1f));
    }

    private IEnumerator RandomSpawn(float time) 
    {
        while(true)
        {
            yield return new WaitForSeconds(time);

            //Spawn chances and locations of food fish
            if (randomBoolean(0.4f)){//Normal fish
                Transform tempFish = Instantiate(goodFish, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
            if (randomBoolean(0.2f)){//Nemo fish
                Transform tempFish = Instantiate(nemo, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
            if (randomBoolean(0.1f)){//Colourful fish
                Transform tempFish = Instantiate(colourful, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
            if (randomBoolean(0.01f)){//Golden fish
                Transform tempFish = Instantiate(golden, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }

            //Spawn chances and locations of damage fish
            if (randomBoolean(0.33f)){//Bad fish
                Transform tempFish = Instantiate(badFish, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
            if (randomBoolean(0.13f)){//Sword fish
                Transform tempFish = Instantiate(sword, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
            if (randomBoolean(0.04f)){//mine fish
                Transform tempFish = Instantiate(mine, RandomVec2D(topX, topY, bottomX, bottomY), Quaternion.identity);
            }
        }
    }

    private Vector2 RandomVec2D(float topX, float topY, float bottomX, float bottomY) //Random location
    {
        Vector2 vector2D = new Vector2(Random.Range(topX, bottomX), Random.Range(topY, bottomY));
        return vector2D;
    }

    private bool randomBoolean(float x) //The larger the value the more likely 0.5f is 50%
    {
        return (Random.value < x);
    }
}