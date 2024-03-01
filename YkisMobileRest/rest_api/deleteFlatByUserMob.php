 <?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['user_id']) &&
    !empty($_POST['user_id']) &&
    isset($_POST['address_id']) &&
    !empty($_POST['address_id']) &&
    isset($_POST['token']) &&
    !empty($_POST['token']))   {
    $address_id = $_POST['address_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();

        $result = $dbOperationsObject->deleteFlatByUser($address_id , $user_id);
        if(mysqli_affected_rows($result)>0){
        $response["success"] = 1;
        $response["message"] =  "Appartment deleted";
        }else{
            $response["success"] = 0;
            $response["message"] = "Failed to delete apartment";
        }
        echo json_encode($response);

} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

 ?>
