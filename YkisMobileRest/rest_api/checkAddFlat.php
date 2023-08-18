<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['kod']) && !empty($_POST['kod']) &&    
    isset($_POST['uid']) && !empty($_POST['uid']) &&    
    isset($_POST['email']) && !empty($_POST['email']))   {
    $uid = $_POST['uid'];
    $kod = $_POST['kod'];
    $email = $_POST['email'];

    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultCheck = $dbOperationsObject->checkAddFlat( $kod,$uid,$email);
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
