function clearForm(){
    let userChoice = window.confirm('Are you sure you want to clear all text?');
    if(userChoice){
        document.getElementById("title").value = "";
        document.getElementById("posttext").value = "";
    }
}
function closeForm(){
    let userChoice = window.confirm('Are you sure you want to clear all text?');
    if(userChoice){
        window.location.href = "viewBlog.php";
    }
}
function checkFields(){
    document.getElementById("title").style.borderColor = "black";
    document.getElementById("posttext").style.borderColor = "black";
    let x = document.getElementById("title").value;
    let y = document.getElementById("posttext").value;
    if(x.length==0 || y.length==0){
        event.preventDefault();
        if(x.length==0){
            document.getElementById("title").style.borderColor = "red";
            document.getElementById("title").placeholder = "Title (Required!)";
        }
        if(y.length==0){
            document.getElementById("posttext").style.borderColor = "red";
            document.getElementById("posttext").placeholder = "Blog Post (Required!)";
        }
    }
}
function loginForm(){
    window.location.href = "login.html";
}
function showAddBlogForm(){
    document.getElementById('addblog').style.display = "block";
    document.getElementById('addblog').style.zIndex = "9999";
    document.querySelector('body > *:not(#addblog)').style.filter = "blur(1em)";
    document.querySelector('#BLOGVIEW > *').style.display = "none";
    var x, i;
    x = document.querySelectorAll('section');
    for (i=0; i< x.length; i++){
        x[i].style.display = "none";
    }
    var editButton = document.getElementById('editButton');
    console.log(editButton);
    if(editButton != null){
        editButton.style.display = "none";
    }
}
function submitForm(action){
    var form = document.getElementById("blogEntryForm");
    form.action = action;
    form.submit();
}
document.getElementById("clearButton").addEventListener("click",clearForm);
document.getElementById("postButton").addEventListener("click",checkFields);
document.getElementById("closeaddBlog").addEventListener("click",closeForm);
