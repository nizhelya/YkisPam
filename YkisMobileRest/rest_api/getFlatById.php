<?php

include_once "GeneralFunctions.php";
$response = array();


if (!empty($_POST['address_id']) && !empty($_POST['uid']))
 {
    $address_id = $_POST['address_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultApartments = $dbOperationsObject->getFlatById($address_id);
        $apartments = $generalFunctionsObject->getFlatById($resultApartments);

        $response["success"] = 1;
        $response["message"] = "Успешно";
        $response["apartment"] = $apartments;


 } else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}
echo json_encode($response);

