<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	include 'config.php';

	$conn = mysqli_connect($HostName, $HostUser, $HostPass, $DatabaseName);

	$email = $_POST['mail'];

	mysqli_set_charset($conn,'utf8mb4');

	if(mysqli_connect_error($conn)){
		die("Connection Failed: ".mysqli_connect_error($conn));
	}

	$query = "	
			(
			 	SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Bus' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((bus B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'bus' AND H.Email = '$email'
	  		)
	  		UNION(
	  			SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Train' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((train B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'train' AND H.Email = '$email'
	  		)
	  		UNION(
	  			SELECT B.ID AS Ticket_ID, F.Locname AS Source, T.Locname AS Destination, B.Dept_time AS Departure, B.Trv_time AS
			  	Travel, 'Flight' as Type, H.Travel_Date as Date, H.BookID AS Booking_ID
	  		  	FROM (((airplane B JOIN locations F ON B.Source = F.Locid) JOIN locations T ON B.Dest = T.Locid) 
	  		  		JOIN bookings H ON H.Travel_ID = B.ID) 
	  		  	WHERE H.Mode = 'airplane' AND H.Email = '$email'
	  		);";


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