
<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['teplomer_id']) &&
    !empty($_POST['teplomer_id'])  &&
    isset($_POST['current_value']) &&
    !empty($_POST['current_value']) &&
    isset($_POST['new_value']) &&
    !empty($_POST['new_value']) &&
    isset($_POST['user_id']) &&
    !empty($_POST['user_id'])&&
    isset($_POST['token']) &&
    !empty($_POST['token']))   {
    $teplomer_id = $_POST['teplomer_id'];
    $current_value = $_POST['current_value'];
    $new_value = $_POST['new_value'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $date = Date("Ymd");
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

    $result = $dbOperationsObject->addCurrentHeatReading($teplomer_id , $current_value , $new_value ,$date);
    $results = $generalFunctionsObject->addCurrentReading($result);
    $response["success"] = $results[0]["success"];
    $response["message"] = $results[0]["message"];
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
