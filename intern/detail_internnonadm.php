<?php 
require_once('koneksi.php');

$iduser = $_POST["iduser"];

$query = "SELECT users.id, users.nama, users.email, users.notelp, users.alamat, users.about, projects.namaproject FROM projects JOIN userpro ON projects.idproject = userpro.idproject JOIN users ON userpro.iduser = users.id WHERE userpro.iduser = (SELECT users.id FROM users JOIN userpro ON userpro.iduser = users.id WHERE userpro.iduser = '$iduser');";

$res = mysqli_query($con, $query);

$result = array();

while ($row = mysqli_fetch_array($res)){
    $result[] = $row;
}

if($result == null){
    $query = "SELECT users.id, users.nama, users.email, users.notelp, users.alamat, users.about FROM users WHERE users.id = '$iduser'";
    $res = mysqli_query($con, $query);
    while ($row = mysqli_fetch_array($res)){
        $result[] = $row;
    }
}

echo json_encode($result);

mysqli_close($con);

?>