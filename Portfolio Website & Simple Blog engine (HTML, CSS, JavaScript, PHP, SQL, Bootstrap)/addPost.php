<?php
    $dbhost = getenv("MYSQL_SERVICE_HOST");
    $dbport = getenv("MYSQL_SERVICE_PORT");
    $dbuser = getenv("DATABASE_USER");
    $dbpwd = getenv("DATABASE_PASSWORD");
    $dbname = getenv("DATABASE_NAME");
    // Creates connection
    $conn = new mysqli($dbhost, $dbuser, $dbpwd, $dbname);
    // Checks connection
    if ($conn->connect_error) {
     die("Connection failed: " . $conn->connect_error);
    }
    if ($_SERVER["REQUEST_METHOD"] == "POST"){
        $title = $_POST["title"];
        $textBody = $_POST["posttext"];
        date_default_timezone_set("Europe/London");
        $date = date("d F Y");
        $time = date("H:i");
        $sql = "INSERT INTO BLOGPOSTS (title, bodyText, entryDate, entryTime)
        VALUES ('$title', '$textBody', '$date', '$time')";
        if ($conn->query($sql) === TRUE) {
            header("Location:viewBlog.php");
        }
        else{
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    }
    $conn->close();
?>
