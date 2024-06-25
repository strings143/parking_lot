<?php
$host='localhost';
$name='root';
$pwd='123456';
$db='park';
$connect = mysqli_connect($host,$name,$pwd) or die("connection failed");//進去資料庫
mysqli_query($connect,"SET CHARACTER SET 'UTF8';"); //這函式為查詢的意思,而這部份是為了避免出現亂碼而設定的
mysqli_query($connect,'SET NAMES UTF8;');
mysqli_query($connect,'SET CHARACTER_SET_CLIENT=UTF8;');
mysqli_query($connect,'SET CHARACTER_SET_RESULTS=UTF8;');
mysqli_select_db($connect,$db) or die("db selection failed");//連上所要的資料庫
$result=mysqli_query($connect,"SELECT * FROM `parking_lot` ");//搜索資料表
while($row=mysqli_fetch_assoc($result))
{
    $tmp=$row['of'];//資料表裡的欄位
    print(json_encode($tmp));//用json格式輸出
}
mysqli_close($connect);
?>
