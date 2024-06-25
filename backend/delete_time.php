<?php                     
    DEFINE ('DBServer', 'localhost');  //127.0.0.1 or localhost
    DEFINE ('DBName', 'park');         //資料庫名稱
    DEFINE ('DBUser', 'root');         //帳號
    DEFINE ('DBPw', '123456');      //密碼
    date_default_timezone_set('Asia/Taipei');
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
    $now_time = date('Y/m/d H:i:s');
    
    $query_2="SELECT * FROM `qr_code` where `qr_code`.`arrive`= 0 AND `end_time` <= '$now_time'";
    $result_2=mysqli_query($conDb,$query_2) or trigger_error("query error " . mysqli_error($conDb));
    $row_2=mysqli_fetch_assoc($result_2);
    $q_id=$row_2['q_id'];
    
    $f="SELECT * FROM `reserve` where `reserve`.`q_id`= '$q_id' ";//new
    $g=mysqli_query($conDb,$f) or trigger_error("query error " . mysqli_error($conDb));//new
    $row_test=mysqli_fetch_assoc($g);
    $car_id=$row_test['car_id'];//new
    

    $sum1 = "UPDATE `parking_lot` SET `r_id` = NUll where `parking_lot`.`car_id` = '$car_id' "; //new  
    $sum2 = mysqli_query($conDb, $sum1) or trigger_error("query error " . mysqli_error($conDb));
  
	$query_1 = "DELETE FROM `qr_code` WHERE `end_time` <= '$now_time'  AND  `arrive` = 0 "; 
    $result_1 = mysqli_query($conDb, $query_1) or trigger_error("query error " . mysqli_error($conDb));

    $query_3 = "DELETE FROM `reserve` WHERE `reserve`.`q_id`=  '$q_id'"; 
    $result_3 = mysqli_query($conDb, $query_3) or trigger_error("query error " . mysqli_error($conDb));

	
    mysqli_close($conDb);
?>

