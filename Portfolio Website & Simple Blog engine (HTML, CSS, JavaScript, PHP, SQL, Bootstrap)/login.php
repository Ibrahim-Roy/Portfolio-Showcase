<?php
    $dbhost = getenv("MYSQL_SERVICE_HOST");
    $dbport = getenv("MYSQL_SERVICE_PORT");
    $dbuser = getenv("DATABASE_USER");
    $dbpwd = getenv("DATABASE_PASSWORD");
    $dbname = getenv("DATABASE_NAME");
    // Create connection
    $conn = new mysqli($dbhost, $dbuser, $dbpwd, $dbname);
    // Check connection
    if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
    }
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        $query = $conn->prepare("SELECT * FROM USERS WHERE email = ?");
        $query->bind_param("s",$email);
        $query->execute();
        $query->store_result();
        if($query->num_rows > 0){
            $query = $conn->prepare("SELECT firstName, password FROM USERS WHERE email = ?");
            $query->bind_param("s",$email);
            $query->execute();
            $query->store_result();
            $query->bind_result($name, $result);
            $query->fetch();
            if($pass===$result){
                session_start();
                $_SESSION['loggedIn'] = TRUE;
                $_SESSION['name'] = $name;
                header("Location: viewBlog.php");
            }
            else{
                echo "<p style='margin-top:1em'>INVALID EMAIL OR PASSWORD</p>";
                include("login.html");
            }
        }
        else{
            echo "<p style='margin-top:1em'>INVALID EMAIL OR PASSWORD</p>";
            include("login.html");
        }
        $query->close();
    }
    $conn->close();
?>
