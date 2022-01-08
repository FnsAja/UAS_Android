<?php
	
include 'koneksi.php';
	
class user{}
	
$projectname = trim($_POST["ProjName"]); //menghilangkan spasi di projectname
$projectdesc = trim($_POST["ProjDesc"]); //menghilangkan spasi di projectdesc
$start_date = trim($_POST["StartDate"]); //menghilangkan spasi di startdate
$end_date = trim($_POST["EndDate"]); //menghilangkan spasi di enddate

if ((empty($projectname)) || (empty($projectdesc)) || (empty($start_date)) || (empty($end_date))){
	$response = new user();
	$response->success = 0;
	$response->message = "Field tidak boleh kosong"; // inputan projectname, projectdesc, tanggal mulai dan tanggal selesai tidak boleh kosong.
	die(json_encode($response));
} else {
	if (!empty($projectname)){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM projects WHERE namaproject='".$projectname."'")); //select nama project yang ingin dimasukan

        if ($num_rows == 0){
		$query = mysqli_query($con, "INSERT INTO projects (namaproject, descpro, start, end) VALUES ('".$projectname."','".$projectdesc."', '".$start_date."', '".$end_date."')");
			if ($query){
				$response = new user();
				$response->success = 1;
				$response->message = "Menambah project berhasil";
				die(json_encode($response)); // proses menambahkan project dan berhasil
			}
		} else {
			$response = new user();
			$response->success = 0;
			$response->message = "Nama Project sudah ada";
			die(json_encode($response)); // exception apabila data sudah ada dalam database.
		}
	}
}

mysqli_close($con);

?>