<?php 
require_once('koneksi.php');

$iduser = $_POST["iduser"];

$query = "SELECT projects.namaproject as namaproj, users.nama as namaintern, userpro.jobdesk as jobdesc from projects JOIN userpro ON projects.idproject = userpro.idproject JOIN users ON users.id = userpro.iduser WHERE projects.idproject = (SELECT userpro.idproject FROM userpro WHERE userpro.iduser = '$iduser')";

$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

echo json_encode($result);

mysqli_close($con);

?>