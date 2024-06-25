
<?php

$mysqli = new mysqli('localhost', 'user01', '123dyu321', 'dyu107');

$sqlcheck = "SELECT * FROM `shi` WHERE 1";

$result = mysqli_query($mysqli, $sqlcheck);

$obj = mysqli_fetch_object($result);

$test=$obj->test;

$output = array('plate' => $test);
echo json_encode($output);

?>
