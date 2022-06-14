using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlatformEnemyBehaviour : MonoBehaviour
{
    [SerializeField] float moveSpeed = 1f;

    Rigidbody2D rb;

    // Start is called before the first frame update
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        if(facingRight()){
            rb.velocity = new Vector2(moveSpeed, 0f);
        }
        else{
            rb.velocity = new Vector2(-moveSpeed, 0f);
        }

    }

    private bool facingRight(){
        return transform.localScale.x > Mathf.Epsilon;
    }

    private void OnTriggerEnter2D(Collider2D collision){
        transform.localScale = new Vector2(-(transform.localScale.x), transform.localScale.y);
    }

}
