   <?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['house_id']) && !empty($_POST['house_id']) &&
    isset($_POST['service']) &&
    isset($_POST['qty']) &&
    isset($_POST['total']) &&
    isset($_POST['user_id']) && !empty($_POST['user_id']) &&
    isset($_POST['token']) && !empty($_POST['token']))   {
    $address_id = $_POST['address_id'];
    $service = $_POST['service'];
    $qty = $_POST['qty'];
    $total = $_POST['total'];
    $house_id = $_POST['house_id'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperations();
    $generalFunctionsObject = new GeneralFunctionsClass();
        $resultServices = $dbOperationsObject->getFlatServices($address_id , $house_id , $service ,$total , $qty);
        $services = $generalFunctionsObject->getFlatServices($resultServices);
        $response["success"] = 1;
        $response["message"] = "Success!";
        $response["services"] = $services;
        echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["services"] = array();
    echo json_encode($response);
}
 ?>
