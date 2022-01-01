<?php 
require_once('koneksi.php');

$iduser = $_POST["iduser"];

$query = "SELECT users.id, users.nama, users.email, users.notelp, users.alamat, users.about FROM users WHERE users.id = '$iduser'";

$result = array();

$res = mysqli_query($con, $query);

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

echo json_encode($result);

mysqli_close($con);

?>