using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class mainMenu : MonoBehaviour
{
    public GameObject options;

    public GameObject instructions;

    public GameObject musicOptions;

    public GameObject difficulty;

    public void Play(){
        SceneManager.LoadScene("Main World");
    }

    public void Reset(){
        SceneManager.LoadScene("Main Menu");
    }

    public void OptionsOpen(){
        options.SetActive(true);
    }

    public void OptionClose(){
        options.SetActive(false);
    }

    public void HowOpen(){
        instructions.SetActive(true);
    }

    public void HowClose(){
        instructions.SetActive(false);
    }

    public void MusicOpen(){
        musicOptions.SetActive(true);
    }

    public void MusicClose(){
        musicOptions.SetActive(false);
    }

    public void DiffOpen(){
        difficulty.SetActive(true);
    }

    public void DifClose(){
        difficulty.SetActive(false);
    }

    public void Quit(){
        Application.Quit();
        Debug.Log("closing the game");
    }
}
