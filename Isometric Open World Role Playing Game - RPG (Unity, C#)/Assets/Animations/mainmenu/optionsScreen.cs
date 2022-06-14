using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class optionsScreen : MonoBehaviour
{
    public Toggle fullscreenT;
    public Toggle vsyncT;

    public List<ResolutionItem> resolutions = new List<ResolutionItem>();
    private int selectedRes;
    public TMP_Text resolutionLabel;


    // Start is called before the first frame update
    void Start()
    {
        fullscreenT.isOn = Screen.fullScreen; 

        if(QualitySettings.vSyncCount == 0){
            vsyncT.isOn = false;
        }
        else{
            vsyncT.isOn = true;
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void ResolutionLeft(){
        selectedRes--;
        if(selectedRes < 0){
            selectedRes = 0;
        }

        updateResLabel();
    }

    public void ResolutionRight(){
        selectedRes++;
        if(selectedRes > resolutions.Count - 1){
            selectedRes = resolutions.Count - 1;
        }

        updateResLabel();
    }

    public void updateResLabel(){
        resolutionLabel.text = resolutions[selectedRes].horizontal.ToString() + " x " + resolutions[selectedRes].vertical.ToString();
    }

    public void ApplyGraphics(){

        //Screen.fullScreen = fullscreenT.isOn;

        if(vsyncT.isOn){
            QualitySettings.vSyncCount = 1;
        }
        else{
            QualitySettings.vSyncCount = 0;
        }

        Screen.SetResolution(resolutions[selectedRes].horizontal, resolutions[selectedRes].vertical, fullscreenT.isOn);

    }
}


[System.Serializable]
public class ResolutionItem{

    public int horizontal;
    public int vertical;
}
