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
   $password = $_POST['password']; 
   $account =  $_POST['account']; 
   $query = "SELECT  * FROM `member` WHERE `account`= '$account' AND `password`='$password'"; 
    $result = mysqli_query($conDb, $query);
    $count=mysqli_num_rows($result);//搜索有無這筆資料,如果有count>1
    
    if($count>0)
    {
        $output["error"] = false;//搭配app的判斷式,接收error,然後布林值概念,要是成立"false"將不會啟動if的功能
    }
    else
    {
        $output["error"] = true;
    }
    echo json_encode($output);
    mysqli_close($conDb);
   
?>

