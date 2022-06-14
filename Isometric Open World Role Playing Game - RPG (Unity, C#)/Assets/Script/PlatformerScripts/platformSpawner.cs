using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class platformSpawner : MonoBehaviour
{

    public GameObject objectToSpawn;

    public float timeToSpawn;
    private float currentTimeToSpawn;
    
    public void SpawnObject(){

        Instantiate(objectToSpawn, transform.position, objectToSpawn.transform.rotation);
    }

    // Update is called once per frame
    void Update()
    {
        if(currentTimeToSpawn > 0){
            currentTimeToSpawn -= Time.deltaTime;
        }
        else{
            SpawnObject();
            currentTimeToSpawn = timeToSpawn;
        }
    }
}
