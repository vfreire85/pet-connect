<?php
// Conexão com o banco de dados PostgreSQL
$host = 'localhost';
$dbname = 'petconnect';
$user = 'postgres';
$pass = '123456'; // Substitua pela sua senha do PostgreSQL
$dsn = "pgsql:host=$host;dbname=$dbname;port=5432";

//try {
    // Conecta ao banco de dados
  //  $pdo = new PDO($dsn, $user, $pass);
    //$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
//} catch (PDOException $e) {
  //  echo json_encode(["error" => "Erro ao conectar com o banco de dados: " . $e->getMessage()]);
    //exit;
//}
try {
    $pdo = new PDO($dsn, $user, $pass);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    echo json_encode(["message" => "Conexão com o banco de dados bem-sucedida!"]);
} catch (PDOException $e) {
    echo json_encode(["error" => "Erro ao conectar com o banco de dados: " . $e->getMessage()]);
}
return $pdo;
?>