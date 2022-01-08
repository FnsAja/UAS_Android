<?php
	
include 'koneksi.php';
	
class proj{}
	
$idproject = trim($_POST["idProj"]);

if ((empty($idproject))){
	$response = new proj();
	$response->success = 0;
	$response->message = "ID tidak boleh kosong"; //idproject yang ingin didelete tidak boleh kosong atau tidak ada data.
	die(json_encode($response));
} else {
	if (!empty($idproject)){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM userpro WHERE idproject='$idproject'"));
		if ($num_rows > 0){
			$query = mysqli_query($con, "DELETE FROM userpro WHERE idproject = '$idproject'"); //query pertama untuk mendelete relasi idprojet yang ada didalam tabel userpro
			if($query){
				$response = new proj();
				$response->message1 = "Relasi Terhapus";
				$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM projects WHERE idproject='$idproject'")); 
				if ($num_rows > 0){
					$query = mysqli_query($con, "DELETE FROM projects WHERE idproject = '$idproject'"); //query kedua untuk mendelete idproject yang ada didalam projects
					if($query){
						$response->success = 1;
						$response->message = "Berhasil Menghapus Project";
						die(json_encode($response)); //pesan apabila project telah terhapus
					}
				} else {
					$response->success = 0;
					$response->message = "Project Tidak Terhapus";
					die(json_encode($response)); //pesan apabila idproject gagal dihapus di tabel project
				}
			} else{
				$response = new proj();
				$response->success = 0;
				$response->message1 = "Relasi Tidak Terhapus";
				die(json_encode($response)); //pesan apabila idProject tidak terhapus di tabel userpro
			}			
		}		
	}
}

mysqli_close($con);

?>