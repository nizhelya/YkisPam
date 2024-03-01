<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['raion_id']) && !empty($_POST['raion_id']) && isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $block_id = $_POST['raion_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultStreets = $dbOperationsObject->getStreetsFromBlock($block_id);
        $streets = $generalFunctionsObject->getStreetsFromBlock($resultStreets);
        $response["success"] = 1;
        $response["message"] = "Успешно!";
        $response["address"] = $streets;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
