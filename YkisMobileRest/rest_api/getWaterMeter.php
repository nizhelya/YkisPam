<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['uid']) && !empty($_POST['uid']))   {
    $address_id = $_POST['address_id'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultWater= $dbOperationsObject->getWaterMeter($address_id);
        $water_meters = $generalFunctionsObject->getWaterMeter($resultWater);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["water_meters"] = $water_meters;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["water_meters"] = array();
    echo json_encode($response);
}

?>
