<?php 
    include "dbconnect.php";
    $sdt = $_POST['sdt'];
    $email = $_POST['email'];
    $tongtien = $_POST['tongtien'];
    $iduser = $_POST['iduser'];
    $diachi = $_POST['diachi'];
    $soluong = $_POST['soluong'];
    $chitiet = $_POST['chitiet'];




    $query = 'INSERT INTO `donhang` (`iduser`,`diachi`,`sodienthoai`,`email`,`soluong`,`tongtien`) VALUES ('.$iduser.',"'.$diachi.'","'.$sdt.'","'.$email.'",'.$soluong.',"'.$tongtien.'")';
    $data = mysqli_query($conn,$query);
    if($data == true){
        $query = 'SELECT id AS iddonhang FROM `donhang` WHERE `iduser` = '.$iduser.' ORDER BY id DESC LIMIT 1';
        $data = mysqli_query($conn,$query);
        while($row = mysqli_fetch_assoc($data)){
            $iddonhang = ($row);
        }
        if(!empty($iddonhang)){
            // co don hang (đẩy dòng copy vào đây)
            $chitiet = json_decode($chitiet, true);
            foreach ($chitiet as $key => $value) {
                $truyvan = 'INSERT INTO `chitietdonhang` (`iddonhang`,`idsp`,`soluong`,`gia`) VALUES ('.$iddonhang["iddonhang"].','.$value["idsp"].','.$value["soLuong"].',"'.$value["giasp"].'")';
                echo $truyvan;
                $data = mysqli_query($conn,$truyvan);
             
            }
            if($data == true){
                $arr = [
                    'success' => true,
                    'message' => 'thanh cong'
                   
                ];
            }else{
                $arr = [
                    'success' => false,
                    'message' => 'khong thanh cong 2'
                   
                ];
            }
            print_r(json_encode($arr));
        }
    }else{
        $arr = [
        'success' => false,
        'message' => "khong thanh cong 1",
        ];
        print_r(json_encode($arr));
    }


?>