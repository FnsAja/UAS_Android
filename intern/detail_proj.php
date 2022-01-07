<?php 
require_once('koneksi.php');

$idproject = $_POST['idproj'];

$query = "SELECT projects.namaproject as namaproj, users.nama as namaintern, users.divisi as divisiintern, userpro.jobdesk as jobdesc, projects.descpro as deskripsi, projects.start as startdate, projects.end as enddate from projects JOIN userpro ON projects.idproject = userpro.idproject JOIN users ON users.id = userpro.iduser WHERE projects.idproject = '$idproject'";
		//query untuk mengselect project seuai dengan idproject yang terpilih.
$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row; //menampilkan detail project berdasarkan project yang dipilih.
}

echo json_encode($result);

mysqli_close($con);

?>