<?php
    session_start();
?>
<!Doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="reset.css" type="text/css">
        <link rel="stylesheet" href="stylesheet.css" type="text/css">
        <script src="script.js" defer></script>
        <title>Blog</title>
    </head>
    <body>
        <header>
            <h1><a id="heading" href="index.php">IBRAHIM BADAR</a><h1>
            <?php
                if($_SESSION['loggedIn'] == TRUE){
                    echo "<a id='logout' href='logout.php'>LOGOUT</a>";
                    echo "<button id='addblogbtn' type='button' onclick='showAddBlogForm()'>ADD BLOG</button>";
                }
                else{
                    echo "<button id='login' type='button' onclick='loginForm()'>LOGIN</button>";
                }
            ?>
        </header>
        <aside id="addblog">
            <form id="blogEntryForm" method="POST" action="addPost.php">
                <fieldset>
                    <legend>ADD BLOG</legend>
                    <button type="button" id="closeaddBlog">x</button>
                    <input type="text" name="title" placeholder="Title" size="60" id="title">
                    <textarea name="posttext" rows="8" cols="60" placeholder="Enter your text here"
                    id="posttext"></textarea>
                    <input type="submit" value="POST" id="postButton">
                    <input type="button" value="PREVIEW" id="previewButton" onclick="submitForm('preview.php')">
                    <input type="button" value="CLEAR" id="clearButton">
                </fieldset>
            </form>
        </aside>
        <article id="BLOGVIEW">
            <form method="POST">
                <?php
                    if($_SESSION['loggedIn'] == TRUE){
                        echo "<h2>WELCOME " . $_SESSION['name'] . "</h2>";
                    }
                ?>
                <label>FILTER:</label>
                <select name="filter">
                    <option value="ALL" selected>ALL</option>
                    <option value="%January%">JANUARY</option>
                    <option value="%February%">FEBRUARY</option>
                    <option value="%March%">MARCH</option>
                    <option value="%April%">APRIL</option>
                    <option value="%May%">MAY</option>
                    <option value="%June%">JUNE</option>
                    <option value="%July%">JULY</option>
                    <option value="%August%">AUGUST</option>
                    <option value="%September%">SEPTEMBER</option>
                    <option value="%October%">OCTOBER</option>
                    <option value="%November%">NOVEMBER</option>
                    <option value="%December%">DECEMBER</option>
                </select>
                <input type="submit" name="submitfilter" value="SUBMIT" />
            </form>

                <?php
                    $filter = $_POST['months'];
                    $dbhost = getenv("MYSQL_SERVICE_HOST");
                    $dbport = getenv("MYSQL_SERVICE_PORT");
                    $dbuser = getenv("DATABASE_USER");
                    $dbpwd = getenv("DATABASE_PASSWORD");
                    $dbname = getenv("DATABASE_NAME");
                    //Create connection
                    $conn = new mysqli($dbhost, $dbuser, $dbpwd, $dbname);
                    //Check connection
                    if ($conn->connect_error) {
                     die("Connection failed: " . $conn->connect_error);
                    }
                    //FILTER ALL
                    if(isset($_POST["submitfilter"]) and $_POST["filter"] != "ALL"){
                        $query = $conn->prepare("SELECT * FROM BLOGPOSTS WHERE entryDate LIKE ? ORDER BY ID DESC");
                        $query->bind_param("s",$_POST["filter"]);
                        $query->execute();
                        $result = $query->get_result();
                        while($row = $result->fetch_assoc()){
                            echo
                                "<section class='individualPost'><h3>" . $row["entryDate"] . ", " . $row["entryTime"]
                                ."</h3><h2>" . $row["title"] . "</h2><p>" . $row["bodyText"]
                                . "</p></section>";
                        }
                    }
                    else{
                        $query = "SELECT * FROM BLOGPOSTS ORDER BY ID DESC";
                        $result = $conn->query($query);
                        while($row = $result->fetch_assoc()){
                            echo
                                "<section><h3>" . $row["entryDate"] . ", " . $row["entryTime"]
                                ."</h3><h2>" . $row["title"] . "</h2><p>" . $row["bodyText"]
                                . "</p></section>";
                        }
                    }
                    $conn->close();
                ?>
        </article>
    </body>
</html>
