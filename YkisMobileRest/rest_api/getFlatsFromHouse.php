<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['house_id']) && !empty($_POST['house_id']) && isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $house_id = $_POST['house_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultFlats = $dbOperationsObject->getFlatsFromHouse($house_id);
        $flats = $generalFunctionsObject->getFlatsFromHouse($resultFlats);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["address"] = $flats;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
