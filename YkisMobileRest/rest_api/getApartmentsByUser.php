<?php

include_once "GeneralFunctions.php";
//var_dump($_POST);
//print(1)
$response = array();

if ( isset($_POST['uid'])  && !empty($_POST['uid'])) {

    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();
//
        $resultApartments = $dbOperationsObject->getApartmentsByUser($uid);
        $apartments = $generalFunctionsObject->getApartmentsByUser($resultApartments);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["apartments"] = $apartments;
        echo json_encode($response);


} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>


