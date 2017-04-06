<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: returning connection Object for the DB Connectivity
  
	$conn = mysql_connect('*****DB Server Link******', '***Master Name***', '***DB NAME***');
	 if (!$conn)
    {
	 die('Could not connect: ' . mysql_error());
	}
	mysql_select_db("***DB NAME***", $conn);
	define("GOOGLE_API_KEY", "****GCM API KEY****");   //Setting Server API Key for the Google Cloud Messaging 
	return $conn;
	
?>
