using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RangedNPC : HostileNPC
{
    public GameObject lootingGuidePanel;
    public GameObject resource;


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
            Destroy(this.gameObject, 60f);
        }
    }

    private IEnumerator attack()
    {
        attacking = true;
        animator.SetTrigger("Attack");
        yield return new WaitForSeconds(0.25f);
        Vector3 aimPosition = target.GetComponent<Player>().getPosition();
        (transform.GetChild(0).gameObject).GetComponent<RangedWeapon>().shoot(aimPosition, "Player");
        attacking = false;
    }
}