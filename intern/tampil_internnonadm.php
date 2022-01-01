<?php 
require_once('koneksi.php');

$iduser = $_POST['iduser'];

$query = "SELECT * FROM users WHERE id = '$iduser'";

$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

echo json_encode($result);

mysqli_close($con);

?>