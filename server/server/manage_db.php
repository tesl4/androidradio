
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <style>
        table {
            border: 1px solid gray;
            border-collapse: collapse;
        }

        td {
            border: 1px solid gray;
            padding: 5px;
        }
    </style>
</head>
<body>
    <?php

    $k_bcsize = "numberofentries";
    $k_File = "File";
    $k_Title = "Title";

    // DB connection info
    $host = "localhost";
    $user = "tesl4";
    $pwd = "wmf@081007";
    $db = "bc_list";
    try{
        $conn = new PDO("mysql:host=$host;dbname=$db", $user, $pwd);
        if($conn)
        {
            if(is_uploaded_file($_FILES['pls']['tmp_name']))
            {
                //echo "업로드한 파일명 : ". $_FILES['pls']['tmp_name'] . '</br>';
                //echo "업로드한 파일명 : ". $_FILES['pls']['size'] . '</br>';
                //echo "업로드한 파일명 : ". $_FILES['pls']['name'] . '</br>';
                //echo "업로드한 파일명 : ". $_FILES['pls'] . '</br>';

                $fp = fopen($_FILES['pls']['tmp_name'], "r") or die("파일열기에 실패하였습니다");
                while(!feof($fp)){
                    $buffer .= fread($fp,$_FILES['pls']['size']);
                }
                $fcon = htmlspecialchars($buffer);
                $splited = explode("\n", $fcon);
                $i =0;
                $hashinfo = array();
                while($i < count($splited))
                {
                    //echo $splited[$i]."</br>";

                    $atom = explode("=", $splited[$i]);
                    if(count($atom) >= 2)
                    {
                        //echo $atom[0]. " - ".$atom[1]."</br>";
                        $hashinfo[$atom[0]] = $atom[1];
                    }
                    $i++;
                }

                $i = 0;
                while($i <$hashinfo[$k_bcsize])
                {
                    $ftitle = $k_Title . strval($i+1);
                    $faddr = $k_File . strval($i+1);
                    echo "제목 : ".$hashinfo[$ftitle]."</br>";
                    echo "주소 : ".$hashinfo[$faddr]."</br>";
                    echo "--------------------------------------------------"."</br>"."</br>";
                    $i++;
                }

                fclose($fp);

            }
        }
        else
        {
            echo "no";
        }
    }
    catch(Exception $e){
        die(print_r($e));
    }
    ?>
</body>
</html>

