using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ChildSpawner: MonoBehaviour {
 
    public GameObject childReference;
 
    void Awake () 
    { 
        // create a new child instance at parents' position and rotation
        childReference = Instantiate(childReference, transform.position, transform.rotation);
 
        // attach child
        childReference.transform.SetParent(transform, true);
    }
}