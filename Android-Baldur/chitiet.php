<?php
    include "dbconnect.php";
    $page = $_POST['page'];
    $total = 5; //1 trang 5 sp
    $pos = ($page-1)*$total; //0,5 0,5
    $loai = $_POST['type'];


    $query = 'SELECT * FROM `sanphammoi` WHERE `type` = '.$loai.' LIMIT '.$pos.','.$total.' ';
    $data = mysqli_query($conn, $query);
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

    print_r(json_encode($arr));

?>