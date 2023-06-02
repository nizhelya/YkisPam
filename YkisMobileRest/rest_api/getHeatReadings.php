<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['teplomer_id']) && !empty($_POST['teplomer_id']) &&
    isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $teplomer_id = $_POST['teplomer_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $result= $dbOperationsObject->getHeatReadings($teplomer_id);
        $heat_reading = $generalFunctionsObject->getHeatReadings($result);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["heat_readings"] = $heat_reading;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["heat_readings"] = array();
    echo json_encode($response);
}

?>
