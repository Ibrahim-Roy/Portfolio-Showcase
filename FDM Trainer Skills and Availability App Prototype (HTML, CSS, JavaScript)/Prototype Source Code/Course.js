function AdminAccount(){
    this.save = function(){
        localStorage.setItem("Registry",JSON.stringify(this.userAccounts));
    }
    this.createAccount = function(uname,pass,type){
        var x;
        if(type == "0"){
            x = new SchedulerAccount(uname,pass);
        }
        else if(type == "1"){
            x = new TrainerAccount(uname,pass);
            new SchedulerAccount().addNewTrainer(x.username);
        }
        else{
            x = new TraineeAccount(uname,pass);
        }
        this.userAccounts.push(x);
        this.save();
    }
    this.getAllaccounts = function(){
        return this.userAccounts;
    }
    var x = localStorage.getItem("Registry");
    if(x == null){
        this.userAccounts = [];
        this.save();
    }
    else{
        this.userAccounts = JSON.parse(x);
    }
}
function UserAccount(uname, pass){
    this.username = uname;
    this.password = pass;
    this.login = function(enteredUname, enteredPass){
            const username = enteredUname;
            const password = enteredPass;
            if ((username.length != 0) && (password.length != 0)){
                var userAccounts = new AdminAccount().getAllaccounts();
                for(var i=0; i<userAccounts.length; i++){
                    if(username == userAccounts[i].username && password == userAccounts[i].password){
                        if(userAccounts[i].type == "Scheduler"){
                            var loggedIn = new SchedulerAccount(username,password);
                            localStorage.setItem("LoggedIn",JSON.stringify(loggedIn));
                            window.location.replace("schedulerIndex.html");
                            return;
                        }
                        else if(userAccounts[i].type =="Trainer"){
                            var loggedIn = new TrainerAccount(username,password);
                            localStorage.setItem("LoggedIn",JSON.stringify(loggedIn));
                            window.location.replace("trainerIndex.html");
                            return;
                        }
                        else{
                            var loggedIn = new TraineeAccount(username,password);
                            localStorage.setItem("LoggedIn",JSON.stringify(loggedIn));
                            window.location.replace("traineeIndex.html");
                            return;
                        }
                    }
                }
                document.getElementById("name").style.borderColor = "initial";
                document.getElementById("password").style.borderColor = "initial";
                alert("Incorrect username or password")
            }
            else {
                document.getElementById("name").style.borderColor = "red";
                document.getElementById("password").style.borderColor = "red";
                alert("Please enter a username and password")
            }
    }
    this.logout = function(){
        localStorage.setItem("LoggedIn","False");
        window.location.replace("Index.html");
    }
}

function SchedulerAccount(uname, pass){
    UserAccount.apply(this,arguments);
    this.save = function(key,attribute){
        localStorage.setItem(key,JSON.stringify(attribute));
    }
    this.createCourse = function(courseName, courseType, courseDesc){
        this.allCourses.push(new Course(courseName, courseType, courseDesc));
        this.save("AllCourses",this.allCourses);
    }
    this.createModule = function(moduleName, moduleCourse, moduleDesc){
        var m = new Module(moduleName, moduleCourse, moduleDesc);
        this.allModules.push(m);
        this.save("AllModules",this.allModules);
        for(var i=0; i<this.allCourses.length; i++){
            if(this.allCourses[i].name == moduleCourse){
                var c = new Course(this.allCourses[i].name,this.allCourses[i].description);
                c.addModule(m);
                this.allCourses[i] = c;
                this.save("AllCourses",this.allCourses);
            }
        }
    }
    this.scheduleModule = function(trainerModuleSelect, trainerSelect, trainerTimeSelect, trainerDaySelect){
        var allModules = this.allModules;
        var selectedModule;
        for(var i=0; i<allModules.length; i++){
            if(allModules[i].name == trainerModuleSelect){
                selectedModule = new Module(allModules[i].name, allModules[i].course, allModules[i].description);
                break;
            }
        }
        selectedModule.schedule(trainerSelect, trainerTimeSelect, trainerDaySelect);
    }
    this.getCourses = function(){
        return this.allCourses;
    }
    this.getModules = function(){
        return this.allModules;
    }
    this.getTrainers = function(){
        return this.allTrainers;
    }
    this.addNewTrainer = function(trainer){
        this.allTrainers.push(trainer);
        this.save("AllTrainers",this.allTrainers);
    }
    this.type = "Scheduler";
    var x = localStorage.getItem("AllCourses");
    if(x == null){
        this.allCourses = [];
        this.save("AllCourses",this.allCourses);
    }
    else{
        this.allCourses = JSON.parse(x);
    }
    var y = localStorage.getItem("AllModules");
    if(y == null){
        this.allModules = [];
        this.save("AllModules",this.allModules);
    }
    else{
        this.allModules = JSON.parse(y);
    }
    var z = localStorage.getItem("AllTrainers");
    if(z == null){
        this.allTrainers = [];
        this.save("AllTrainers",this.allTrainers);
    }
    else{
        this.allTrainers = JSON.parse(z);
    }
}

function TrainerAccount(uname, pass){
    UserAccount.apply(this,arguments);
    this.type = "Trainer";
}

function TraineeAccount(uname, pass){
    UserAccount.apply(this,arguments);
    this.type = "Trainee";
}

function Course(name, type, description){
    this.addModule = function(moduleObj){
        this.modules.push(moduleObj);
    }
    this.name = name;
    this.type = type;
    this.modules = [];
    this.description = description;
}

function Module(name, course, description){
    this.schedule = function(tname, time, day){
        this.trainer = tname;
        masterSchedule = new MasterSchedule();
        masterSchedule.add(this.name, this.trainer, time, day);
    }
    this.name = name;
    this.trainer = "";
    this.course = course;
    this.description = description;
}

function Schedule(){
    this.schedule = new Array(5);
    for(var i = 0; i<5; i++){
        this.schedule[i] = new Array(5);
        for(var j=0; j<5; j++){
            this.schedule[i][j] = [];
        }
    }
}

function MasterSchedule(){
    this.save = function(){
        localStorage.setItem("MasterSchedule",JSON.stringify(this.schedule));
    }
    this.add = function(mname, tname, time, day){
        var d = parseInt(day);
        var t = parseInt(time);
        this.schedule[d][t].push([mname,tname]);
        this.save();
    }
    this.getTrainerSchedule = function(trainer){
        var trainerSchedule = new Schedule().schedule;
        for(var i=0; i<5; i++){
            for(var j=0; j<5; j++){
                for(var z=0; z<this.schedule[i][j].length; z++){
                    console.log(trainer);
                    console.log(this.schedule[i][j][z][1])
                    if(this.schedule[i][j][z][1] == trainer){
                        console.log("Works");
                        trainerSchedule[i][j] = [this.schedule[i][j][z][0],this.schedule[i][j][z][1]];
                        console.log(trainerSchedule);
                    }
                }
            }
        }
        return trainerSchedule;
    }
    var x = localStorage.getItem("MasterSchedule");
    if (x!=null){
        this.schedule = JSON.parse(x);
    }
    else{
        this.schedule = new Schedule().schedule;
        this.save();
    }
}

//Handlers

function adminLogin(){
    var uname = document.getElementById("ALUname").value;
    var pass = document.getElementById("ALPass").value;
    if(uname!="" && pass!=""){
        if(uname==localStorage.getItem("AdminUname") && pass==localStorage.getItem("AdminPass")){
            event.preventDefault();
            window.location.replace("adminIndex.html");
        }
        else{
            event.preventDefault();
            var p = document.getElementById("ifIncorrect");
            p.innerHTML = "Incorrect Login Details!"
        }
    }
    var admin = {username:"Admin", type:"Admin"};
    localStorage.setItem("LoggedIn",JSON.stringify(admin));
}

function adminLogout(){
    localStorage.removeItem("LoggedIn");
    window.location.replace("index.html");
}

function displayOrHideElement(elementId){
    var x = document.getElementById(elementId);
    if(x.style.display === "none"){
        x.style.display = "block";
    }
    else{
        x.style.display = "none";
    }
}

function createAccountButtonHandler(){
    var uname = document.getElementById("CAUname").value;
    var pass = document.getElementById("CAPass").value;
    if(uname!="" && pass!=""){
        type = document.getElementById("CAType").value;
        (new AdminAccount().createAccount(uname,pass,type));
    }
}

function clearLocalStorage(){
    localStorage.clear();
}

function loginButtonHandler(){
    const username = document.getElementById("name").value;
    const password = document.getElementById("password").value;
    new UserAccount().login(username,password);
}

function logoutButtonHandler(){
    new UserAccount().logout();
}

function createCourseButton(){
    const courseName = document.forms["courseForm"]["cname"].value;
    const courseType = document.forms["courseForm"]["ctype"].value;
    const courseDesc = document.forms["courseForm"]["cdesc"].value;
    if (courseName != "" && courseType != "" && courseDesc != ""){
        new SchedulerAccount().createCourse(courseName, courseType, courseDesc);
        event.preventDefault()
    }
    else {
        if (courseName === ""){
            document.getElementById("cname").style.borderColor = "red";
            event.preventDefault()
        }
        else if (courseName !== ""){
            document.getElementById("cname").style.borderColor = "initial";
        }
        if(courseType === ""){
            document.getElementById("ctype").style.borderColor = "red";
            event.preventDefault()
        }
        else if(courseType !== ""){
            document.getElementById("ctype").style.borderColor = "initial";
        }

        if(courseDesc === ""){
            document.getElementById("cdesc").style.borderColor = "red";
            event.preventDefault()
        }
        else if(courseDesc !== ""){
            document.getElementById("cdesc").style.borderColor = "initial";
        }
    }
    var courseList = new SchedulerAccount().getCourses();
    var moduleSelect = document.getElementById("mcourse");
    var newCourseName = courseList[courseList.length-1].name;
    var courseOption = document.createElement("option");
    courseOption.text = newCourseName;
    courseOption.value = newCourseName;
    moduleSelect.add(courseOption);
}

function clearText() {
    return window.confirm("Are you sure you want to clear all the content?");
}

function addNewModuleButton(){
    const moduleName = document.forms["newModuleForm"]["mname"].value;
    const moduleCourse = document.forms["newModuleForm"]["mcourse"].value;
    const moduleDesc = document.forms["newModuleForm"]["mdesc"].value;

    if (moduleName != "" && moduleCourse != "" && moduleDesc != ""){
        new SchedulerAccount().createModule(moduleName, moduleCourse, moduleDesc);
        event.preventDefault()
    }
    else {
        if (moduleName === ""){
            document.getElementById("mname").style.borderColor = "red";
            event.preventDefault()
        }
        else if (moduleName !== ""){
            document.getElementById("mname").style.borderColor = "initial";
        }
        if (moduleCourse === ""){
            document.getElementById("mcourse").style.borderColor = "red";
            event.preventDefault()
        }
        else if (moduleCourse !== ""){
            document.getElementById("mcourse").style.borderColor = "initial";
        }
        if (moduleDesc === ""){
            document.getElementById("mdesc").style.borderColor = "red";
            event.preventDefault()
        }
        else if (moduleDesc !== ""){
            document.getElementById("mdesc").style.borderColor = "initial";
        }
    }
    var modulesList = new SchedulerAccount().getModules();
    var trainerModuleSelect = document.getElementById("trainerModuleSelect");
    var modulesOption = document.createElement("option");
    var modulesName = modulesList[modulesList.length-1].name;
    modulesOption.text = modulesName;
    modulesOption.value = modulesName;
    trainerModuleSelect.add(modulesOption);
}

function scheduleHandler(){
    const trainerModuleSelect = document.forms["scheduleTrainerForm"]["trainerModuleSelect"].value;
    const trainerSelect = document.forms["scheduleTrainerForm"]["trainerSelect"].value;
    const trainerTimeSelect = document.forms["scheduleTrainerForm"]["trainerTimeSelect"].value;
    const trainerDaySelect = document.forms["scheduleTrainerForm"]["trainerDateSelect"].value;

    if (trainerModuleSelect != "" && trainerSelect!= "" && trainerTimeSelect != "" && trainerDaySelect != ""){
        new SchedulerAccount().scheduleModule(trainerModuleSelect, trainerSelect, trainerTimeSelect, trainerDaySelect);
        event.preventDefault();
    } else {
        if (trainerModuleSelect === ""){
            document.getElementById("trainerModuleSelect").style.borderColor = "red";
            event.preventDefault()
        }
        else if (trainerModuleSelect !== ""){
            document.getElementById("trainerModuleSelect").style.borderColor = "initial";
        }
        if (trainerSelect === ""){
            document.getElementById("trainerSelect").style.borderColor = "red";
            event.preventDefault()
        }
        else if (trainerSelect !== ""){
            document.getElementById("trainerSelect").style.borderColor = "initial";
        }
        if (trainerTimeSelect === ""){
            document.getElementById("trainerTimeSelect").style.borderColor = "red";
            event.preventDefault()
        }
        else if (trainerTimeSelect !== ""){
            document.getElementById("trainerTimeSelect").style.borderColor = "initial";
        }
        if (trainerDaySelect === ""){
            document.getElementById("trainerDateSelect").style.borderColor = "red";
            event.preventDefault()
        }
        else if (trainerDaySelect !== ""){
            document.getElementById("trainerDateSelect").style.borderColor = "initial";
        }
    }
}

function viewtrainerAvailibilityButton(){
    const trainerSelect = document.forms["viewtrainerAvailibilityForm"]["trainerAvailibilitySelect"].value;
    var table = document.getElementById("trainerAvailibilityTable");
    table.style = ("display: none;");
    if (trainerSelect != ""){
        if(table.hasChildNodes()){
            for(var i = table.rows.length - 1; i > -1; i--)
            {
                table.deleteRow(i);
            }
        }
        var userSchedule = new MasterSchedule().getTrainerSchedule(trainerSelect);
        console.log(new MasterSchedule().schedule);
        console.log(userSchedule);
        var days = ["","Monday","Tuesday","Wednesday","Thursday","Friday"];
        var times = ["","09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00", "17:00 - 19:00"];
        for(i=0; i<1; i++){
            var row = table.insertRow(i);
            for(var j=0; j<6; j++){
                var cell = row.insertCell(j);
                cell.innerHTML = times[j];
            }
        }
        for(i=1; i<6; i++){
            var row = table.insertRow(i);
            for(var j=0; j<1; j++){
                var cell = row.insertCell(j);
                cell.innerHTML = days[i];
            }
        }
        for(var i=1; i<6; i++){
            var row = table.rows[i];
            for(var j=1; j<6; j++){
                var cell = row.insertCell(j);
                if(userSchedule[i-1][j-1].length !=0){
                    var mname = userSchedule[i-1][j-1][0];
                    cell.innerHTML = mname;
                }
                else{
                    cell.innerHTML = "Available";
                }
            }
        }
        table.style = ("display: block;");
        event.preventDefault();
    } else {
        if (trainerSelect === ""){
            document.getElementById("trainerAvailibilitySelect").style.borderColor = "red";
            event.preventDefault()
        }
        else if (trainerSelect !== ""){
            document.getElementById("trainerAvailibilitySelect").style.borderColor = "initial";
        }
    }
}

function trainerViewScheduleHandler(){
    var user = JSON.parse(localStorage.getItem("LoggedIn"));
    var userSchedule = new MasterSchedule().getTrainerSchedule(user.username);
    document.getElementById("viewTrainerScheduleB").style = ("display: none;");
    console.log(new MasterSchedule().schedule);
    console.log(userSchedule);
    var table = document.getElementById("timetable");
    var days = ["","Monday","Tuesday","Wednesday","Thursday","Friday"];
    var times = ["","09:00 - 11:00", "11:00 - 13:00", "13:00 - 15:00", "15:00 - 17:00", "17:00 - 19:00"];
    for(i=0; i<1; i++){
        var row = table.insertRow(i);
        for(var j=0; j<6; j++){
            var cell = row.insertCell(j);
            cell.innerHTML = times[j];
        }
    }
    for(i=1; i<6; i++){
        var row = table.insertRow(i);
        for(var j=0; j<1; j++){
            var cell = row.insertCell(j);
            cell.innerHTML = days[i];
        }
    }
    for(var i=1; i<6; i++){
        var row = table.rows[i];
        for(var j=1; j<6; j++){
            var cell = row.insertCell(j);
            if(userSchedule[i-1][j-1].length !=0){
                var mname = userSchedule[i-1][j-1][0];
                cell.innerHTML = mname + "";
            }
            else{
                cell.innerHTML = "";
            }
        }
    }
}

function loadExistingObjects(){
    var trainerList = new SchedulerAccount().getTrainers();
    /* displaying the trainer names in the form */
    var trainerSelect = document.getElementById("trainerSelect");
    for ( var x=0; x < trainerList.length; x++ ) {
        var trainerName = trainerList[x];
        var trainerOption = document.createElement("option");
        trainerOption.text = trainerName;
        trainerOption.value = trainerName;
        trainerSelect.add(trainerOption);
    }
    var availableTrainerList = new SchedulerAccount().getTrainers();
    var trainerAvailibilitySelect = document.getElementById("trainerAvailibilitySelect");
    for ( var x=0; x < trainerList.length; x++ ) {
        var availableTrainerName = availableTrainerList[x];
        var availableTrainerOption = document.createElement("option");
        availableTrainerOption.text = availableTrainerName;
        availableTrainerOption.value = availableTrainerName;
        trainerAvailibilitySelect.add(availableTrainerOption);
    }
    var modulesList = new SchedulerAccount().getModules();

    var trainerModuleSelect = document.getElementById("trainerModuleSelect");

    for ( var x=0; x < modulesList.length; x++ ) {
        var modulesName = modulesList[x].name;
        var modulesOption = document.createElement("option");
        modulesOption.text = modulesName;
        modulesOption.value = modulesName;
        trainerModuleSelect.add(modulesOption);
    }
    var courseList = new SchedulerAccount().getCourses();
    var moduleSelect = document.getElementById("mcourse");
    for ( var x=0; x < courseList.length; x++ ) {
        var courseName = courseList[x].name;
        var courseOption = document.createElement("option");
        courseOption.text = courseName;
        courseOption.value = courseName;
        moduleSelect.add(courseOption);
    }
}
