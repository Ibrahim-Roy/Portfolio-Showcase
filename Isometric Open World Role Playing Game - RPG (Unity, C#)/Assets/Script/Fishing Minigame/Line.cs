using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Line : MonoBehaviour
{
    private LineRenderer lineRend;
    private Vector2 hookPos;
    public Transform destination;

    // Start is called before the first frame update
    void Start()
    {
        lineRend = GetComponent<LineRenderer>();
        lineRend.positionCount = 2;
        lineRend.SetPosition(0, new Vector3(0.22f, 4.6f, 0f));//Starting position of fishing rod sprite
    }

    // Update is called once per frame
    void Update()//Set 2nd point as the co-ordinates of the hook.
    {
        lineRend.SetPosition(1, new Vector3(destination.position.x, (destination.position.y + 0.3f), 0f));
    }
}
