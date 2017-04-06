<?php 
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: Destroying session during the LogOut 

session_start();
if (isset($_SESSION['username'])) {
   session_destroy();
   echo "<br> you are logged out successufuly!";
    header("location: index.html"); // Redirecting To Other Page
} 
  

 ?>