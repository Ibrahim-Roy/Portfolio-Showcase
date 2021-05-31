<!Doctype html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="reset.css" type="text/css">
        <link rel="stylesheet" href="stylesheet.css" type="text/css">
        <script src="script.js" defer></script>
        <title>Preview</title>
    </head>
    <body>
        <?php
            $previewTitle = $_POST['title'];
            $previewText = $_POST['posttext'];
            echo
            "<aside id='addblog'>"
                . "<form id='blogEntryForm' method='POST' action='addPost.php'>"
                    . "<fieldset>"
                        . "<legend>ADD BLOG</legend>"
                        . "<button type='button' id='closeaddBlog'>x</button>"
                        . "<input type='text' name='title' placeholder='Title' value='".$previewTitle."' size='60' id='title'>"
                        . "<textarea name='posttext' rows='8' cols='60' placeholder='Enter your text here'"
                        . "id='posttext'>".$previewText."</textarea>"
                        . "<input type='submit' value='POST' id='postButton'>"
                        . "<input type='button' value='PREVIEW' id='previewButton' onclick='submitForm(\"preview.php\")'>"
                        . "<input type='button' value='CLEAR' id='clearButton'>"
                    . "</fieldset>"
                . "</form>"
            . "</aside>";
            echo
            "<article id='BLOGVIEW'>
                <section class='individualPost'><h3>" . date("d F Y") . ", " . date("H:i")
                ."</h3><h2>" . $previewTitle . "</h2><p>" . $previewText
                . "</p></section>
                <input type='button' id='editButton' value='EDIT' onclick='showAddBlogForm()'>";
        ?>
    </body>
</html>
