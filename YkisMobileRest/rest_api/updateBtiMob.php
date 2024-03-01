<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['phone']) &&
    isset($_POST['email']) &&
    isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $address_id = $_POST['address_id'];
    $phone = $_POST['phone'];
    $email = $_POST['email'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();

        $result = $dbOperationsObject->updateBti($address_id , $phone , $email);
         if(mysqli_affected_rows($result)>0){
        $response["success"] = 1;
        $response["message"] =  "Contacts updated";
        }else{
            $response["success"] = 0;
            $response["message"] = "Failed to update contacts";
        }
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
