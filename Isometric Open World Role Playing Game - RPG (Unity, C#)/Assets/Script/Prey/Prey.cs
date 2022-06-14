using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Prey : MonoBehaviour
{
    public Slider healthBar;
    public ParticleSystem blood;
    public GameObject lootingGuidePanel;
    public GameObject resource;
    public float maximumRoamingDistance = 0;
    public float normalMovementSpeed;
    public int maxHealth;

    public void takeDamage(int amount)
    {
        Debug.Log("Take Damage");
        if(movementSpeed == normalMovementSpeed)
        {
            movementSpeed = movementSpeed*3;
            StartCoroutine(returnToNormalSpeed());
        }
        decrementHealth(amount);
    }

    public bool getAliveStatus()
    {
        return alive;
    }

    public bool getSkinnedStatus()
    {
        return skinned;
    }

    private Rigidbody2D rigidBody;
    private Animator animator;
    private Vector2 originalPosition;
    private Vector2 randomRoamDestinationPosition;
    private float movementSpeed;
    private int health;
    private bool alive = true;
    private bool skinned = false;
    private bool harvestPossible = false;

    private void Awake()
    {
        rigidBody = GetComponent<Rigidbody2D>();
        animator = GetComponent<Animator>();
    }

    private void Start()
    {
        originalPosition = new Vector2(transform.position.x, transform.position.y);
        movementSpeed = normalMovementSpeed;
        setRandomRoamDestination();
        health = maxHealth;
        healthBar.maxValue = maxHealth;
        healthBar.fillRect.GetComponentInChildren<Image>().color = Color.red;         
    }

    private void FixedUpdate()
    {
        if(alive)
        {
            roamWorldRandomly();
        }
    }

    private void Update()
    {
        if(harvestPossible && Input.GetKeyDown(KeyCode.E))
        {
            if(!alive && !skinned)
            {
                Instantiate(resource, transform.position, Quaternion.identity);
                animator.SetTrigger("Skinned");
                Destroy(this.gameObject, 60f);
            }
        }   
    }

    private void OnCollisionStay2D(Collision2D other) {
        setRandomRoamDestination();
    }

     private void OnTriggerEnter2D(Collider2D other)
    {
        if(other.gameObject.CompareTag("Player"))
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

    private void OnTriggerExit2D(Collider2D other)
    {
        if(other.gameObject.CompareTag("Player"))
        {
            if(lootingGuidePanel.activeSelf)
            {
                lootingGuidePanel.SetActive(false); 
            }
            harvestPossible = false;
        }    
    }

    private void roamWorldRandomly()
    {
        Vector2 nextStepPosition = Vector2.MoveTowards(transform.position, randomRoamDestinationPosition, (movementSpeed)*Time.deltaTime);
        rigidBody.MovePosition(nextStepPosition);
        if(Vector2.Distance(transform.position, randomRoamDestinationPosition) < 0.5)
        {
            setRandomRoamDestination();
        }
    }

    private void setRandomRoamDestination()
    {
        randomRoamDestinationPosition = new Vector2(
            Random.Range(originalPosition.x - maximumRoamingDistance, originalPosition.x + maximumRoamingDistance),
            Random.Range(originalPosition.y - maximumRoamingDistance, originalPosition.y + maximumRoamingDistance)
        );
        animationHandler(randomRoamDestinationPosition); 
    }

    private void animationHandler(Vector2 destination)
    {
        Vector2 walkingDirection = (destination - new Vector2(transform.position.x, transform.position.y)).normalized;
        animator.SetFloat("Horizontal", walkingDirection.x);
        animator.SetFloat("Vertical", walkingDirection.y);
    }

    private void decrementHealth(int amount)
    {
        if(health > 0)
        {
            health -= amount;
            if(!blood.isPlaying)
            {
                blood.Play();
            }
            if(!healthBar.gameObject.activeSelf)
            {
                healthBar.gameObject.SetActive(true);
            }
        }
        if(health <= 0)
        {
            health = 0;
            healthBar.gameObject.SetActive(false);
            animator.SetTrigger("Kill");
            alive = false;
            rigidBody.constraints = RigidbodyConstraints2D.FreezeAll;
        }
        healthBar.value = health;
    }


    private IEnumerator returnToNormalSpeed()
    {
        yield return new WaitForSeconds(10f);
        movementSpeed = normalMovementSpeed;
    }
}
