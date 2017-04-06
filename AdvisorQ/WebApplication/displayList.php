<?php
// NAME : Adhavann Ramalingam
// PROJECT: AdvisorQ  - CSE6349 Advanced Computer Networks
// FUNCTION: Displaying the Queue details( Waiting Queue,Missed Queue and Completed Queue) 

// Session Implementation for the web application 
session_start();
if(!isset($_SESSION['username'])){
    echo "you are not logged in as </br>", $_SESSION['username'];
    header("location: index.html"); // Redirecting To Other Page   
}

?>
<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">

    <title>AdvisorQ</title>

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.0/css/font-awesome.min.css">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Material Design Bootstrap -->
    <link href="css/mdb.min.css" rel="stylesheet">

    <!-- Your custom styles (optional) -->
    <link href="css/style.css" rel="stylesheet">


</head>

<script type="text/javascript">
// POST method to send the PUSH Notification to the user for get in
function myAjax(uta_idVal) {
      $.ajax({
           type: "POST",
           url: 'queryProcessingWebApp.php?uta_id='+uta_idVal,
           //   contentType: "application/json",
           data:{operation:'GetIn'},
           success:function(html) {
           }

      });
}

//POST method to send the PUSH Notification to the user for missing queue
function sendMissed(uta_idVal){
    $.ajax({
           type: "POST",
           url: 'queryProcessingWebApp.php?uta_id='+uta_idVal,
           //   contentType: "application/json",
           data:{operation:'NotArrived'},
           success:function(html) {
               location.reload(true);
           }

      });
}

//POST method to send the PUSH Notification to the user for Finished Advising
function sendCompleted(uta_idVal){
    $.ajax({
           type: "POST",
           url: 'queryProcessingWebApp.php?uta_id='+uta_idVal,
           //   contentType: "application/json",
           data:{operation:'Done'},
           success:function(html) {
               location.reload(true);
           }

      });
}


</script>

<body class="container">
<br>
<div class="card">
<div class="card-block">
<h2 class="card-title">AdvisorQ
<a class="btn btn-primary pull-right" href="logout.php">Logout</a></h2>
</div>
</div>
<br>
<br>
<br>
<section id= "list">



<!-- Nav tabs -->
<ul  class="nav nav-tabs tabs-4 blue" role="tablist">
    <li class="nav-item">
        <a class="nav-link active" data-toggle="tab" href="#panel1"  role="tab">Waiting Queue</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#panel2"  role="tab">Missed Queue</a>
    </li>
    <li class="nav-item">
        <a class="nav-link" data-toggle="tab" href="#panel3"  role="tab">Completed Queue</a>
    </li>
</ul>

<!-- Tab panels -->
<div class="tab-content card">


    <!--Panel 1 :  Waiting Queue-->  
    <div class="tab-pane fade in active" id="panel1" role="tabpanel">
        <br>

        <div class="card">
        <div class="card-block">
            <h4 class="card-title"></h4>
<!--Card-->
 
        <?php 
            include 'webApp.php';
            $Object=new webApp();
            $response=$Object->getWaitingPeople();
            $data=json_decode($response);
            if(isset($data3->message)){
                echo $data3->message;
            }
            else{
            foreach($data as $obj){
                //echo $obj->uta_id;
            
        ?>
            <div class="card">
                <!--Card content-->
                <div class="card-block">
                    <!--Title-->
                    <h4 class="card-title"> <?= $obj->uta_id ?>                    <?= $obj->name ?></h4>
                    
                    <p class="card-text"> Reason : <?= $obj->reason ?>  <a  onclick="myAjax(<?= $obj->uta_id ?>)" data-toggle="modal" data-target="#modal1<?= $obj->uta_id ?>" class="btn btn-primary pull-right ">GET IN</a></p>
                </div>
                <!--/.Card content-->
            </div>
             <!-- Modal Part-->
            <div id="modal1<?= $obj->uta_id ?>" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="close"></button>
                        <h4 class="modal-title" id="mymodalTitle">Student Details</h4>
                        </div>

                        <div class="modal-body">
                            <h4><?= $obj->uta_id ?></h4> <h4><?= $obj->name ?></h4>
                        </div>

                        <div class="modal-footer">
                        <button type="button" class="btn btn-secondary waves-effect waves-light" data-dismiss="modal" onclick="sendMissed(<?= $obj->uta_id ?>)" >Not Arrived</button>
                        <button type="button" class="btn btn-primary waves-effect waves-light" data-dismiss="modal" onclick="sendCompleted(<?= $obj->uta_id ?>)" >Completed</button>
                        </div>
                    </div>
                </div>
            </div>
             <!-- Modal Part-->

            <!--Card-->
            <?php }
            }
             ?>
        </div>
        </div>



    </div>
    <!--/.Panel 1-->

    <!--Panel 2  : Missed Queue -->
    <div class="tab-pane fade" id="panel2" role="tabpanel">
        <br>
        <div class="card">
        <div class="card-block">
            <h4 class="card-title"></h4>
<!--Card-->
 
        <?php 
            $Object=new webApp();
            $response2=$Object->getInProgress();
            $data2=json_decode($response2);
            if(isset($data3->message)){
                echo $data3->message;
            }
            else{
            foreach($data2 as $obj2){
                //echo $obj->uta_id;     
        ?>
            <div class="card">
                <!--Card content-->
                <div class="card-block">
                    <!--Title-->
                    <h4 class="card-title"> <?= $obj2->uta_id; ?>                    <?= $obj2->name; ?></h4>
                    <!--Text-->
                    <p class="card-text"> Reason : <?= $obj2->reason ?> <a  onclick="myAjax(<?= $obj2->uta_id ?>)" data-toggle="modal" data-target="#modal2<?= $obj2->uta_id ?>" class="btn btn-primary pull-right ">GET IN</a></p>
                </div>
                <!--/.Card content-->
            </div>
            <!-- Modal Part-->
            <div id="modal2<?= $obj2->uta_id ?>" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-lg">
                                <div class="modal-content">
                                    <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="close"></button>
                                    <h4 class="modal-title" id="mymodalTitle">Student Details</h4>
                                    </div>

                                    <div class="modal-body">
                                        <h4><?= $obj2->uta_id ?></h4> <h4><?= $obj2->name ?></h4>
                                    </div>

                                    <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-secondary waves-effect waves-light" onclick="sendMissed(<?= $obj2->uta_id ?>)">Not Arrived</button>
                                    <button type="button" data-dismiss="modal" class="btn btn-primary waves-effect waves-light" onclick="sendCompleted(<?= $obj2->uta_id ?>)" >Completed</button>
                                    </div>
                                </div>
                            </div>
                        </div>
            <!--Modal Part-->


            <!--Card-->
            <?php }
            }
             ?>
        </div>
        </div>
        
    </div>
    

    <!-- Panel 3: Completed Queue -->
     <div class="tab-pane fade" id="panel3" role="tabpanel">
        <br>
        <div class="card">
        <div class="card-block">
            <h4 class="card-title"></h4>
<!--Card-->
 
        <?php 
            $response3=$Object->getCompleted();
            $data3=json_decode($response3);
            if(($data3->message)){
                echo $data3->message;
            }
            else{
            foreach($data3 as $obj3){
                //echo $obj->uta_id;
                
            
        ?>
            <div class="card">
                <!--Card content-->
                <div class="card-block">
                    <!--Title-->
                    <h4 class="card-title"> <?= $obj3->uta_id ?>                    <?= $obj3->name ?></h4>
                    <!--Text-->
                    <p class="card-text"> Reason : <?= $obj3->reason ?></p>
                </div>
                <!--/.Card content-->
            </div>


            <!--Card-->
            <?php }
            }
             ?>
        </div>
        </div>
        
        

    </div>
    <!--/.Panel 3-->

</div>

    <!-- Start your project here-->

</section>



<!--/.Card-->
   



    <!-- /Start your project here-->


    
    <!-- SCRIPTS -->

    <!-- JQuery -->
    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

    <!-- Bootstrap tooltips -->
    <script type="text/javascript" src="js/tether.min.js"></script>

    <!-- Bootstrap core JavaScript -->
    <script type="text/javascript" src="js/bootstrap.min.js"></script>

    <!-- MDB core JavaScript -->
    <script type="text/javascript" src="js/mdb.min.js"></script>


</body>

</html>