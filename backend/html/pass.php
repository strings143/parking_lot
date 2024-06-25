<?php

$mysqli = new mysqli('localhost', 'user01', '123dyu321', 'dyu107');

$tmp=$_POST['num'];
$sqlcheck = "UPDATE `shi` SET `test` = '$tmp'";
$result = mysqli_query($mysqli, $sqlcheck);

?>

