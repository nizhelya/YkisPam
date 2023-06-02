<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['user_id'])  ) {
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultBlocks = $dbOperationsObject->getBlocks();
        $blocks = $generalFunctionsObject->getBlocks($resultBlocks);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["address"] = $blocks;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}

?>
