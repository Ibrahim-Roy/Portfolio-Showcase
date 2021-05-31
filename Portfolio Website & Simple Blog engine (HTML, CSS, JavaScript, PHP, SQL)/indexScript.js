var eduPosition = 1;
var expPosition = 1;
var achPosition = 1;
function scroll2Right(ID){
    if(ID=="Education"){
        position = eduPosition;
    }
    else if(ID=="Experience"){
        position = expPosition;
    }
    else if(ID=="Acheivements"){
        position = achPosition;
    }
    for(var i=1; i<=document.getElementById(ID).childElementCount; i++){
        document.querySelector("#"+ID+" li:nth-child("+i+")").style.display = "none";
    }
    position++;
    if(position > document.getElementById(ID).childElementCount){
        position = 1;
    }
    document.querySelector("#"+ID+" li:nth-child("+position+")").style.display = "list-item";
    if(ID=="Education"){
        eduPosition = position;
    }
    else if(ID=="Experience"){
        expPosition = position
    }
    else if(ID=="Acheivements"){
        achPosition = position;
    }
}
function scroll2Left(ID){
    if(ID=="Education"){
        position = eduPosition;
    }
    else if(ID=="Experience"){
        position = expPosition
    }
    else if(ID=="Acheivements"){
        position = achPosition;
    }
    for(var i=1; i<=document.getElementById(ID).childElementCount; i++){
        document.querySelector("#"+ID+" li:nth-child("+i+")").style.display = "none";
    }
    position--;
    if(position < 1){
        position = document.getElementById(ID).childElementCount;
    }
    document.querySelector("#"+ID+" li:nth-child("+position+")").style.display = "list-item";
    if(ID=="Education"){
        eduPosition = position;
    }
    else if(ID=="Experience"){
        expPosition = position
    }
    else if(ID=="Acheivements"){
        achPosition = position;
    }
}
