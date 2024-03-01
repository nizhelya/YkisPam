<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['teplomer_id']) && !empty($_POST['teplomer_id']) &&
    isset($_POST['uid']) && !empty($_POST['uid']))   {
    $teplomer_id = $_POST['teplomer_id'];
    $uid = $_POST['uid'];
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
