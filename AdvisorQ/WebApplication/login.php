<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: LogIn validation php file

include 'connectionWebsite.php';
session_start(); // Starting Session
$error=''; // Variable To Store Error Message
if (isset($_POST['submit'])) {
if (empty($_POST['mailid']) || empty($_POST['password'])) {
$error = "Username or Password is invalid";
}
else
{
// Define $username and $password

$username=$_POST['mailid'];
$password=$_POST['password'];

$password = mysql_real_escape_string($password);

$query = mysql_query("select * from ADVISOR_AUTHENTICATION where _PASSWORD ='$password' AND _USERNAME ='$username'", $conn);
$rows = mysql_num_rows($query);

if ($rows == 1) {
$_SESSION['username']=$username; // Initializing Session
echo $_SESSION['username'];
header("location: displayList.php"); // Redirecting To Other Page
echo "Login successful";
} else {
header("location: index.html");
}

}
}
mysql_close($conn); // Closing Connection
?>