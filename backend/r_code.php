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
  
    $end_time=date('Y-m-d H:i:s', strtotime('+20second'));
    //$end_time=date('Y-m-d H:i:s', strtotime('+20 minute'));
    $end_time1=date('Y-m-d H:i:s', strtotime('+40second'));
	$carid = mysqli_real_escape_string($conDb, $_POST["car_id"]); 
    $query_1 = "UPDATE `parking_lot` SET `r_id` = '1' where `parking_lot`.`car_id` = '$carid' ";   
    $result_1 = mysqli_query($conDb, $query_1) or trigger_error("query error " . mysqli_error($conDb));
    
    
	$qid = mysqli_real_escape_string($conDb, $_POST["q_id"]); 
    $query_2 = "INSERT INTO `qr_code`(`q_id`, `end_time`, `arrive`) VALUES ('$qid','$end_time',0)"; 
    $result_2 = mysqli_query($conDb, $query_2) or trigger_error("query error " . mysqli_error($conDb));
	
    $account= mysqli_real_escape_string($conDb, $_POST["account"]);
    $query_3="SELECT * FROM `member` where `member`.`account`= '$account' ";
    $result_3=mysqli_query($conDb,$query_3) or trigger_error("query error " . mysqli_error($conDb));
    $row=mysqli_fetch_assoc($result_3);
    $m_id=$row['m_id'];

    $add="INSERT INTO `reserve`(`m_id`, `car_id`, `q_id`) VALUES ('$m_id','$carid','$qid')";
    $result_4 = mysqli_query($conDb, $add) or trigger_error("query error " . mysqli_error($conDb));
	
    mysqli_close($conDb);
?>

