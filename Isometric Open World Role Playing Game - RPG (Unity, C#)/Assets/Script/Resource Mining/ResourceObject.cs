using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ResourceObject : MonoBehaviour
{
    private GameObject player;

    private void Awake()
    {
        player = GameObject.FindGameObjectWithTag("Player");
    }

    private void Start()
    {
        this.transform.position = Vector2.MoveTowards(this.transform.position, new Vector2(this.transform.position.x, this.transform.position.y -2), 0.5f);        
    }

    private void OnTriggerEnter2D(Collider2D other) {
        if(other.gameObject.tag == "Player")
        {
            player.GetComponent<Player>().collectResource(this.gameObject.tag);
            Destroy(this.gameObject);
        }
    }
}
