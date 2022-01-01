<?php 
require_once('koneksi.php');

$iduser = $_POST["iduser"];

$query = "SELECT projects.idproject, COUNT(users.id) as jumlah , projects.namaproject FROM users JOIN userpro ON userpro.iduser = users.id JOIN projects ON userpro.idproject = projects.idproject WHERE projects.idproject IN (SELECT userpro.idproject FROM userpro WHERE userpro.iduser = '$iduser') GROUP BY projects.namaproject ORDER BY projects.idproject ASC";

$res = mysqli_query($con, $query) or die("Query Error : " .mysqli_error($con));

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

echo json_encode($result);

mysqli_close($con);

?>