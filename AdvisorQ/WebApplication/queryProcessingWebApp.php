<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: Updating users status in the DB and Initialising Notification for the users

include 'connectionWebsite.php';      //Database connection file 
include 'gcm.php';                    //PUSH Ntification File
$request = $_POST['operation'];
$action=$_POST['operation'];
if(isset($action)){                  
        if($action=="NotArrived"){          //Block: when the users status is clicked as Not Arrived
            $uta_id=$_GET['uta_id'];
            $getStatusQuery="select STATUS from SCHEDULE_LIST where UTA_ID='$uta_id'";
            $getStatusExe=mysql_query($getStatusQuery);
            if(mysql_num_rows($getStatusExe)){
                    while($row = mysql_fetch_array($getStatusExe, MYSQL_NUM)) {
                        $status=$row[0];
                    }
            }
            else{                          // If any error occurs : Unknown Error
                $response=Array();
			    $response['status']=0;
			    $response['message']="Oops! Please try again!";
			    return (json_encode($response));
            }
            if($status=="Registered"){    // Block: Updating status on condition Current Status = Registered 
              $updateOfflineQuery="update SCHEDULE_LIST set STATUS='Offline' where UTA_ID='$uta_id'";  
              $updateOfflineExe=mysql_query($updateOfflineQuery);
              if(!$updateOfflineExe){
                //If update Fails
                $response=Array();
			    $response['status']=0;
			    $response['message']="You have missed the Queue";
              }
              // sending Mobile Notification for Missing user's turn 
              $missedQueueMessage="Sorry! You have missed the Queue";
              $gcm=new GCM();
              $gcm_id=$gcm->getGCM($uta_id);
              if(isset($gcm_id)){
                $gcm->send($gcm_id,$missedQueueMessage);
                }
                return (json_encode($response));
            }
            elseif($status=="Queued"){      //Updating the user status on condition current status is Queued
                $updateMissedQuery="update SCHEDULE_LIST set STATUS='Missed' where UTA_ID='$uta_id'";
                $updateMissedExe=mysql_query($updateMissedQuery);
                if(!$updateMissedExe){
                    //If Update Fails
                    $response=Array();
			        $response['status']=0;
			        $response['message']="Error! Missed Status Update";
                    return (json_encode($response));
                }
                // Updating the Missed Queue Table if the updated status is missed
                $getScheduleIDQuery="select SCHEDULE_ID from SCHEDULE_LIST where UTA_ID='$uta_id'";
                $getScheduleIDExe=mysql_query($getScheduleIDQuery);
                if(mysql_num_rows($getScheduleIDExe)){
                    while($row = mysql_fetch_array($getScheduleIDExe, MYSQL_NUM)) {
                        $schedule_id=$row[0];
                    }
                    $getMissedIDQuery="select max(MISSED_ID) from MISSED_QUEUE where UTA_ID='$uta_id'";
                    $getMissedIDExe=mysql_query($getMissedIDQuery);
                    if(mysql_num_rows($getMissedIDExe)){
                        while($row = mysql_fetch_array($getMissedIDExe, MYSQL_NUM)) {
                            $missed_id=$row[0];
                        }
                        $missed_id=$missed_id+1;
                        $updateMissedTable="insert into MISSED_QUEUE(UTA_ID,SCHEDULE_ID,MISSED_ID) values('$uta_id','$schedule_id','$missed_id')";
                        $updateMissedTableExe=mysql_query($updateMissedTable);
                        if($updateMissedTableExe){
                            //If update fails
                            $response=Array();
			                $response['status']=0;
			                $response['message']="Error! Missed Table Update";
			                return (json_encode($response));
                        }
                    }
                }
                //Sending Push Notification to the user about Missing Queue
                $missedQueueMessage=array('message'=>"Sorry! You have missed the Queue");
                $gcm=new GCM();
                $gcm_id=$gcm->getGCM($uta_id);
                if(isset($gcm_id)){
                  $gcm->send($gcm_id,$missedQueueMessage);
                } 
            }
         }
         if($action=="Done"){         //Block: If the Advisor clicks on status Completed 
             $update_status='Completed';
             $uta_id=$_GET['uta_id'];
             $updateCompletedQuery="update SCHEDULE_LIST set STATUS='$update_status' where UTA_ID='$uta_id'";
             $updateCompletedExe=mysql_query($updateCompletedQuery);
             if(!$updateCompletedExe){
                //if update fails
                $response=Array();
			    $response['status']=0;
			    $response['message']="Error! Finished Session Update";
			    return (json_encode($response));
             }
             else{
                 if($update_status=="Completed"){
                     $getScheduleAndReasonQuery="select ADVISOR_ID,REASON from SCHEDULE_LIST where UTA_ID='$uta_id'";
                     $getScheduleAndReasonExe=mysql_query($getScheduleAndReasonQuery);
                     if(mysql_num_rows($getScheduleAndReasonExe)){
                        while($row = mysql_fetch_array($getScheduleAndReasonExe, MYSQL_NUM)) {
                            $advisor_id=$row[0];
                            $reason=$row[1];
                        }
                     }
                     $completedTableQuery="insert into COMPLETED(UTA_ID,ADVISOR_ID,REASON) values('$uta_id','$advisor_id','$reason')";
                     $completedTableExe=mysql_query($completedTableQuery);
                     if(!$completedTableExe){
                        $response=Array();
			            $response['status']=0;
			            $response['message']="Error! Completed Table Update";
			            return (json_encode($response));    
                     }
                     else{
                        $response=Array();
			            $response['status']=1;
			            $response['message']="Success";
			            return (json_encode($response));   
                     }

                 }
                 //Sending PUSH Notification to the User about Completed Status
                 $completedQueueMessage=array('message'=>"You have finished the Advising session! All the best");
                 $gcm=new GCM();
                 $gcm_id=$gcm->getGCM($uta_id);
                 if(isset($gcm_id)){
                     $gcm->send($gcm_id,$completedQueueMessage);
                }
                $response=Array();
			    $response['status']=1;
			    $response['message']="Success";
			    return (json_encode($response)); 
             }

         }
         if($action="GetIn"){        // Sending notification to the User to get into Advisor's room
             $GetInMessage=array('message'=>"Please get into Advisor room ");
             $uta_id=$_GET['uta_id'];
             $getGCMIdQuery="select GCM_REGISTRATION_ID from STUDENT_DETAIL where UTA_ID='$uta_id'";
             $getGCMIdExe=mysql_query($getGCMIdQuery) or die(mysql_error);
             if(mysql_num_rows($getGCMIdExe)>0){
                 while($row = mysql_fetch_array($getGCMIdExe, MYSQL_NUM)) {
                            $gcm_id=$row[0];
                        }
                 $gcm=new GCM();
                 $gcm->send($gcm_id,$GetInMessage);
             }
             $response=Array();
             $response['status']=1;
             $response['message']="Success";
             return (json_encode($response));      
         }

}
mysql_close();
?>