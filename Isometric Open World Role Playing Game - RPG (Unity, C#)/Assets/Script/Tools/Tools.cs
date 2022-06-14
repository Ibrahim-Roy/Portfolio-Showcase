using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Tools : MonoBehaviour
{
    public ParticleSystem hitParticles;
    public string targetTag;

    private void OnTriggerEnter2D(Collider2D other) {
        if(other.gameObject.tag == targetTag)
        {
            other.gameObject.GetComponent<MineAbleObject>().takeDamage();
            if(!hitParticles.isPlaying)
            {
                hitParticles.Play();
            }
        }
    }
}