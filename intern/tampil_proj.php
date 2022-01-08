<?php 
require_once('koneksi.php');

$query = "SELECT projects.idproject, COUNT(users.id) as jumlah, projects.namaproject, users.nama FROM users JOIN userpro ON users.id = userpro.iduser JOIN projects ON projects.idproject = userpro.idproject GROUP BY projects.idproject;";
			//query untuk menampilan isi project beserta relasi dengan user yang ada.
$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row; //fetching data dari fungsi select diatas.
}

echo json_encode($result);

mysqli_close($con);

?>