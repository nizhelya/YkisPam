<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['teplomer_id']) &&
    !empty($_POST['teplomer_id'])  &&
    isset($_POST['current_value']) &&
    !empty($_POST['current_value']) &&
    isset($_POST['new_value']) &&
    !empty($_POST['new_value']) &&
    isset($_POST['uid']) &&
    !empty($_POST['uid']))   {
    $uid  = $_POST['uid'];
    $teplomer_id = $_POST['teplomer_id'];
    $current_value = $_POST['current_value'];
    $new_value = $_POST['new_value'];
    $token = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

    $result = $dbOperationsObject->addCurrentHeatReading($uid,$teplomer_id , $current_value , $new_value );
    $results = $generalFunctionsObject->addCurrentReading($result);
    $response["success"] = $results[0]["success"];
    $response["message"] = $results[0]["message"];
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>