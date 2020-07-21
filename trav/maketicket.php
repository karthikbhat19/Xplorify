<?php

if($_SERVER['REQUEST_METHOD']=='POST'){

	include 'config.php';

	$conn = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

	$mode = $_POST['mode'];
	$id = $_POST['id'];
	$email = $_POST['mail'];
	$date = $_POST['date'];

	mysqli_set_charset($conn,'utf8mb4');

	$query = "INSERT INTO `bookings` VALUES (null,'$email','$date','$mode','$id');";

	$res = mysqli_query($conn,$query);

	if($res){
		
		$ins_id = mysqli_insert_id($conn);

		$query = "	
			(
			 	SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Bus' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((bus B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'bus' AND H.BookID = '$ins_id' )
	  		UNION(
	  			SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Train' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((train B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'train' AND H.BookID = '$ins_id' )
	  		UNION(
	  			SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Flight' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((airplane B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'airplane' AND H.BookID = '$ins_id' );";


	  	$res2 = mysqli_query($conn,$query);

	  
	  	while($row[] = mysqli_fetch_assoc($res2)) {

  		$item = $row;

  		$json = json_encode($item);
 
 	}

 		$fp = fopen('bit.json', 'w');
		fwrite($fp, json_encode($json));
		fclose($fp);

 		echo $json;


	}else{
		echo "Failed";
	}

}

?>