 <?php
$response = array();

include_once "GeneralFunctions.php";

if (!empty($_POST['address_id']) &&  !empty($_POST['uid']))   {
    $address_id = $_POST['address_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();

        $result = $dbOperationsObject->deleteFlatByUser($address_id , $uid);
        if(mysqli_affected_rows($result)>0){
        $response["success"] = 1;
        $response["message"] =  "Appartment deleted";
        
        }else{
            $response["success"] = 0;
           $response["message"] = "Failed to delete apartment";
           // $response["message"] = "'.$uid.'";

           
        }
        echo json_encode($response);

} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

 ?>
