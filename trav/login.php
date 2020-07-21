<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){

 include 'config.php';
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $email = $_POST['email'];
 $password = $_POST['password'];
 
 $Sql_Query = "select * from user where Email = '$email' and Password = '$password' limit 1 ";
 
 $check = mysqli_fetch_array(mysqli_query($con,$Sql_Query));
 
 if(isset($check)){
 
 echo "Data Matched";
 }
 else{
 echo "Invalid Username or Password. Please Try Again";
 }
 
 }else{
 echo "Check Again";
 }
//mysqli_close($con);

?>