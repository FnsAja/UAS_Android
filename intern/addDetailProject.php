<?php
	
include 'koneksi.php';
	
class user{}
	
$query1 = mysqli_query($con, "SELECT AUTO_INCREMENT as idproject FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'xyzinternship' AND TABLE_NAME = 'projects'"); //query1 digunakan untuk locate data terbaru terakhir yang ada dalam database misal id sampai 7 maka dia akan mencari id terbaru yang ingin ditambah kan
$r = mysqli_fetch_assoc($query1);
$idproject = $r['idproject'] - 1; //idproject dikurangi satu karena pada query1 locate id data ke yang terbaru, agar bisa select ke data terakhir maka id - 1
$idintern = trim($_POST["idIntern"]); //menghilangkan spasi pada idintern
$jobdesc = trim($_POST["jobDesc"]); //menghilangkan spasi pada jobdecs

if ((empty($idintern)) || (empty($jobdesc))){
	$response = new user();
	$response->success = 0;
	$response->message = "Field tidak boleh kosong"; //cek idintern dan jobDesc tidak boleh kosong
	die(json_encode($response));
} else {
	if (!empty($idintern)){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM userpro WHERE userpro.iduser = '$idintern' AND userpro.idproject = '$idproject'"));
		if ($num_rows == 0){
			$query = mysqli_query($con, "INSERT INTO userpro (idproject, iduser, jobdesk) VALUES ('$idproject', '$idintern', '$jobdesc')");
			if ($query){
				$response = new user();
				$response->success = 1;
				$response->message = "Menambah Project berhasil!";
				die(json_encode($response)); //merupakan proses untuk menambahkan idintern dan jobDesc kedalam database
			}else{
				$response = new user();
				$response->success = 0;
				$response->message = "Gagal";
				die(json_encode($response)); //merupakan respon apabila data gagal dimasukan kedalam database
			}
		} else {
			$response = new user();
			$response->success = 0;
			$response->message = "User sudah ada didalam Project";
			die(json_encode($response));  // merupakan excution apabila user sudah ada didalam project 
		}
	}
}

mysqli_close($con);

?>
