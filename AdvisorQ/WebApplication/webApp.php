<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: Returning data from the DB about waiting List, Missed list and Completed list 

class webApp
{
    function __construct() {
         
    }
    function getWaitingPeople(){              //return the list of people waiting in the Queue
        include 'connectionWebsite.php';      //Database connection file
        $getWaitingListQuery="select A.UTA_ID,A.NAME,B.REASON from STUDENT_DETAIL as A, SCHEDULE_LIST as B where A.UTA_ID=B.UTA_ID and (B.STATUS like 'Registered' or B.STATUS like 'Queued')";
        $getWaitingListExe=mysql_query($getWaitingListQuery);
		if(!$getWaitingListExe){
			//Query returns FALSE
			echo $getWaitingListExe;
			$response=Array();
			$response['message']="Oops sorry!! Please refresh";
			echo ($response['message']);
		}
		else{
			if(mysql_num_rows($getWaitingListExe)!=0){
				//send result to Web App
				$response=Array();
				while($row = mysql_fetch_array($getWaitingListExe, MYSQL_NUM)) {
                        $temp=Array();
						$temp['uta_id']=$row[0];
						$temp['name']=$row[1];
						$temp['reason']=$row[2];
						array_push($response,$temp);
                    }
				return (json_encode($response));
			}
			else{
				//If there is no one waiting
				$response=Array();
				$response['message']="Queue is Empty";
				echo ($response['message']);
			}
		} 
		mysql_close();       
}

function getInProgress(){                 //Returns the list of people missed his/her turn 
    include 'connectionWebsite.php';      //Database connection file
    $getInProgressListQuery="select A.UTA_ID,A.NAME,B.REASON from STUDENT_DETAIL as A, SCHEDULE_LIST as B where A.UTA_ID=B.UTA_ID and B.STATUS like 'Missed'";
		$getInProgressListExe=mysql_query($getInProgressListQuery);
		if(!$getInProgressListExe){
			//Query returns FALSE
			$response=Array();
			$response['message']="Oops sorry!! Please refresh";
			echo ($response['message']);
		}
		else{
			if(mysql_num_rows($getInProgressListExe)!=0){
				//send result to Web App
				$response=Array();
				while($row = mysql_fetch_array($getInProgressListExe, MYSQL_NUM)) {
                        $temp=Array();
						$temp['uta_id']=$row[0];
						$temp['name']=$row[1];
						$temp['reason']=$row[2];
						array_push($response,$temp);
                    }
				return (json_encode($response));
			}
			else{
				//If there is no one waiting
				$response=Array();
				$response['message']="Queue is Empty";
				echo ($response['message']);
			}
		}
		mysql_close();
}

function getCompleted(){                  //Returns the list of peole who has completed the advising session
    include 'connectionWebsite.php';      //Database connection file
    $getCompletedListQuery="select A.UTA_ID,A.NAME,B.REASON from STUDENT_DETAIL as A, SCHEDULE_LIST as B where A.UTA_ID=B.UTA_ID and B.STATUS like 'Completed'";
		$getCompletedListExe=mysql_query($getCompletedListQuery);
		if(!$getCompletedListExe){
			//Query returns FALSE
			$response=Array();
			$response['message']="Oops sorry!! Please refresh";
			echo ($response['message']);
		}
		else{
			if(mysql_num_rows($getCompletedListExe)!=0){
				//send result to Web App
				$response=Array();
				while($row = mysql_fetch_array($getCompletedListExe, MYSQL_NUM)) {
                        $temp=Array();
						$temp['uta_id']=$row[0];
						$temp['name']=$row[1];
						$temp['reason']=$row[2];
						array_push($response,$temp);
                    }
				return (json_encode($response));
			}
			else{
				//If there is no one waiting
				$response=Array();
				$response['message']="Queue is Empty";
				echo ($response['message']);
			}
		}
mysql_close();
}
}
?>