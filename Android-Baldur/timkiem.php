<?php 
    
    include "dbconnect.php";
   $search = $_POST['search'];
   if (empty($search)){
    $arr = [
        'success' => false,
        'massage' => " khong thanh cong"
    ];
   } else {
      
       $query = "SELECT * FROM `sanphammoi` WHERE `product_name` LIKE '%".$search."%';";
       $data = mysqli_query($conn,$query);
       $result = array();
       while ($row = mysqli_fetch_assoc($data)){
           $result[] = ($row);
           //code...
       }

       if(!empty($result)) {
           $arr = [
               'success' => true,
               'massage' => "thanh cong",
               'result' => $result
           ];
       } else {
           $arr = [
               'success' => false,
               'massage' => " khong thanh cong",
               'result' => $result
           ];
       }
   }
    print_r(json_encode($arr));
?>