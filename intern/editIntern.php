<?php

use user as GlobalUser;

include 'koneksi.php';
	
class user{}
	
$idintern = trim($_POST["idIntern"]);
$fullname = trim($_POST["fullname"]);
$username = trim($_POST["username"]);
$address = trim($_POST["address"]);
$email = trim($_POST["email"]);
$phone = trim($_POST["phone"]);
$performance = trim($_POST["performance"]);
$division = trim($_POST["division"]);
$aboutme = trim($_POST["aboutme"]);
// $idintern = 3;
// $fullname = "DonixWawan";
// $username = "doniawan";
// $address = "Pantai mutiara";
// $email = "doniwan@gmail.com";
// $phone = "082345211234";
// $performance = "Very Good";
// $division = "IT Web";
// $aboutme = "Saya Doni Awan, saya pekerja yang jujur dan sangat menjunjung ketepatan waktu";

if ((empty($fullname)) || (empty($username)) || (empty($address)) || (empty($email)) || (empty($phone)) || (empty($division)) || (empty($performance)) || (empty($aboutme))) {
	$response = new user();
	$response->success = 0;
	$response->message = "Field tidak boleh kosong";
	die(json_encode($response));
} else {
	if (!empty($idintern)){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM users WHERE id='$idintern'"));
		if ($num_rows > 0){
			$query = mysqli_query($con, "UPDATE users SET nama = '$fullname', username = '$username', alamat = '$address', email = '$email', notelp = '$phone', divisi = '$division', about = '$aboutme', performance = '$performance' WHERE id = '$idintern'");
			if($query){
				$response = new user();
                $response->success = 1;
				$response->message = "Update Berhasil!";	
                die(json_encode($response));
			} else {
				$response = new user();
                $response->success = 0;
				$response->message = "User gagal diupdate :( ";
				die(json_encode($response));
				}
		} else{
			$response = new user();
			$response->success = 0;
			$response->message = "User tidak ada";
			die(json_encode($response));
		}			
	}		
}

mysqli_close($con);

?>