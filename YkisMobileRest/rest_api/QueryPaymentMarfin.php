<?php
include_once './yis_config.php';

class QueryPaymentMarfin
{
	private $_db;
	protected $login;
	protected $result;
	protected $res_callback;
	protected $sql;	
	protected $sql_callback;
	protected $url_callback;
	protected $row;	
	protected $what;
	protected $nomer;
	protected $type;
	protected $pokaz;
	protected $pred;
	protected $tek;
	protected $kvartplata =0;
	protected $rfond =0;

	protected $teplo=0;
	protected $voda=0;
	protected $tbo=0;
	protected $data=NULL;
	protected $res=array();	
	public	  $results=array();





public function newOplata(stdClass $params){
	
$_db = $this->connect();
$array = (array) $params;
  foreach ( $array as $key => $value ) 
    {
      if(isset($value)) { 
	if (is_int($value)) { $this->$key= (int)$value;}
	else if (is_float($value)) { $this->$key= $value;}
	else {$this->$key =$value;}
	}
    }
$this->sql='CALL YISGRAND.newPaymentMarfin('
.$this->address_id.',"'
.$this->oplata1.'","'
.$this->oplata4.'","'
.$this->oplata5.'","'
.$this->oplata6.'","'	
.$this->oplata10.'","'
.$this->newOplata.'","'
.$this->user_id.'",'
.' @kvartplata,@teplo, @voda, @tbo, @payment_id,@edrpou,@firstname,@patronymic,@surname,@account,@address,@data_in ,@success, @msg)';

$this->result = $_db->query($this->sql) or die('Connect Error in '.$this->what.'(' .  $this->sql . ') ' . $_db->connect_error);

$this->sql_callback='SELECT  @kvartplata, @teplo, @voda, @tbo, @payment_id,@edrpou,@firstname,@patronymic,@surname,@account,@address,@data_in ,@success, @msg';

$this->res_callback = $_db->query($this->sql_callback) or die('Connect Error in '.$this->what.'(' .  $this->sql_callback . ') ' . $_db->connect_error);

while ($this->row = $this->res_callback->fetch_assoc()) {
/*
  $this->results['kvartplata']	= $this->row['@kvartplata'] *100;
  $this->results['teplo']	= $this->row['@teplo'] *100;
  $this->results['voda']	= $this->row['@voda'] *100;
  $this->results['tbo']		= $this->row['@tbo'] *100;
  */
  $this->results['kvartplata']	= $this->row['@kvartplata'];
  $this->results['teplo']	= $this->row['@teplo'];
  $this->results['voda']	= $this->row['@voda'];
  $this->results['tbo']		= $this->row['@tbo'] ;
  $this->results['payment_id'] 	= $this->row['@payment_id'];
  $this->results['edrpou'] 	= $this->row['@edrpou'];
  $this->results['firstname'] 	= $this->row['@firstname'];
  $this->results['patronymic'] 	= $this->row['@patronymic'];
  $this->results['surname'] 	= $this->row['@surname'];
  $this->results['account'] 	= $this->row['@account'];
  $this->results['address'] 	= $this->row['@address'];
  $this->results['data_in'] 	= $this->row['@data_in'];
  $this->results['success'] 	= $this->row['@success'];
  $this->results['msg']		= $this->row['@msg'];
}	


if ($this->results['success'] == 1) {


 $partner = array();
 $service = array();
 $teplo = array();
 $voda = array();
 $tbo = array();
 $kvartplata = array();
 $transaction = array();
 $billattr = array();
 $data = array();
 $requestData = array();

 
 if ($this->results['teplo'] >0)  { 
	$teplo["ServiceCode"] ="26134519";
	$teplo["Sum"] =  $this->results['teplo'];
	$service[] =  $teplo;


 }
 if ($this->results['kvartplata'] >0  ) {
	$kvartplata["ServiceCode"] =$this->results['edrpou'];
	$kvartplata["Sum"] =  $this->results['kvartplata'];
	$service[] =  $kvartplata;
} 
if ($this->results['voda'] >0 ) {
	$voda["ServiceCode"] ="31783053";
	$voda["Sum"] = $this->results['voda'];
	$service[] =  $voda;
}
if ($this->results['tbo'] >0) {
	$tbo["ServiceCode"] ="30750184";
	$tbo["Sum"] = $this->results['tbo'] ;
	$service[] =  $tbo;
}

$partner["PartnerToken"] = "8aff556f-1025-439a-8c7d-fda279523332";
$partner["OperationType"] = 10005;


$billattr["PayerAddress"] = $this->results['address'];

$transaction["TransactionID"] = $this->results['payment_id'];
$transaction["TerminalID"] = "1" ;
$transaction["DateTime"] = $this->results['data_in'];

$data["PayType"] = "7";
$data["Phone"] = "";
$data["Email"] = "";
$data["Account"] = $this->results['account'];
$data["FirstName"] = $this->results['firstname'];
$data["LastName"] = $this->results['surname'];
$data["MiddleName"] = $this->results['patronymic'];
$data["Service"] = $service;
$data["BillAttr"] = $billattr;
$data["Transaction"] = $transaction;

		 
$requestData["Partner"] =  $partner;
$requestData["Data"] = json_encode($data);	 

$json_data= json_encode($requestData);	
//print_r(json_decode($json_data));


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
//print_r(json_decode($curl_result_code));

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

curl_close( $curl_url );
$paym = json_decode($curl_result_url,true);

//print_r($paym);



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
  
  if ($code == "200" && $status == "10" ) { 
  $this->results['success']=1;
  $this->results['url'] = $uri;
 //print_r($this->results['url']);

  $this->up_stat ='UPDATE YISGRAND.MTB_PAYMENT as t1  SET t1.`chek`="'.$status.'", t1.`pay_id`="'.$ndoc.'" ,t1.`uuid`="'.$uuid.'" WHERE t1.`payment_id`="'.$this->results['payment_id'].'" ';
  			    //   print_r($this->up_stat); 

  $this->upd_status = $_db->query($this->up_stat) or die('Connect Error in '.$this->what.'(' .  $this->up_stat . ') ' . $_db->connect_error);
  
  
  } else {
    $this->results['success']=0;
    $this->results['msg']='Сервіс платежів Xpay<br>Платеж не сформований';
    }	

}

  return $this->results;

}
	
}
