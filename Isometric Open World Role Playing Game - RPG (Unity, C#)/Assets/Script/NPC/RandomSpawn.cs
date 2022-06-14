using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RandomSpawn : MonoBehaviour
{
    public Transform enemy;
    public float spawnChance;

    void Start()
    {
        RandomSpawnNPC(1);
    }

    private IEnumerator RandomSpawnNPC(float time)
    {
        while (true)
        {
            yield return new WaitForSeconds(time);

            if (randomBoolean(spawnChance))
            {//Normal fish
                Debug.Log("SpawnAttempt");
                Transform tempEnemy = Instantiate(enemy);
            }
        }
    }

    private bool randomBoolean(float x)
    {
        return (Random.value < x);
    }
}