<?php
	
include 'koneksi.php';
	
class user{}
	
$fullname = trim($_POST["fullname"]); //menghilangkan spasi di fullname
$username = trim($_POST["username"]); //menghilangkan spasi di username
$password = trim($_POST["password"]); //menghilangkan spasi di password
$confirm_password = trim($_POST["confirm_password"]); //menghilangkan spasi di confirm_password
$address = trim($_POST["address"]); //menghilangkan spasi di address
$email = trim($_POST["email"]); //menghilangkan spasi di email
$phone = trim($_POST["phone"]); //menghilangkan spasi di phone
$division = trim($_POST["division"]); //menghilangkan spasi di division 
$status = trim($_POST["status"]); //menghilangkan spasi di status
$about = trim($_POST["about"]);

if ((empty($fullname)) || (empty($about)) || (empty($username)) || (empty($password)) || (empty($address)) || (empty($email)) || (empty($phone)) || (empty($division)) || (empty($status))) {
	$response = new user();
	$response->success = 0;
	$response->message = "Field tidak boleh kosong"; //fungsi untuk cek apabila inputan diantara fullname, username, password, email, phone, division, status ada yang kosong.
	die(json_encode($response));
} else if ((empty($confirm_password)) || $password != $confirm_password) {
	$response = new user();
	$response->success = 0;
    $response->message = "Konfirmasi password harus sama"; //didalam registrasi confirm password harus sama.
	die(json_encode($response));
} else {
	//TODO REGEX
	if (!empty($username) && $password == $confirm_password){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM users WHERE username='".$username."'")); //select database sesuai dengan username

        if ($num_rows == 0){
		$query = mysqli_query($con, "INSERT INTO users (nama, username, password, alamat, email, notelp, divisi, performance, about) VALUES ('".$fullname."','".$username."', '".$password."', '".$address."', '".$email."', '".$phone."', '".$division."', '".$status."', '".$about."')");
			if ($query){				//query untuk push data kedalam database.
				$response = new user();
				$response->success = 1;
				$response->message = "Register berhasil";
				die(json_encode($response)); //registrasi berhasil dan data masuk kedalam database
			}
		} else {
			$response = new user();
			$response->success = 0;
			$response->message = "Username sudah terdaftar";
			die(json_encode($response)); //username telah terdaftar
		}
	}
}

mysqli_close($con);

?>