
<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['pok_id']) &&
    !empty($_POST['pok_id'])  &&
    isset($_POST['user_id']) &&
    !empty($_POST['user_id'])&&
    isset($_POST['token']) &&
    !empty($_POST['token']))   {
    $pok_id = $_POST['pok_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $date = Date("Ymd");
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

    $result = $dbOperationsObject->deleteCurrentHeatReading($pok_id);
    $results = $generalFunctionsObject->deleteCurrentReading($result);
    if($results[0]["success"]==1){
        $response["message"] = "Readings deleted successful";
    }else $response["message"] = "Failed to delete readings" ;
    $response["success"] = $results[0]["success"];
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
