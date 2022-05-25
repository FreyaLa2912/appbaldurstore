<?php 
include "dbconnect.php";
use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\Exception;

require 'PHPMailer/src/Exception.php';
require 'PHPMailer/src/PHPMailer.php';
require 'PHPMailer/src/SMTP.php';
$email = $_POST['email'];
$query = 'SELECT * FROM `user` WHERE `email` = "'.$email.'"';
$data = mysqli_query($conn,$query);
$result = array();
while($row = mysqli_fetch_assoc($data)){
    $result[] = ($row);
    // code...
}
if(empty($result)){
    $arr = [
        'success' => false,
        'message' => 'Mail không chính xác',
        'result' => $result
    ];
}else{
    // send mail
    print_r($result);
    $email = ($result[0]['email']);
    $pass = ($result[0]['pass']);
    //sua uRL khi doi IP
    $link="<a href='http://192.168.31.190/android-baldur/reset_pass.php?key=".$email."&reset=".$pass."'>Click To Reset password</a>";
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;                  
    // GMAIL username
    $mail->Username = "freya.la2912@gmail.com";
    // GMAIL password
    $mail->Password = "JenyLa2912"; //pass cua mail
    $mail->SMTPSecure = "ssl";  
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From="freya.la2912@gmail.com"; //mail nguoi nhan
    $mail->FromName='Baldur Store';
    $mail->AddAddress($email, 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body = $link;
    if($mail->Send())
    {
        $arr = [
            'success' => true,
            'message' => 'Please check your email',
            'result' => $result
        ];
        //Xuat hien loi thong bao tu App
    }
    else
    {
        $arr = [
            'success' => false,
            'message' => 'Send failed',
            'result' => $mail ->ErorrInfo
        ];
    }
}
print_r(json_encode($arr));



    

?>