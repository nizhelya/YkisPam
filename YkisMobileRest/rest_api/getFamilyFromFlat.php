<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['uid']) && !empty($_POST['uid']))   {
    $address_id = $_POST['address_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultFamily = $dbOperationsObject->getFamilyFromFlat($address_id);
        $family = $generalFunctionsObject->getFamilyFromFlat($resultFamily);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["family"] = $family;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
     $response["family"] = array();
    echo json_encode($response);
}

?>
