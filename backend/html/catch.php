<?php
  DEFINE ('DBServer', 'localhost');  //127.0.0.1 or localhost
  DEFINE ('DBName', 'park');         //資料庫名稱
  DEFINE ('DBUser', 'root');         //帳號
  DEFINE ('DBPw', 'amy102523');      //密碼
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
$url="https://proj.dyu.edu.tw/user01/shi/show.php";
$data=file_get_contents($url);    //取得json字串
$data=json_decode($data,true);    //對json解碼
$data_1= $data["plate"];




$query = "UPDATE `qr_code` SET `arrive` = 1 where `qr_code`.`q_id`='$data_1' AND `end_time` >= '$now_time'";
$result = mysqli_query($conDb, $query) or trigger_error("query error " . mysqli_error($conDb));  



$query_1="SELECT * FROM `member` where `member`.`plate`= '$data_1' ";
$result_1=mysqli_query($conDb,$query_1) or trigger_error("query error " . mysqli_error($conDb));
$row_1=mysqli_fetch_assoc($result_1);
$m_id=$row_1['m_id'];

$query_2="SELECT * FROM `reserve` where `reserve`.`m_id`= '$m_id' ";
$result_2=mysqli_query($conDb,$query_2) or trigger_error("query error " . mysqli_error($conDb));
$row_2=mysqli_fetch_assoc($result_2);
$qid=$row_2['q_id'];

$f="SELECT * FROM `reserve` where `reserve`.`q_id`= '$qid' ";//new
$g=mysqli_query($conDb,$f) or trigger_error("query error " . mysqli_error($conDb));//new
$row_test=mysqli_fetch_assoc($g);
$car_id=$row_test['car_id'];//new


$query_3 = "DELETE FROM `qr_code` WHERE `qr_code`.`q_id`= '$qid'  "; 
$result_3 = mysqli_query($conDb, $query_3) or trigger_error("query error " . mysqli_error($conDb));
$num = "DELETE FROM `reserve` WHERE `reserve`.`q_id`= '$qid'  "; 
$num1 = mysqli_query($conDb, $num) or trigger_error("query error " . mysqli_error($conDb));



$tmp="SELECT * FROM `qr_code` where `qr_code`.`q_id`='$data_1' ";
$tmp1=mysqli_query($conDb,$tmp) or trigger_error("query error " . mysqli_error($conDb));
$row=mysqli_fetch_assoc($tmp1);
$arrive=$row['arrive'];


if($arrive>0)
{
    echo "歡迎進入";
    header("Location:http://163.23.24.56/html/in.html");
    exit; 
}
else if($m_id>0)
{
  $sum1 = "UPDATE `parking_lot` SET `r_id` = NUll where `parking_lot`.`car_id` = '$car_id' "; 
  $sum2 = mysqli_query($conDb, $sum1) or trigger_error("query error " . mysqli_error($connect));
  
    echo "謝謝關臨";
    header("Location:http://163.23.24.56/html/out.html");
    exit; 
}
else 
{
  $a = "DELETE FROM `qr_code` WHERE `qr_code`.`q_id`= '$data_1'  "; 
  $b = mysqli_query($conDb, $a) or trigger_error("query error " . mysqli_error($conDb));
  $c = "DELETE FROM `reserve` WHERE `reserve`.`q_id`=  '$data_1'  "; 
  $d = mysqli_query($conDb, $c) or trigger_error("query error " . mysqli_error($conDb));
 
    echo "超過預約囉";
    header("Location:http://163.23.24.56/html/getout.html");
    exit; 
}




mysqli_close($conDb);

?>

   
