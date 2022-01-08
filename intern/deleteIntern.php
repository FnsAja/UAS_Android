<?php
	
include 'koneksi.php';
	
class intern{}
	
$idintern = trim($_POST["idIntern"]);

if ((empty($idintern))){
	$response = new intern();
	$response->success = 0;
	$response->message = "ID tidak boleh kosong";
	die(json_encode($response));  //id intern yang didelete tidak boleh kosong
} else {
	if (!empty($idintern)){
		$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM userpro WHERE iduser='$idintern'")); //select idintern yang ingin didelete
		if ($num_rows > 0){
			$query = mysqli_query($con, "DELETE FROM userpro WHERE iduser = '$idintern'"); //querry ini akan mendelete relasi di tabel userpro, semua idrelasi yang ada user tersebut akan dihapus
			if($query){
				$response = new intern();
				$response->message1 = "Relasi Terhapus";
				$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM users WHERE id='$idintern'")); 
				if ($num_rows > 0){
					$query = mysqli_query($con, "DELETE FROM users WHERE id = '$idintern'"); //setelah terdelete query nya, disini akan didelete user nya dari tabel user.
					if($query){
						$response->success = 1;
						$response->message = "Berhasil Menghapus User";
						die(json_encode($response));  //pesan bahwa user telah terdelete
					}
				} else {
					$response->success = 0;
					$response->message = "User Tidak Terhapus";
					die(json_encode($response)); //pesan apabila user tidak terhapus di tabel user
				}
			} else{
				$response = new intern();
				$response->success = 0;
				$response->message1 = "Relasi Tidak Terhapus";
				die(json_encode($response)); //pesan apabila user tidak terhapus di tabel relasi userpro
			}			
        } else{
            $response = new intern();
			$response->message1 = "Relasi Terhapus";
			$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM users WHERE id='$idintern'"));
			if ($num_rows > 0){
				$query = mysqli_query($con, "DELETE FROM users WHERE id = '$idintern'");
				if($query){
					$response->success = 1;
					$response->message = "Berhasil Menghapus User";
					die(json_encode($response));
				}
			} else {
				$response->success = 0;
				$response->message = "User Tidak Terhapus";
				die(json_encode($response));
			}
        }	
	}
}

mysqli_close($con);

?>