<?php 
require_once('koneksi.php');

$idproj = $_POST["idproj"];

$query = "SELECT projects.namaproject as namaproj, users.nama as namaintern, userpro.jobdesk as jobdesc from projects JOIN userpro ON userpro.idproject = projects.idproject JOIN users ON users.id = userpro.iduser WHERE projects.idproject = '$idproj'";

$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

echo json_encode($result);

mysqli_close($con);

?>