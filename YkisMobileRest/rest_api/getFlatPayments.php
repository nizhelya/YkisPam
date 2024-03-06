   <?php
$response = array();

include_once "GeneralFunctions.php";

if (!empty($_POST['address_id']) && !empty($_POST['uid']))   {
    $address_id = $_POST['address_id'];
    $uid = $_POST['uid'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();
        $resultPayments = $dbOperationsObject->getFlatPayments($address_id );
        $payments = $generalFunctionsObject->getFlatPayments($resultPayments);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["payments"] = $payments;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["payments"] = array();
    echo json_encode($response);
}
 ?>
