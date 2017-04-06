<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: returning data frm DB to Android App for Real-time Queue, registering through App and adding user to Queue
if($_POST){
include 'WebApplication/connectionWebsite.php';      //Database connection file 
$request = $_POST;
$action=$_POST['operation'];
if(isset($action)){
	if($action=="Register")		                    // block for the registration part		  
	{
		$name=$_POST['name'];
		$uta_id=$_POST['uta_id'];
		$mobile_number=$_POST['mobile_number'];
		$reason=$_POST['reason'];
		$advisor_id=$_POST['advisor_id'];
		$gcm_id=$_POST['token'];
		$token_number=0;
		$status="Registered";                      //Registered, Queued,missed, Completed & InProgress ,offline
		$registerUserQuery="insert into STUDENT_DETAIL(NAME,UTA_ID,MOBILE_NUMBER,GCM_REGISTRATION_ID) values('$name','$uta_id','$mobile_number','$gcm_id')";
		$registerUserExe=mysql_query($registerUserQuery);	
		if($registerUserExe ){
			//if registered succesfully
			$registerScheduleListQuery="insert into SCHEDULE_LIST(UTA_ID,ADVISOR_ID,REASON,STATUS,TOKEN_NUMBER) values('$uta_id','$advisor_id','$reason','$status','$token_number')";
			$registerScheduleListExe=mysql_query($registerScheduleListQuery);
			if($registerScheduleListExe){
				$response=Array();
				$response['status']=1;
				$response['message']="Registered succesfully";
				echo($response['message']);
			}
			else{
				$response=Array();
				$response['status']=0;
				$response['message']="Registration failed! Please try again later";
				echo($response['message']);
			}	
		}
		else{                                 //If registration fails: SQL error 
			$checkAlreadyRegisteredQuery="select UTA_ID from SCHEDULE_LIST where UTA_ID='$uta_id' and (STATUS like 'Registered' or STATUS like 'Queued')";
			$checkAlreadyRegisteredExe=mysql_query($checkAlreadyRegisteredQuery);
			if(mysql_num_rows($checkAlreadyRegisteredExe)){
				// If Already Registered and status is either registered or Queued
				$response=Array();
				$response['status']=0;
				$response['message']="You have Already Registered";
				echo($response['message']);
			}
			else{
				$checkRegisteredStatusQuery="select UTA_ID from SCHEDULE_LIST where UTA_ID='$uta_id'";
				$checkRegisteredStatusExe=mysql_query($checkRegisteredStatusQuery);
				if(mysql_num_rows($checkRegisteredStatusExe)){
					//Updating status of the person if his status is not registered or queued
					$updateRegisteredStatusQuery="update SCHEDULE_LIST set STATUS='Registered' where UTA_ID='$uta_id'";
					$updateRegisteredStatusExe=mysql_query($updateRegisteredStatusQuery);
					if($updateRegisteredStatusExe){
						$response=Array();
						$response['status']=0;
						$response['message']="Successfully Registration Again ";
						echo($response['message']);
					}

				}
				else{       // Error occurs when registration fails :due to unknown error
					$response=Array();
					$response['status']=0;
					$response['message']="Registration failed! Please try again later";
					echo($response['message']);
				}

			}
				
			
		}
	}
	if($action=="AddMeToQueue"){              //Block: when the user clicks to add them into Queue
		$uta_id=$_POST['uta_id'];
		$checkQueueingIDQuery="select SCHEDULE_ID from SCHEDULE_LIST where UTA_ID='$uta_id'";
		$addMeToQueueQuery="update SCHEDULE_LIST set STATUS='Queued' where UTA_ID='$uta_id'";
		$result=mysql_query($checkQueueingIDQuery);
		if(mysql_num_rows($result)){
			$addMeToQueueExe=mysql_query($addMeToQueueQuery);
			//result handling to update each time or just a single time
			$response=Array();
			$response['status']=1;
			$response['message']="Succesfully added to the Queue";
			echo($response['message']);
		}
		else{
			//handlimg for :register first to try condition
			$response=Array();
			$response['status']=0;
			$response['message']="Please Register and try again";
			echo($response['message']);
		}
	}
	if($action=="RealTimeQueue"){     // Block: to list the people waiting in the queue 
		$realTimeQueueQuery="select SCHEDULE_ID,UTA_ID,STATUS from SCHEDULE_LIST where STATUS like 'Queued' or STATUS like 'Registered'";
		$realTimeQueueExe=mysql_query($realTimeQueueQuery);
		if(!$realTimeQueueExe){
			//Query returns FALSE : unknown error
			$response=Array();
			$response['status']=0;
			$response['message']="Oops sorry!! Please try again";
			echo($response['message']);
		}
		else{
			if(mysql_num_rows($realTimeQueueExe)!=0){
				//send result to Android App 
				$response=Array();
				while($row = mysql_fetch_array($realTimeQueueExe, MYSQL_NUM)) {
                        $temp=Array();
						$temp['uta_id']=$row[1];
						$temp['status']=$row[2];
						array_push($response,$temp);
                    }
				echo (json_encode($response));
			}
			else{
				//If there is no one waiting in the Queue
				$response=Array();
				$response['status']=0;
				$response['message']="Queue is Empty";
				echo($response['message']);
			}
		}
	}
}

mysql_close();

}
?>