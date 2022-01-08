<?php 
require_once('koneksi.php');

$query = "SELECT * FROM users"; //query untuk menampilkan data dari tabel user

$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
} //fetching data dari select diatas.

echo json_encode($result);

mysqli_close($con);

?>