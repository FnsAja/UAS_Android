<?php 
define('HOST', 'localhost'); //mendefinisikan host
define('USER', 'root'); //mendefinisikan user
define('PASS', ''); //mendefinisikan pass
define('DATABASE', 'xyzinternship'); //mendefinisikan database yang digunakan

$con = mysqli_connect(HOST,USER,PASS,DATABASE) or die('Unable to Connect');
?>