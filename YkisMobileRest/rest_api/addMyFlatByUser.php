<?php
$response = array();
include_once "GeneralFunctions.php";

if (!empty($_POST['kod']) && !empty($_POST['uid']) && !empty($_POST['email']))   {
    $uid = $_POST['uid'];
    $kod = $_POST['kod'];
    $email = $_POST['email'];

    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $result = $dbOperationsObject->addMyFlatByUser($kod,$uid,$email);
        $results = $generalFunctionsObject->addMyFlatByUser($result);
        $response["success"] = $results[0]["success"];
        $response["message"] = $results[0]["message"];
        $response["addressId"] = $results[0]["addressId"];

} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["address"] = array();
}
echo json_encode($response);


