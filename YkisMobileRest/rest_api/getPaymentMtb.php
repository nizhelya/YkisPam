<?php
$response = array();

include_once "GeneralFunctions.php";

if (isset($_POST['address_id']) && !empty($_POST['address_id']) &&
    isset($_POST['oplata1']) && !empty($_POST['oplata1']) &&
    isset($_POST['oplata4']) && !empty($_POST['oplata4']) &&
    isset($_POST['oplata5']) && !empty($_POST['oplata5']) &&
    isset($_POST['oplata6']) && !empty($_POST['oplata6']) &&
    isset($_POST['oplata10']) && !empty($_POST['oplata10']) &&
    isset($_POST['new_oplata']) && !empty($_POST['new_oplata']) &&
    isset($_POST['user_id']) && !empty($_POST['user_id'])
)   {
    $address_id = $_POST['address_id'];
    $oplata1 = $_POST['oplata1'];
    $oplata4 = $_POST['oplata4'];
    $oplata5 = $_POST['oplata5'];
    $oplata6 = $_POST['oplata6'];
    $oplata10 = $_POST['oplata10'];
    $new_oplata = $_POST['new_oplata'];
    $user_id = $_POST['user_id'];
    $token = $_POST['token'];
    $dbOperationsObject = new DBOperationsMtb();
    $generalFunctionsObject = new GeneralFunctionsClass();

        $resultPayment= $dbOperationsObject->newOplata($address_id , $oplata1 , $oplata4, $oplata5 , $oplata6 , $oplata10 , $new_oplata, $user_id);
        $payment = $generalFunctionsObject->getMtbPayment($resultPayment);
        $response["success"] = 1;
        $response["message"] = "Успех";
        $response["payment"] = $payment;
//        echo json_encode($response);
    $json_data= json_encode($payment);


    $curl = curl_init();

    curl_setopt_array($curl, array(
        //CURLOPT_URL => 'https://stage-papi.xpay.com.ua/cipher',
        CURLOPT_URL => 'https://papi.xpaydirect.com/cipher',
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_MAXREDIRS => 10,

        CURLOPT_TIMEOUT => 0,
        CURLOPT_SSL_VERIFYPEER => 0,
        CURLOPT_SSL_VERIFYHOST => 0,
        //CURLOPT_SSLVERSION => 3,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/json'
        ),
        CURLOPT_CUSTOMREQUEST => "POST",
        CURLOPT_POSTFIELDS => $json_data
    ));
//cURL Error: 35<br>cURL ErrorNo: Unknown SSL protocol error in connection to stage-papi.xpay.com.ua:443 {"type":"rpc","tid":22,"action":"QueryPaymentMarfin","method":"newOplata","result":null}

    $curl_result = curl_exec( $curl );
    curl_close( $curl );

    $curl_result_code = $curl_result;

    $curl_url = curl_init();
    curl_setopt_array($curl_url, array(
        //CURLOPT_URL => 'https://stage-papi.xpay.com.ua:488/xpay',

        CURLOPT_URL => 'https://papi.xpaydirect.com/xpay',
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_SSL_VERIFYPEER => 0,
        CURLOPT_SSL_VERIFYHOST => 0,
        //CURLOPT_SSLVERSION => 3,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTPHEADER => array(
            'Content-Type: application/json'
        ),
        CURLOPT_CUSTOMREQUEST => "POST",
        CURLOPT_POSTFIELDS => $curl_result_code
    ));
//cURL Error: 35<br>cURL ErrorNo: Unknown SSL protocol error in connection to stage-papi.xpay.com.ua:443 {"type":"rpc","tid":22,"action":"QueryPaymentMarfin","method":"newOplata","result":null}

    $curl_result_url = curl_exec( $curl_url );
    print_r($curl_result_url);
    curl_close( $curl_url );
    $paym = json_decode($curl_result_url,true);

if(isset($paym['Code']) && ($paym['Code'])) {
    $code =  $paym['Code'];
} else {
    $code = 0;
}

if(isset($paym['Message']) && ($paym['Message'])) {
    $message =  $paym['Message'];
} else {
    $message = 0;
}

if(isset($paym['Data']['OperationID']) && ($paym['Data']['OperationID'])) {
    $ndoc =  $paym['Data']['OperationID'];
} else {
    $ndoc = "";
}

if(isset($paym['Data']['OperationStatus']) && ($paym['Data']['OperationStatus'])) {
    $status =  $paym['Data']['OperationStatus'];
} else {
    $status = "";
}

if(isset($paym['Data']['URI']) && ($paym['Data']['URI'])) {
    $uri =  $paym['Data']['URI'];
} else {
    $uri = "";
}

if(isset($paym['Data']['uuid']) && ($paym['Data']['uuid'])) {
    $uuid =  $paym['Data']['uuid'];
} else {
    $uuid = "";
}
echo $code;
if ($code == "200" && $status == "10" ) {
    $this->results['success'] = 1;
    $this->results['url'] = $uri;
    $resultPayment = $dbOperationsObject->checkPaymentMtb($ndoc, $status, $uri, $uuid);
}else{
    $response["success"] = 0;
    $response["message"] = $uri;
    $response["payment"] = array();
    echo json_encode($response);
}
    $response["success"] = 0;
    $response["message"] = $uri;
    $response["payment"] = array();
    echo json_encode($response);;
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    $response["payment"] = array();
    echo json_encode($response);
}

?>
