

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
        class Broadcast
        {
            public $uid;
            public $bc_title = "";
            public $bc_addr = "";
            public $bc_genre;
            public $thumbnail_addr = "";
        }

    // DB connection info
        $host = "localhost";
        $user = "tesl4";
        $pwd = "wmf@081007";
        $db = "bc_list";
        try{
            $conn = new PDO( "mysql:host=$host;dbname=$db", $user, $pwd);
            if($conn)
            {
                $query = $conn->prepare('SELECT * FROM bc_list');
                $query->execute();
                $list = $query->fetchAll();

                echo "size is : " . count($list)."</br>";

                $i = 0;
                $res = array();
                foreach($list as $row)
                {
                    $info = new Broadcast();
                    $info->uid = $row['uid'];
                    $info->bc_title = $row['bc_title'];
                    $info->bc_genre = $row['bc_genre'];
                    $info->bc_addr = $row['bc_addr'];
                    $info->thumbnail_addr   = $row['thumbnail_addr'];

                    $i++;
                    array_push($res, $info);
                    echo json_encode($row)."</br>";
                }
                $josnres = json_encode($res);
                echo "</br>"."{"."\"bc_list\":  ".$josnres."}";
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