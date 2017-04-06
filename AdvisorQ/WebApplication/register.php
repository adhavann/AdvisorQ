<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: Registering user into DB for web application 

include 'connectionWebsite.php';

$error=''; // Variable To Store Error Message

if (isset($_POST['submit'])) {
if (empty($_POST["username"]) || empty($_POST['password'])) {
$error = "Username or Password is invalid";
echo "Error";
}
else
{
// Define $username and $password

$username=$_POST['username'];
$password=$_POST['password'];

$password = mysql_real_escape_string($password);

$setQuery= mysql_query("INSERT INTO ADVISOR_AUTHENTICATION (_USERNAME,_PASSWORD) VALUES ('$username','$password')", $conn);

if($setQuery){
	header("location: index.html"); // Redirecting To Other Page
}
else{
    die('Could not enter data: ' . mysql_error());
	echo "Failure";
}

}
}
mysql_close($conn); // Closing Connection
?>