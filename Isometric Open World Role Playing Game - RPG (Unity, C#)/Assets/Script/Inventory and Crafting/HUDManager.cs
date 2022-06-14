using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class HUDManager : MonoBehaviour
{
    public GameObject alertTextContainer;
    public GameObject alertTextBoxPrefab;
    public GameObject craftingRecipeDisplay;
    public GameObject swordSlot;
    public GameObject bowSlot;
    public GameObject axeSlot;
    public GameObject pickaxeSlot;
    public GameObject compass;
    public GameObject tutorialTextBox;
    public Slider healthBar;
    public Slider hungerBar;
    public Text woodText;
    public Text stoneText;
    public Text rawFishText;
    public Text rawMeatText;
    public Text arrowsText;
    public Text meatText;
    public Text fishText;

    public void setMaxHealthBarValue(int maxHealth)
    {
        healthBar.fillRect.GetComponentInChildren<Image>().color = Color.red;
        healthBar.maxValue = maxHealth;
        setHealth(maxHealth);
    }

    public void setMaxHungerBarValue(int maxHunger)
    {
        hungerBar.fillRect.GetComponentInChildren<Image>().color = new Color(254,184,0,255);
        hungerBar.maxValue = maxHunger;
        setHunger(maxHunger);
    }

    public void updateHUD(string HUDComponent, int value)
    {
        if(HUDComponent == "Health")
        {
            setHealth(value);
        }
        else if(HUDComponent == "Hunger")
        {
            setHunger(value);
        }
        else if(HUDComponent == "Wood")
        {
            setText(woodText, value);
        }
        else if(HUDComponent == "Stone")
        {
            setText(stoneText, value);
        }
        else if(HUDComponent == "Raw Fish")
        {
            setText(rawFishText, value);
        }
        else if(HUDComponent == "Raw Meat")
        {
            setText(rawMeatText, value);
        }
        else if(HUDComponent == "Arrows")
        {
            setText(arrowsText, value);
        }
        else if(HUDComponent == "Fish")
        {
            setText(fishText, value);
        }
        else if(HUDComponent == "Meat")
        {
            setText(meatText, value);
        }
        else if(HUDComponent == "Equipped Item")
        {
            bowSlot.GetComponent<Image>().color = Color.white;
            swordSlot.GetComponent<Image>().color = Color.white;
            axeSlot.GetComponent<Image>().color = Color.white;
            pickaxeSlot.GetComponent<Image>().color = Color.white;
            if(value == 1)
            {
                bowSlot.GetComponent<Image>().color = new Color(0.282f, 0.204f, 0.015f, 0.612f);
            }
            else if(value == 2)
            {
                swordSlot.GetComponent<Image>().color = new Color(0.282f, 0.204f, 0.015f, 0.612f);
            }
            else if(value == 3)
            {
                axeSlot.GetComponent<Image>().color = new Color(0.282f, 0.204f, 0.015f, 0.612f);
            }
            else if(value == 4)
            {
                pickaxeSlot.GetComponent<Image>().color = new Color(0.282f, 0.204f, 0.015f, 0.612f); 
            }
        }
    }

    public void alert(string alertText)
    {
        var newTextBox = Instantiate(alertTextBoxPrefab, new Vector3(0,0,0), Quaternion.identity);
        newTextBox.transform.SetParent(alertTextContainer.transform, false);
        newTextBox.GetComponentInChildren<Text>().text = alertText;
        Destroy(newTextBox, 1.5f);
    }

    public void showCraftingRecipe(string recipeName)
    {
        craftingRecipeDisplay.SetActive(true);
        string recipeText = "Recipe:\n";
        if(recipeName == "Arrow")
        {
            recipeText += "1x Wood\n1x Stone";
        }
        else if(recipeName == "Campfire")
        {
            recipeText += "4x Wood\n2x Stone";
        }
        else if(recipeName == "Meat")
        {
            recipeText += "1x Raw Meat";
        }
        else if(recipeName == "Fish")
        {
            recipeText += "1x Raw Fish";
        }
        craftingRecipeDisplay.GetComponentInChildren<Text>().text = recipeText;
    }

    public void hideCraftingRecipe()
    {
        craftingRecipeDisplay.SetActive(false);
    }

    public void updateCompass(Vector2 startPosition, Vector2 destinationPosition)
    {
        Vector2 direction = (destinationPosition - startPosition).normalized;
        float newRotation = Mathf.Atan2(-direction.x, direction.y) * Mathf.Rad2Deg;
        if(compass.transform.rotation.z != newRotation)
        {
            compass.transform.rotation = Quaternion.Euler(0, 0, (newRotation));
        }
    }

    public void hideTutorialBox()
    {
        tutorialTextBox.SetActive(false);
        Time.timeScale = 1;
    }

    private void setText(Text textbox, string text)
    {
        textbox.text = text;
    }

    private void setText(Text textbox, int text)
    {
        textbox.text = text.ToString();
    }

    private void setHealth(int health)
    {
        healthBar.value = health;
    }

    private void setHunger(int hunger)
    {
        hungerBar.value = hunger;
    }
}
