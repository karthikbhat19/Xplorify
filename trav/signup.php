<?php

 if($_SERVER['REQUEST_METHOD']=='POST')
 {

 include 'config.php';
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $email = $_POST['email'];
 $password = $_POST['pass'];
 $name = $_POST['name'];
 $mob = $_POST['mob'];
 $dob = $_POST['dob'];
 
 $Sql_Query = "select * from user where Email = ' $email'";
 
 $check = mysqli_fetch_array(mysqli_query($con,$Sql_Query));
 
 if(isset($check)){
 
 echo "User Already Exists";
 }
 else{
 $Sql_Query = "INSERT INTO user VALUES('$name','$email','$password','$mob','$dob');";

 $res=mysqli_query($con,$Sql_Query);

 echo "Success";
 }
 
 }else{
 echo "Check Again";
 }
//mysqli_close($con);

?>