
<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['pok_id']) &&
    !empty($_POST['pok_id'])  &&
       isset($_POST['uid']) &&
    !empty($_POST['uid']))   {
    $pok_id = $_POST['pok_id'];
    $uid = $_POST['uid'];
    $date = Date("Ymd");
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

    $result = $dbOperationsObject->deleteCurrentWaterReading($pok_id);
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
