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
	$data_1 = mysqli_real_escape_string($conDb, $_GET["data_1"]); //得到arduino裡的值,且變數名稱跟arduino裡面一樣
    $query_1 = "UPDATE `parking_lot` SET `of` = '$data_1' where `parking_lot`.`car_id` = 'A1'";   //"parking_lot"資料表 ,"feel"欄
    $result_1 = mysqli_query($conDb, $query_1) or trigger_error("query error " . mysqli_error($conDb));
	mysqli_close($conDb);
?>

