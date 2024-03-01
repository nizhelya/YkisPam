<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['vodomer_id']) && !empty($_POST['vodomer_id'])  &&
    isset($_POST['uid']) && !empty($_POST['uid']))   {
    $vodomer_id = $_POST['vodomer_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultWater= $dbOperationsObject->getWaterReadings($vodomer_id);
        $water_reading = $generalFunctionsObject->getWaterReadings($resultWater);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["water_readings"] = $water_reading;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["water_readings"] = array();
    echo json_encode($response);
}

?>
