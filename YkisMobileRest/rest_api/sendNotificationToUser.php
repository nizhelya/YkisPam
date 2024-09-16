<?php

$response = array();
if (isset($_POST['recipient_token']) && !empty($_POST['recipient_token']) &&
    isset($_POST['title']) &&
    isset($_POST['body']))   {

    $recipient_token = $_POST['recipient_token'];
    $title = $_POST['title'];
    $body = $_POST['body'];

    $url = 'https://fcm.googleapis.com/v1/projects/ykis-4cab7/messages:send';

    $fields = json_encode([
        'message' => [
            'token' => $recipient_token,
            'notification' => [
                'title' => $title,
                'body' => $body
            ]
        ]
    ]);
    $headers = [
        'Authorization: Bearer cf76348ba82d2d76d433b81421dfd289c4864abe',
        'Content-Type: application/json'
    ];

    $h = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);

    $result = curl_exec($ch);
    if ($result === FALSE) {
        die('FCM Send Error: ' . curl_error($ch));
    }

    curl_close($ch);
    echo $result;
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>


