<?php                     
    DEFINE ('DBServer', 'localhost');  //127.0.0.1 or localhost
    DEFINE ('DBName', 'park');         //資料庫名稱
    DEFINE ('DBUser', 'root');         //帳號
    DEFINE ('DBPw', '123456');      //密碼

$conDb = mysqli_connect(DBServer,DBUser,DBPw);             
	if (!$conDb) 
	{
    die("Can not connect to DB: " . mysqli_error($conDb));
    exit(); 
    }

   $selectDb = mysqli_select_db($conDb, DBName);
    if (!$selectDb)
	{
    die("Database selection failed: " . mysqli_error($conDb));
    exit(); 
    }
      
	$qid = mysqli_real_escape_string($conDb, $_POST["q_id"]); 
    $query_1 = "DELETE FROM `qr_code` WHERE `q_id`='$qid'  "; 
    $result_1 = mysqli_query($conDb, $query_1) or trigger_error("query error " . mysqli_error($conDb));
	
    mysqli_close($conDb);
?>

