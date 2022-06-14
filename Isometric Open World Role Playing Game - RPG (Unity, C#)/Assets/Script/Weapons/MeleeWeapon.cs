using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MeleeWeapon : MonoBehaviour
{
    private void Start()
    {
        
    }

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
        else
        {
            Physics2D.IgnoreCollision(other.gameObject.GetComponent<Collider2D>(), GetComponent<Collider2D>());
        }
    }
}
