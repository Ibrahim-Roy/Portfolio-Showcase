using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RangedAmmunition : MonoBehaviour
{
    private void OnTriggerEnter2D(Collider2D other)
    {
        if(other.gameObject.CompareTag("Hostile"))
        {
            other.gameObject.GetComponentInParent<HostileNPC>().takeDamage(1);
        }
        else if(other.gameObject.CompareTag("Prey"))
        {
            other.gameObject.GetComponent<Prey>().takeDamage(1);
        }
        FindObjectOfType<AudioManager>().Play("arrow");//Hit Sound
        DespawnTime(5);
        Destroy(this.gameObject);
    }

    private void OnCollisionEnter2D(Collision2D other) {
        FindObjectOfType<AudioManager>().Play("arrow miss");//Miss Sound
        DespawnTime(5);
        Destroy(this.gameObject);    
    }

    private IEnumerator DespawnTime(float time)
    {
        while(true)
        {
            yield return new WaitForSeconds(time);
        }
    }

}
