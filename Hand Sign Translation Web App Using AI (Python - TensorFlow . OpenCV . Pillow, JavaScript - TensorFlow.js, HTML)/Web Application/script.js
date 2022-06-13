// Get DOM elements
const initialInstructions = document.getElementById("initial-instructions");
const launchInstructions = document.getElementById("launch-instructions");
const launchButton = document.getElementById("launch-button");
const mainapp = document.getElementById("mainapp");
const loadingIcon = document.getElementById("loading-icon");
const video = document.getElementById("camera");
const textarea = document.getElementById("dialogbox");
const delayDisplay = document.getElementById("delay-display");
const delaySlider = document.getElementById("delay-slider");

// Create Label Map
const letters = [..."ABCDEFGHIKLMNOPQRSTUVWXY"];
const labelMap = {
    1:"Accept",
    2:"Delete",
}
let id = 3;
for(let i=0; i<(letters.length); i++) {
    labelMap[id] = letters[i];
    id++;
}

// Set initial delay
let delay = delaySlider.value;
var wait = true;

// Check if users device supports a camera
if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
    launchButton.addEventListener("click", enableCamera);
} else {
    window.alert("There was an error accessing your camera, please ensure your device and browser have camera support");
}

// Add event listener to the delay slider which calls the change delay function
delaySlider.addEventListener("change", changeDelay);

// Fetch the model
var model = undefined;
model_url = "https://raw.githubusercontent.com/Ibrahim-Roy/Sign-Language-Detection-Web-Application/main/model/model.json";

// Load the model
tf.loadGraphModel(model_url).then(function(loadedModel) {
    model = loadedModel;
    // Show model is ready to use.
    launchInstructions.classList.remove("opacity-25");
});

// Changes the delay value each time the slider is moved
function changeDelay(event) {
    delay = delaySlider.value;
    delayDisplay.innerHTML = `Delay between detections in seconds: ${delay}`;
}

// Enable the camera view and start detections.
function enableCamera(event) {
    // Only continue if the model has finished loading.
    if (!model) {
      return;
    }

    // Force video but not audio.
    const constraints = {
      video: true
    };
    // Activate the camera stream.
    navigator.mediaDevices.getUserMedia(constraints).then(function(stream) {
        initialInstructions.classList.add("d-none");
        loadingIcon.classList.remove("d-none");
        video.srcObject = stream;
        video.onloadeddata = function() {
            window.requestAnimationFrame(startDetections);
        };
    }).catch(error => window.alert("A camera is required to use this application, please click the 'LAUNCH' button again and then allow camera permissions when prompted by the browser!"));
}

// Main function for detections
async function startDetections() {
    // Process the images
    const img = tf.browser.fromPixels(video);
    const resized = tf.image.resizeBilinear(img, [640,480]);
    const casted = resized.cast("int32");
    const expanded = casted.expandDims(0);
    const obj = await model.executeAsync(expanded);

    // Get the class and the score from the detection
    const classes = await obj[3].array();
    const scores = await obj[2].array();

    if(wait) {
        loadingIcon.classList.add("d-none");
        mainapp.classList.remove("d-none");
    }

    // Add deetcted class to text box
    if(classes[0][0] && scores[0][0]>0.5){
        textarea.value = textarea.value.slice(0, -1);
        if(labelMap[classes[0][0]] == "Accept"){
            textarea.value += " ";
            textarea.value = textarea.value.replace("  ", "\n");
        } else if(labelMap[classes[0][0]] == "Delete") {
            textarea.value = textarea.value.slice(0, -1);
        } else {
            textarea.value += labelMap[classes[0][0]];
        }
        textarea.value += "_";
        wait = true;
    }

    // Release all resources
    tf.dispose(img);
    tf.dispose(resized);
    tf.dispose(casted);
    tf.dispose(expanded);
    tf.dispose(obj);

    // Iterate to continously keep detecting hand signs
    if(wait) {
        wait = false;
        setTimeout(startDetections, delay*1000);
    } else {
        // Call this function again to keep predicting when the browser is ready.
        window.requestAnimationFrame(startDetections);
    }
}
