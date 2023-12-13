<?php
include_once "DB.php";

class DBOperationsMtb {
    public function __constructor() {

    }

    public function newOplata( $address_id  , $oplata1 , $oplata4 , $oplata5 , $oplata6 , $oplata10 ,$new_oplata , $user_id  )
    {
        $com = new DbConnect();
        $sql='CALL YISGRAND.newPaymentMarfin(
        "'.$address_id.'",
        "'.$oplata1.'",
        "'.$oplata4.'",
        "'.$oplata5.'",
        "'.$oplata6.'",
        "'.$oplata10.'",
        "'.$new_oplata.'",
        "'.$user_id.'",
        @kvartplata,
        @teplo, 
        @voda, 
        @tbo, 
        @payment_id,
        @edrpou,
        @firstname,
        @patronymic,
        @surname,
        @account,
        @address,
        @data_in ,
        @success, @msg)';

        mysqli_query( $com->getDb(), $sql);

        $sqlCallBack='SELECT  @kvartplata, @teplo, @voda, @tbo, @payment_id,@edrpou,@firstname,@patronymic,@surname,@account,@address,@data_in ,@success, @msg';

        $result = mysqli_query( $com->getDb(), $sqlCallBack);


        return $result;
    }

    public function checkPaymentMtb( mixed $ndoc, mixed $status, mixed $uri, mixed $uuid)
    {
        $com = new DbConnect();
        $sql = 'UPDATE YISGRAND.MTB_PAYMENT as t1  SET t1.`chek`="'.$status.'", t1.`pay_id`="'.$ndoc.'" ,t1.`uuid`="'.$uuid.'" WHERE t1.`payment_id`="'.$this->results['payment_id'].'"';
//        print_r($sql);
        mysqli_query( $com->getDb(), $sql);
        $sqlCallBack = 'SELECT @success , @msg ';
        $result = mysqli_query( $com->getDb(), $sqlCallBack);
        return $result;
    }
}