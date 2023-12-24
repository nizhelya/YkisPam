<?php
$response = array();

include_once "GeneralFunctions.php";
$dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultBlocks = $dbOperationsObject->getBlocks();
        $blocks = $generalFunctionsObject->getBlocks($resultBlocks);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["address"] = $blocks;
        echo json_encode($response);
?>
