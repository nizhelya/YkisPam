<?php

 include_once "config.php";  

class DbConnect{
	private $db;
	
		public function __construct(){
		
		$this->db = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME) or die('Connect Error in ' .$this->db->connect_error);

            if ($this->db->connect_error) {
                return false;
            } else {		
                $this->db->set_charset("utf8mb4");    
            }
        }
        public function getDb(){	
            return $this->db;
		}	
} 
