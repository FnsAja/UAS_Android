<?php
	
include_once "koneksi.php";

class user{}

$username = trim($_POST["username"]); //membaca variabel username dan menghilangkan spasi
$password = trim($_POST["password"]); //membaca variabel password dan menghilangkan spasi

if ((empty($username)) || (empty($password))) { 
	$response = new user();
	$response->success = 0;
	$response->message = "Username atau password kosong"; 
	die(json_encode($response));
} //cek apabila username atau password yang diinput kosong.
	
$query = mysqli_query($con, "SELECT * FROM users WHERE username='$username' AND password='$password'"); //cek apabila username dan password ada dan sesuai dalam database mysql
	
$row = mysqli_fetch_array($query); //fetching database
	
if (!empty($row)){
	$response = new user();
	$response->success = 1;
	$response->message = "Selamat datang ".$row['username'];
	$response->username = $row['username'];
	$response->id = $row['id'];
	$response->fullname = $row['nama'];
	$response->access = 0; //sampai saat ini merupakan tahap login user sukses dan diberikan access= 0 artinya tidak punya akses admin.
	if($row['divisi'] == "admin"){
		$response->access = 1; 
	}//jika username yang di login sama dengan "admin" maka akan diberikan access = 1 artinya punya akses admin.
	die(json_encode($response));
		
} else { 
	$response = new user();
	$response->success = 0;
	$response->message = "Username atau password salah";
	die(json_encode($response)); //ini apa bila username dan password salah dan akan dikeluarkan message = 0
}
	
mysqli_close($con);

?>