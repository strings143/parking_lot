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
    
   $oldpassword = $_POST['oldpassword']; 
   $newpassword =  $_POST['newpassword']; 
   $account=$_POST['account'];

   $query = "SELECT  * FROM `member` WHERE `account`= '$account' AND `password`='$oldpassword'"; 
    $result = mysqli_query($conDb, $query);
    $count=mysqli_num_rows($result);
    if($count>0&&$newpassword!=$oldpassword)
    {
        $tmp= "UPDATE `member` SET `password` = '$newpassword' where `member`.`account` = '$account'";  
        $tmp_1 = mysqli_query($conDb, $tmp);
        $output["error"] = false;
    }
    else
    {
        $output["error"] = true;
    }

    echo json_encode($output);
    mysqli_close($conDb);
   
?>

