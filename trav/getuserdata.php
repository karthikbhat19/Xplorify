<?php 

if($_SERVER['REQUEST_METHOD']=='POST'){

include 'config.php';

$mail = $_POST['email'];

$conn = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

if (mysqli_connect_error($conn)) {
    die("Connection failed: " . mysqli_connect_error($conn));
}

$sql = "SELECT Name, Email, Mobile, DOB FROM user  WHERE Email = '$mail';";

$res = mysqli_query($conn,$sql);


if (mysqli_num_rows($res) == 1) {
 
 
 while($row[] = mysqli_fetch_assoc($res)) {
 
 $item = $row;
 
 $json = json_encode($item);
 
 }
 
 echo $json;

 
} else {
 echo "Err";
}

}
?>