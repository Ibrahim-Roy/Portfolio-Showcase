using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class platformArrow : MonoBehaviour
{
    
    public float moveSpeed = 3f;

    void Update()
    {
        transform.position = new Vector2(transform.position.x + moveSpeed, transform.position.y);
    }
}
