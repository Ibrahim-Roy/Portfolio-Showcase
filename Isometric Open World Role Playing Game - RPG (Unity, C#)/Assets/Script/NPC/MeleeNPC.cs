using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MeleeNPC : HostileNPC
{
    public bool lootAble = false;
    public GameObject lootingGuidePanel;
    public GameObject resource;
    public float dodgeBackDistance = 1.0f;


    protected override void attackTarget()
    {
        if(!attacking)
        {
            StartCoroutine(attack());
        }
    }

    protected override void decrementHealth(int amount)
    {
        base.decrementHealth(amount);
        if(health < 0)
        {
            if(!lootAble)
            {
                Destroy(this.gameObject, 60f);
            }
        }
    }

    private bool skinned = false;
    private bool harvestPossible = false;

    private IEnumerator attack()
    {
        
        attacking = true;
        animator.SetTrigger("Attack");
        yield return new WaitForSeconds(0.25f);
        target.GetComponent<Player>().takeDamage(1);
        yield return new WaitForSeconds(0.25f);
        attacking = false;
        if(!backUp)
        {
            StartCoroutine(dodgeBack());
        }
    }

    private IEnumerator dodgeBack()
    {
        backUp = true;
        float defaultStoppingDistance = stoppingDistance;
        stoppingDistance = dodgeBackDistance;
        yield return new WaitForSeconds(1f);
        stoppingDistance = defaultStoppingDistance;
        backUp = false;
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if(other.gameObject.CompareTag("Player"))
        {
            if(lootAble)
            {
                if(!alive && !skinned)
                {
                    if(other.gameObject.CompareTag("Player"))
                    {
                        lootingGuidePanel.SetActive(true);
                    }
                }
                harvestPossible = true;
            }
        }
    }

    private void OnTriggerExit2D(Collider2D other)
    {
        if(other.gameObject.CompareTag("Player"))
        {
            if(lootAble)
            {
                if(lootingGuidePanel.activeSelf)
                {
                    lootingGuidePanel.SetActive(false); 
                }
                harvestPossible = false;
            }
        }    
    }

    private void Update()
    {
        if(harvestPossible && Input.GetKeyDown(KeyCode.E))
        {
            if(lootAble)
            {
                if(!alive && !skinned)
                {
                    Instantiate(resource, transform.position, Quaternion.identity);
                    animator.SetTrigger("Skinned");
                    Destroy(this.gameObject, 60f);
                }
            }
        }   
    }
}
