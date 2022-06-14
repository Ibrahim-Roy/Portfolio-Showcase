using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Audio;

public class NPCsounds : MonoBehaviour
{
    public AudioSource audioSource;
    public AudioClip attackAudio;
    public AudioClip hitAudio;
    public AudioClip deathAudio;
    public AudioClip otherAudio;

    public void attackSound()
    {
        audioSource.clip = attackAudio;
        audioSource.Play();
    }
    public void hitSound()
    {
        audioSource.clip = hitAudio;
        audioSource.Play();
    }
    public void deathSound()
    {
        audioSource.clip = deathAudio;
        audioSource.Play();
    }
    public void otherSound()
    {
        audioSource.clip = otherAudio;
        audioSource.Play();
    }
}
