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
  
   $address= mysqli_real_escape_string($conDb, $_POST["address"]);
   $query_1="SELECT * FROM `member` where `member`.`plate`= '$address' ";
   $result_1=mysqli_query($conDb,$query_1) or trigger_error("query error " . mysqli_error($conDb));
   $row_1=mysqli_fetch_assoc($result_1);
   $m_id=$row_1['m_id'];
   
   $query_2="SELECT * FROM `reserve` where `reserve`.`m_id`= '$m_id' ";
   $result_2=mysqli_query($conDb,$query_2) or trigger_error("query error " . mysqli_error($conDb));
   $row_2=mysqli_fetch_assoc($result_2);
   $car_id=$row_2['car_id'];
   $output["carid"] = $car_id;
    echo json_encode($output);
    mysqli_close($conDb);
   
?>

