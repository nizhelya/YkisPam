<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['kod']) && !empty($_POST['kod']) &&
    isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $address_id = $_POST['address_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $kod = $_POST['kod'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultCheck = $dbOperationsObject->checkAddFlat($address_id , $kod);
        $check = $generalFunctionsObject->checkAddFlat($resultCheck);
        $response["success"] = $check[0]["success"];
        $response["message"] = $check[0]["message"];
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
