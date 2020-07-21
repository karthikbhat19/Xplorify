<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	include 'config.php';

	$conn = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

	$mob = $_POST['mob'];
	$email = $_POST['mail'];

	$sql = "SELECT * FROM user WHERE Email = '$email' AND Mobile = '$mob';";

	$res = mysqli_query($conn,$sql);

	if(mysqli_num_rows($res)==1){

		echo "Success";
	
	}else{

		echo "False";
	
	}

}

?>