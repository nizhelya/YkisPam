<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['uid']) && !empty($_POST['uid']))   {
    $address_id = $_POST['address_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultHeat= $dbOperationsObject->getHeatMeter($address_id);
        $heat_meters = $generalFunctionsObject->getHeatMeter($resultHeat);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["heat_meters"] = $heat_meters;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["heat_meters"] = array();
    echo json_encode($response);
}

?>
