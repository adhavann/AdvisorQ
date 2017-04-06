<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: returning GCM ID, formatting data for notification and POST data to GCM for PUSH Notification 
class GCM{

    function __construct() {
         //Default Constructor
    }
    function send($to,$message){     //Function to format and send data to POST method
        $fields = array(
            'to' => $to,
            'data' => $message,
        );

        return $this->sendPushNotification($fields);
    }
     private function sendPushNotification($fields) {   //Post method to send data for notification to the Google Cloud Messaging service 
 
        // include config
        include 'connectionWebsite.php'; 
        // Set POST variables
        $url = 'https://gcm-http.googleapis.com/gcm/send';

 
        $headers = array(
            'Authorization: key=' . GOOGLE_API_KEY,
            'Content-Type: application/json'
        );
        // Open connection
        $ch = curl_init();
        // Set the url, number of POST vars, POST data
        curl_setopt($ch, CURLOPT_URL, $url);
 
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
 
        // Disabling SSL Certificate support temporarly
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
 
        if($fields){
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
        }
        // Execute post
        $result = curl_exec($ch);
        if ($result === FALSE) {
            die('Curl failed: ' . curl_error($ch));
        }
 
        // Close connection
        curl_close($ch);
        return $result;
    }
    function getGCM($uta_id){        //Function to return the GCM ID for the specific UTA_ID
        include 'connectionWebsite.php';
        $getGCMIdQuery="select GCM_REGISTRATION_ID from STUDENT_DETAIL where UTA_ID='$uta_id'";
        $getGCMIdExe=mysql_query($getGCMIdQuery);
        $gcm_id=null;
        if(mysql_num_rows($getGCMIdExe)>0){
            while($row = mysql_fetch_array($getGCMIdExe, MYSQL_NUM)) {
                $gcm_id=$row[0];
            }
            return $gcm_id;
        }
        else{
            return $gcm_id;
        }      
    }

}



?>