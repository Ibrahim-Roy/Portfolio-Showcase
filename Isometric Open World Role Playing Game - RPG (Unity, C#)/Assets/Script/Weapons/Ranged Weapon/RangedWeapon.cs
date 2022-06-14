using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RangedWeapon : MonoBehaviour
{
    public GameObject ammunitionPrefab;
    public float shootingSpeed;

    public void shoot(Vector3 aimPosition, string targetTag)
    {
        Vector3 weaponBarrelPosition = transform.position;
        GameObject parent = (gameObject.transform.parent).transform.parent.gameObject;
        GameObject arrow = Instantiate(ammunitionPrefab, weaponBarrelPosition, ammunitionPrefab.transform.rotation);
        // Physics2D.IgnoreCollision(arrow.GetComponent<CircleCollider2D>(), parent.GetComponent<CircleCollider2D>());
        // arrow.GetComponent<RangedAmmunition>().targetTag = targetTag;
        // arrow.GetComponent<RangedAmmunition>().sourceTag = parent.tag;
        aimPosition.z = 0;
        Vector3 shootingDirection = (aimPosition - transform.position).normalized;
        arrow.GetComponent<Rigidbody2D>().velocity = shootingDirection * shootingSpeed;
        arrow.transform.Rotate(0f, 0f, Mathf.Atan2(shootingDirection.y, shootingDirection.x) * Mathf.Rad2Deg);
    }
}