<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['user_id']) ) {
    $user_id = $_POST['user_id'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultAppartments = $dbOperationsObject->getAppartmentsByUser($user_id);
        $appartments = $generalFunctionsObject->getAppartmentsByUser($resultAppartments);
        $response["success"] = 1;
            $response["message"] = "Success!";
        $response["appartments"] = $appartments;
        echo json_encode($response);


} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
