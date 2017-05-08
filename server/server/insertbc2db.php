<?php
    include("CBroadcastItem.php");

    $insertdat  = $_POST['dat'];

    $host = "localhost";
    $user = "tesl4";
    $pwd = "wmf@081007";
    $db = "bc_list";
    try{
        $conn = new PDO("mysql:host=$host;dbname=$db", $user, $pwd);
        if($conn)
        {
            $json = json_decode($insertdat);
            if($json)
            {
                $iserrorrepoting = $error_log;


                //INSERT INTO `bc_list` (`uid`, `bc_title`, `bc_addr`, `bc_genre`, `thumb_addr`) VALUES
                //  (NULL, 'teet1', 'test1addr', 'tead1genre', ''),
                //  (NULL, 'test2', 'test2addr', 'test2gnere', '')

                $query = "INSERT INTO `bc_list` (`uid`, `bc_title`, `bc_addr`, `bc_genre`,`bc_genre_sub`, `thumb_addr`) VALUES ";
                $size = count($json);
                $i = 0;
                $row = "";
                while($i < $size)
                {
                    $row  .= '(NULL,';
                    $row  .= "'".$json[$i]->title."',";
                    $row  .= "'".$json[$i]->address."',";
                    $row  .= "'".$_POST['genre']."',";
                    $row  .= "'".$_POST['genre_sub']."',";
                    $row  .= "'')";
                    if($i != $size-1) $row .= ",";
                    $i++;
                }
                $query .= $row;
                $insert = $conn->prepare($query);
                $res = $insert->execute();


                if($res)
                {
                    echo "데이터 추가에 성공했습니다.";
                }
                else
                {
                    echo "데이터 추가에 실패했습니다.";
                }

                //return "succeed";
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
