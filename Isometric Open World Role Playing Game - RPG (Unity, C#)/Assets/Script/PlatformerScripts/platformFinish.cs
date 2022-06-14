using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class platformFinish : MonoBehaviour
{

    private void OnCollisionEnter2D(Collision2D other){

        if(other.gameObject.CompareTag("Player")){
            
            SceneManager.LoadScene("ending");
        }

    }
}
