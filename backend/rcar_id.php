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
$result=mysqli_query($connect,"SELECT `car_id` FROM `parking_lot` Where `parking_lot`.`r_id` IS NULL ");//搜索資料表
$count=mysqli_num_rows($result);

 if($count>0)
{
while($row=mysqli_fetch_assoc($result))//讀取陣列的資料，索引值只能是"字串"(關聯索引)
{  
 print(json_encode($row['car_id']));//用json格式輸出
	
}
}
else
{
print(json_encode("e"));

}
	

mysqli_close($connect);
?>
