<?php

$DBOperations_path = "../sql/DBOperations.php";
include($DBOperations_path);

$DBOperationsMtb_path = "../sql/DBOperationsMtb.php";
include($DBOperationsMtb_path);
class GeneralFunctionsClass
{
    public function __constructor()
    {

    }



    public function getFlatById($resultAppartments)
    {
        $dbOperationsObject = new DBOperations();
        $appartments = array();
        while ($rowAppartment = mysqli_fetch_array($resultAppartments)) {
            $appartment = array(
                'address_id' => $rowAppartment['address_id'],
                'address' => $rowAppartment['address'],
                'nanim' => $rowAppartment['nanim'],
                'fio' => $rowAppartment['fio'],
                'order' => $rowAppartment['order'],
                'data' => $rowAppartment['data'],
                'area_full' => $rowAppartment['area_full'],
                'area_life' => $rowAppartment['area_life'],
                'area_dop' => $rowAppartment['area_dop'],
                'area_balk' => $rowAppartment['area_balk'],
                'area_otopl' => $rowAppartment['area_otopl'],

                'tenant' => $rowAppartment['tenant'],
                'podnan' => $rowAppartment['podnan'],
                'absent' => $rowAppartment['absent'],
                'email' => $rowAppartment['email'],
                'phone' => $rowAppartment['phone'],
                'raion_id' => $rowAppartment['raion_id'],
                'house_id' => $rowAppartment['house_id'],
                'tenant_tbo' => $rowAppartment['tenant_tbo'],
                'room' => $rowAppartment['room'],
                'privat' => $rowAppartment['privat'],
                'subsidia' => $rowAppartment['subsidia'],
                'vxvoda' => $rowAppartment['vxvoda'],
                'teplomer' => $rowAppartment['teplomer'],
                'distributor' => $rowAppartment['distributor'],
                'lift' => $rowAppartment['lift'],
                'kvartplata' => $rowAppartment['kvartplata'],
                'otoplenie' => $rowAppartment['otoplenie'],
                'ateplo' => $rowAppartment['ateplo'],
                'podogrev' => $rowAppartment['podogrev'],
                'voda' => $rowAppartment['voda'],
                'stoki' => $rowAppartment['stoki'],
                'avoda' => $rowAppartment['avoda'],
                'astoki' => $rowAppartment['astoki'],
                'tbo' => $rowAppartment['tbo'],
                'aggr_kv' => $rowAppartment['aggr_kv'],
                'aggr_voda' => $rowAppartment['aggr_voda'],
                'aggr_teplo' => $rowAppartment['aggr_teplo'],
                'aggr_tbo' => $rowAppartment['aggr_tbo'],
                'boiler' => $rowAppartment['boiler'],
                'enaudit' => $rowAppartment['enaudit'],
                'heated' => $rowAppartment['heated'],
                'ztp' => $rowAppartment['ztp'],
                'ovu' => $rowAppartment['ovu'],
                'paused' => $rowAppartment['paused'],
                'osmd' => $rowAppartment['osmd'],
                'osmd_id' => $rowAppartment['osmd_id'],
                'osbb' => $rowAppartment['osbb'],
                'what_change' => $rowAppartment['what_change'],

                'data_change' => $rowAppartment['data_change'],
                'enaudit_id' => $rowAppartment['enaudit_id'],
                'tarif_kv' => $rowAppartment['tarif_kv'],
                'tarif_ot' => $rowAppartment['tarif_ot'],
                'tarif_aot' => $rowAppartment['tarif_aot'],
                'tarif_gv' => $rowAppartment['tarif_gv'],
                'tarif_xv' => $rowAppartment['tarif_xv'],
                'tarif_st' => $rowAppartment['tarif_st'],
                'tarif_tbo' => $rowAppartment['tarif_tbo'],
                'tne' => $rowAppartment['tne'],
                'kte' => $rowAppartment['kte'],
                'length' => $rowAppartment['length'],
                'diametr' => $rowAppartment['diametr'],
                'dvodomer_id' => $rowAppartment['dvodomer_id'],
                'dteplomer_id' => $rowAppartment['dteplomer_id'],
                'data_in' => $rowAppartment['data_in'],
                'operator' => $rowAppartment['operator'],
                'kod' => $rowAppartment['kod']
            );

            array_push($appartments, $appartment);
        }
        return $appartments;
    }

    public function getAppartmentsByUser($resultAppartmentsMyflat)
    {
        $dbOperationsObject = new DBOperations();
        $appartments = array();
        while ($rowAppartment = mysqli_fetch_array($resultAppartmentsMyflat)) {
            $appartment = array(
                'id'=>$rowAppartment['id'],
                'user_id'=>$rowAppartment['user_id'],
                'address_id' => $rowAppartment['address_id'],
                'address' => $rowAppartment['address'],
                'nanim' => $rowAppartment['nanim'],
                'fio' => $rowAppartment['fio'],
                'order' => $rowAppartment['order'],
                'data' => $rowAppartment['data'],
                'area_full' => $rowAppartment['area_full'],
                'area_life' => $rowAppartment['area_life'],
                'area_dop' => $rowAppartment['area_dop'],
                'area_balk' => $rowAppartment['area_balk'],
                'area_otopl' => $rowAppartment['area_otopl'],

                'tenant' => $rowAppartment['tenant'],
                'podnan' => $rowAppartment['podnan'],
                'absent' => $rowAppartment['absent'],
                'email' => $rowAppartment['email'],
                'phone' => $rowAppartment['phone'],
                'raion_id' => $rowAppartment['raion_id'],
                'house_id' => $rowAppartment['house_id'],
                'tenant_tbo' => $rowAppartment['tenant_tbo'],
                'room' => $rowAppartment['room'],
                'privat' => $rowAppartment['privat'],
                'subsidia' => $rowAppartment['subsidia'],
                'vxvoda' => $rowAppartment['vxvoda'],
                'teplomer' => $rowAppartment['teplomer'],
                'distributor' => $rowAppartment['distributor'],
                'lift' => $rowAppartment['lift'],
                'kvartplata' => $rowAppartment['kvartplata'],
                'otoplenie' => $rowAppartment['otoplenie'],
                'ateplo' => $rowAppartment['ateplo'],
                'podogrev' => $rowAppartment['podogrev'],
                'voda' => $rowAppartment['voda'],
                'stoki' => $rowAppartment['stoki'],
                'avoda' => $rowAppartment['avoda'],
                'astoki' => $rowAppartment['astoki'],
                'tbo' => $rowAppartment['tbo'],
                'aggr_kv' => $rowAppartment['aggr_kv'],
                'aggr_voda' => $rowAppartment['aggr_voda'],
                'aggr_teplo' => $rowAppartment['aggr_teplo'],
                'aggr_tbo' => $rowAppartment['aggr_tbo'],
                'boiler' => $rowAppartment['boiler'],
                'enaudit' => $rowAppartment['enaudit'],
                'heated' => $rowAppartment['heated'],
                'ztp' => $rowAppartment['ztp'],
                'ovu' => $rowAppartment['ovu'],
                'paused' => $rowAppartment['paused'],
                'osmd' => $rowAppartment['osmd'],
                'osmd_id' => $rowAppartment['osmd_id'],
                'osbb' => $rowAppartment['osbb'],
                'what_change' => $rowAppartment['what_change'],

                'data_change' => $rowAppartment['data_change'],
                'enaudit_id' => $rowAppartment['enaudit_id'],
                'tarif_kv' => $rowAppartment['tarif_kv'],
                'tarif_ot' => $rowAppartment['tarif_ot'],
                'tarif_aot' => $rowAppartment['tarif_aot'],
                'tarif_gv' => $rowAppartment['tarif_gv'],
                'tarif_xv' => $rowAppartment['tarif_xv'],
                'tarif_st' => $rowAppartment['tarif_st'],
                'tarif_tbo' => $rowAppartment['tarif_tbo'],
                'tne' => $rowAppartment['tne'],
                'kte' => $rowAppartment['kte'],
                'length' => $rowAppartment['length'],
                'diametr' => $rowAppartment['diametr'],
                'dvodomer_id' => $rowAppartment['dvodomer_id'],
                'dteplomer_id' => $rowAppartment['dteplomer_id'],
                'data_in' => $rowAppartment['data_in'],
                'operator' => $rowAppartment['operator'],
                'kod' => $rowAppartment['kod'],
                'ipay' => $rowAppartment['ipay'],
                'mtb' => $rowAppartment['mtb'],
                'pb' => $rowAppartment['pb']
            );

            array_push($appartments, $appartment);
        }
        return $appartments;
    }
    public function getBlocks($resultBlocks)
    {
        $dbOperationsObject = new DBOperations();
        $blocks = array();
        while ($rowBlocks = mysqli_fetch_array($resultBlocks)) {
            $block = array(
                'raion_id'=>$rowBlocks['raion_id'],
                'raion'=>$rowBlocks['raion']
            );

            array_push($blocks, $block);
        }
        return $blocks;
    }
    public function getStreetsFromBlock($resultStreets)
    {
        $dbOperationsObject = new DBOperations();
        $streets = array();
        while ($rowStreets = mysqli_fetch_array($resultStreets)) {
            $street = array(
                'street_id'=>$rowStreets['street_id'],
                'street'=>$rowStreets['street']
            );

            array_push($streets, $street);
        }
        return $streets;
    }

    public function getHousesFromStreet($resultHouses)
    {
        $dbOperationsObject = new DBOperations();
        $houses = array();
        // print_r($resultHouses);
        while ($rowHouses = mysqli_fetch_array($resultHouses)) {
            $house = array(
                'house_id'=>$rowHouses['house_id'],
                'house'=>$rowHouses['house']
            );

            array_push($houses, $house);
        }
        return $houses;
    }

    public function getFlatsFromHouse($resultFlats)
    {
        $dbOperationsObject = new DBOperations();
        $flats = array();
        while ($rowFlats = mysqli_fetch_array($resultFlats)) {
            $flat = array(
                'address_id'=>$rowFlats['address_id'],
                'address'=>$rowFlats['address'],
                'kod'=>$rowFlats['kod']
            );

            array_push($flats, $flat);
        }
        return $flats;
    }
    public function addFlatByUser($resultFlats)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        //print_r($resultFlats);
        while ($rowFlats = mysqli_fetch_array($resultFlats)) {
            $flat = array(
                'success'=>$rowFlats[0],
                'message'=>$rowFlats[1],
                'address'=>array()
            );

            array_push($results, $flat);
//             print_r($flat);
        }
        return $results;
    }
    public function getFamilyFromFlat($resultFamily)
    {
        $dbOperationsObject = new DBOperations();
        $families = array();
        while ($row = mysqli_fetch_array($resultFamily)) {
            $family = array(
                'rec_id'=>$row['rec_id'],
                'address_id'=>$row['address_id'],
                'address'=>$row['address'],
                'rodstvo'=>$row['rodstvo'],
                'firstname'=>$row['firstname'],
                'surname'=>$row['surname'],
                'lastname'=>$row['lastname'],
                'born'=>$row['born'],
                'sex'=>$row['sex'],
                'phone'=>$row['phone'],
                'subsidia'=>$row['subsidia'],
                'vkl'=>$row['vkl'],
                'inn'=>$row['inn'],
                'document'=>$row['document'],
                'seria'=>$row['seria'],
                'nomer'=>$row['nomer'],
                'datav'=>$row['datav'],
                'organ'=>$row['organ']
            );

            array_push($families, $family);
        }
        return $families;
    }
    public function checkAddFlat($resultCheck)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        //print_r($resultFlats);
        while ($row = mysqli_fetch_array($resultCheck)) {
            if($row['success'] == 0){
                $mes = "Incorrect code";
            } else {
                $mes = "Success";
            }
            $result = array(
                'success'=>$row['success'],
                'message'=>$mes
            );

            array_push($results, $result);
//             print_r($results);
        }
        return $results;
    }

    public function getFlatServices($resultServices)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($resultServices)) {
            $service = array(
                'address_id'=>$row['address_id'],
                'service'=>$row['service'],
                'service1'=>$row['service1'],
                'service2'=>$row['service2'],
                'service3'=>$row['service3'],
                'service4'=>$row['service4'],
                'data'=>$row['data'],
                'period'=>$row['period'],
                'zadol'=>$row['zadol'],
                'zadol1'=>$row['zadol1'],
                'zadol2'=>$row['zadol2'],
                'zadol3'=>$row['zadol3'],
                'zadol4'=>$row['zadol4'],
                'nachisleno'=>$row['nachisleno'],
                'nachisleno1'=>$row['nachisleno1'],
                'nachisleno2'=>$row['nachisleno2'],
                'nachisleno3'=>$row['nachisleno3'],
                'nachisleno4'=>$row['nachisleno4'],
                'oplacheno'=>$row['oplacheno'],
                'oplacheno1'=>$row['oplacheno1'],
                'oplacheno2'=>$row['oplacheno2'],
                'oplacheno3'=>$row['oplacheno3'],
                'oplacheno4'=>$row['oplacheno4'],
                'dolg'=>$row['dolg'],
                'dolg1'=>$row['dolg1'],
                'dolg2'=>$row['dolg2'],
                'dolg3'=>$row['dolg3'],
                'dolg4'=>$row['dolg4']
            );

            array_push($results, $service);
        }
        return $results;
    }
    public function getFlatPayments($resultPayments)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($resultPayments)) {
            $service = array(
                'rec_id'=>$row['rec_id'],
                'address_id'=>$row['address_id'],
                'address'=>$row['address'],
                'year'=>$row['god'],
                'data'=>$row['data'],
                'kvartplata'=>$row['kvartplata'],
                'remont'=>$row['remont'],
                'otoplenie'=>$row['otoplenie'],
                'voda'=>$row['voda'],
                'tbo'=>$row['tbo'],
                'summa'=>$row['summa'],
                'prixod'=>$row['prixod'],
                'kassa'=>$row['kassa'],
                'nomer'=>$row['operator'],
                'data_in'=>$row['data_in']
            );

            array_push($results, $service);
        }
        return $results;
    }

    public function getWaterMeter($resultWater)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($resultWater)) {
            $waterMeter = array(
                'vodomer_id'=>$row['vodomer_id'],
                'dvodomer_id'=>$row['dvodomer_id'],
                'address_id'=>$row['address_id'],
                'house_id'=>$row['house_id'],
                'address'=>$row['address'],
                'nomer'=>$row['nomer'],
                'model_id'=>$row['model_id'],
                'model'=>$row['model'],
                'st'=>$row['st'],
                'voda'=>$row['voda'],
                'place'=>$row['place'],
                'position'=>$row['position'],
                'obr'=>$row['obr'],
                'joint'=>$row['joint'],
                'sdate'=>$row['sdate'],
                'fpdate'=>$row['fpdate'],
                'pdate'=>$row['pdate'],
                'pp'=>$row['pp'],
                'zdate'=>$row['zdate'],
                'avg'=>$row['avg'],
                'date_ar'=>$row['date_ar'],
                'date_ao'=>$row['date_ao'],
                'out'=>$row['out'],
                'spisan'=>$row['spisan'],
                'paused'=>$row['paused'],
                'data_st'=>$row['data_st'],
                'data_fin'=>$row['data_fin'],
                'norma'=>$row['norma'],
                'data_spis'=>$row['data_spis'],
                'operator'=>$row['operator'],
                'work'=>$row['work']
            );

            array_push($results, $waterMeter);
        }
        return $results;
    }

    public function getWaterReadings($resultWater)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($resultWater)) {
            $waterReadings = array(
                'pok_id'=>$row['pok_id'],
                'vodomer_id'=>$row['vodomer_id'],
//                'address_id'=>$row['address_id'],
                'date_readings'=>$row['data'],
                'date_ot'=>$row['date_ot'],
                'date_do'=>$row['date_do'],
                'days'=>$row['days'],
                'last'=>$row['pred'],
                'currant'=>$row['tek'],
                'kub'=>$row['kub'],
                'avg'=>$row['avg'],
                'tarif_xv'=>$row['tarif_xv'],
                'xvoda'=>$row['xvoda'],
                'tarif_st'=>$row['tarif_st'],
                'stoki'=>$row['stoki'],
                'date_st'=>$row['date_st'],
                'date_fin'=>$row['date_fin'],
                'pok_ot'=>$row['pok_ot'],
                'pok_do'=>$row['pok_do'],
                'rday'=>$row['rday'],
                'mday'=>$row['mday'],
                'kub_day'=>$row['kub_day'],
                'qty_kub'=>$row['qty_kub'],
                'data_in'=>$row['data_in'],
                'operator'=>$row['operator'],
                'address_id'=>$row['address_id']

            );

            array_push($results, $waterReadings);
        }
        return $results;
    }

    public function addCurrentReading($result)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        //print_r($resultFlats);
        while ($rowFlats = mysqli_fetch_array($result)) {
            $flat = array(
                'success'=>$rowFlats[0],
                'message'=>$rowFlats[1]
            );

            array_push($results, $flat);
//             print_r($flat);
        }
        return $results;
    }

    public function deleteCurrentReading($result)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($rowFlats = mysqli_fetch_array($result)) {
            $flat = array(
                'success'=>$rowFlats[0],
                'message'=>$rowFlats[1]
            );

            array_push($results, $flat);
        }
        return $results;
    }

    public function getHeatMeter($resultHeat)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($resultHeat)) {
            $heatMeter = array(
                'teplomer_id'=>$row['teplomer_id'],
//                'dteplomer_id'=>$row['dteplomer_id'],
//                'address'=>$row['address'],
                'nomer'=>$row['nomer'],
                'model_id'=>$row['model_id'],
                'model'=>$row['model'],
                'edizm'=>$row['edizm'],
                'koef'=>$row['koef'],
                'area'=>$row['area'],
                //Дата випуску
                'sdate'=>$row['sdate'],
                //Наступна перевірка
                'fpdate'=>$row['fpdate'],
                //Попередня перевірка
                'pdate'=>$row['pdate'],
//                'pp'=>$row['pp'],
                //Дата встановки пломби
//                'zdate'=>$row['zdate'],
            //в перевірці чи в ремонті
                'out'=>$row['out'],
                'spisan'=>$row['spisan'],

                'data_st'=>$row['data_st'],

                'data_fin'=>$row['data_fin'],
                //Дата списанія
                'data_spis'=>$row['data_spis'],
//                'operator'=>$row['operator'],
                'work'=>$row['work']
            );

            array_push($results, $heatMeter);
        }
        return $results;
    }

    public function getHeatReadings( $result)
    {
        $dbOperationsObject = new DBOperations();
        $results = array();
        while ($row = mysqli_fetch_array($result)) {
            $heatReadings = array(
                'pok_id'=>$row['pok_id'],
                'teplomer_id'=>$row['teplomer_id'],
//                'address_id'=>$row['address_id'],
                'date_readings'=>$row['data'],
                'date_ot'=>$row['date_ot'],
                'date_do'=>$row['date_do'],
                'edizm'=>$row['edizm'],
                'koef'=>$row['koef'],
                'days'=>$row['days'],
                'last'=>$row['pred'],
                'currant'=>$row['tek'],
                'gkal'=>$row['gkal'],
                'avg'=>$row['avg'],
                'tarif'=>$row['tarif'],
                'area'=>$row['area'],
                'gkm2'=>$row['gkm2'],
                'otoplenie'=>$row['otoplenie'],
                'pok_ot'=>$row['pok_ot'],
                'pok_do'=>$row['pok_do'],
                'gkal_rasch'=>$row['gkal_rasch'],
                'gkal_day'=>$row['gkal_day'],
                'qty_day'=>$row['qty_day'],
                'day_avg'=>$row['day_avg'],
                'data_in'=>$row['data_in'],
                'operator'=>$row['operator'],
                'qty'=>$row['qty'],
                'address_id'=>$row['address_id']
            );

            array_push($results, $heatReadings);
        }
        return $results;
    }
    public function getMtbPayment($resultPayment)
    {
        $dbOperationsObject = new DBOperations();
        $payment = array();
        // print_r($resultHouses);
        while ($row = mysqli_fetch_array($resultPayment)) {
            $payment['kvartplata']= $row['@kvartplata'];
            $payment['teplo']	    = $row['@teplo'];
            $payment['voda']	    = $row['@voda'];
            $payment['tbo']	    = $row['@tbo'] ;
            $payment['payment_id'] = $row['@payment_id'];
            $payment['edrpou'] 	= $row['@edrpou'];
            $payment['firstname']	= $row['@firstname'];
            $payment['patronymic']= $row['@patronymic'];
            $payment['surname']	= $row['@surname'];
            $payment['account']	= $row['@account'];
            $payment['address']	= $row['@address'];
            $payment['data_in']	= $row['@data_in'];
            $payment['success']	= $row['@success'];
            $payment['msg']		= $row['@msg'];
        }
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


            if ($payment['teplo'] >0)  {
                $teplo["ServiceCode"] ="26134519";
                $teplo["Sum"] =  $payment['teplo'];
                $service[] =  $teplo;


            }
            if ($payment['kvartplata'] >0  ) {
                $kvartplata["ServiceCode"] =$payment['edrpou'];
                $kvartplata["Sum"] =  $payment['kvartplata'];
                $service[] =  $kvartplata;
            }
            if ($payment['voda'] >0 ) {
                $voda["ServiceCode"] ="31783053";
                $voda["Sum"] = $payment['voda'];
                $service[] =  $voda;
            }
            if ($payment['tbo'] >0) {
                $tbo["ServiceCode"] ="30750184";
                $tbo["Sum"] = $payment['tbo'] ;
                $service[] =  $tbo;
            }

            $partner["PartnerToken"] = "8aff556f-1025-439a-8c7d-fda279523332";
            $partner["OperationType"] = 10005;


            $billattr["PayerAddress"] = $payment['address'];

            $transaction["TransactionID"] = $payment['payment_id'];
            $transaction["TerminalID"] = "1" ;
            $transaction["DateTime"] = $payment['data_in'];

            $data["PayType"] = "7";
            $data["Phone"] = "";
            $data["Email"] = "";
            $data["Account"] = $payment['account'];
            $data["FirstName"] = $payment['firstname'];
            $data["LastName"] = $payment['surname'];
            $data["MiddleName"] = $payment['patronymic'];
            $data["Service"] = $service;
            $data["BillAttr"] = $billattr;
            $data["Transaction"] = $transaction;


            $requestData["Partner"] =  $partner;
            $requestData["Data"] = json_encode($data);
        return $requestData;
    }
}