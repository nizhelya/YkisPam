
<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['teplomer_id']) && !empty($_POST['teplomer_id']) &&
    isset($_POST['uid']) && !empty($_POST['uid'])) {
    $teplomer_id = $_POST['teplomer_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

    $resultHeat= $dbOperationsObject->getLastHeatReading($teplomer_id);
    $heat_reading = $generalFunctionsObject->getLastHeatReading($resultHeat);
    $response["success"] = 1;
    $response["message"] = "Successful";
    $response["heat_reading"] = $heat_reading;
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["heat_reading"] = array();
    echo json_encode($response);
}
