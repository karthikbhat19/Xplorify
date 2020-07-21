<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	include 'config.php';

	$conn = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

	mysqli_set_charset($conn,'utf8mb4');
	if(mysqli_connect_error($conn)){
		die("Connection Failed: ".mysqli_connect_error($conn));
	}

	$table = $_POST['mode'];

	$query = "SELECT M.ID AS ID, F.Locname AS Source, T.Locname AS Destination, M.Dept_time AS Departure, M.Trv_time AS
			  Travel, M.Price AS Price
	  		  FROM ((`$table` M JOIN locations F ON M.Source = F.Locid) JOIN locations T ON M.Dest = T.Locid)
	  		  WHERE ";

	if(isset($_POST['from']) && !empty($_POST['from'])){
		$from = $_POST['from'];
		$query = $query."F.Locname = '$from' AND ";
	}

	if(isset($_POST['to']) && !empty($_POST['to'])){
		$to = $_POST['to'];
		$query = $query."T.Locname = '$to';";
	}else{
		$query = $query."1;";
	}

	$res = mysqli_query($conn,$query);

	if (mysqli_num_rows($res) > 0) {

  while($row[] = mysqli_fetch_assoc($res)) {

  $item = $row;

  $json = json_encode($item);
 
 }

$fp = fopen('results.json', 'w');
fwrite($fp, json_encode($json));
fclose($fp);


 echo $json;

}else{

 	return "Empty";
 
 }

}

?>