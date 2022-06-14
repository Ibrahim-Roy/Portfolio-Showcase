using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class platformPlayerMovement : MonoBehaviour
{

    public float speed;
    public float jump;
    public bool checkJump;
    private float Move;
    private Rigidbody2D rb;

    public Animator animator;
    // Start is called before the first frame update
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        animator.SetFloat("Horizontal", Input.GetAxis("Horizontal"));
        
        Move = Input.GetAxis("Horizontal");
        rb.velocity = new Vector2(speed * Move, rb.velocity.y);
        if(Input.GetButtonDown("Jump") && checkJump == false){
            rb.AddForce(new Vector2(rb.velocity.x, jump));
        }
    }

    private void OnCollisionEnter2D(Collision2D other){
        if(other.gameObject.CompareTag("Ground")){
            checkJump = false;
        }
    }

    private void OnCollisionExit2D(Collision2D other){
        if(other.gameObject.CompareTag("Ground")){
            checkJump = true;
        }
    }

}
