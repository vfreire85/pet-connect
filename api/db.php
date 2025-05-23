<?php
// /var/www/html/api/db.php

$dbHost = 'localhost';
$dbName = 'petconnect';
$dbUser = 'api_user'; // Ou um usuário específico com permissões
$dbPass = '5W;#(m=I'; // Substitua pela senha real

try {
    $db = new PDO("pgsql:host=$dbHost;dbname=$dbName", $dbUser, $dbPass);
    $db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    $db->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
    
    // Habilita suporte a JSON no PostgreSQL (se necessário)
    $db->exec("SET search_path TO public");
    
    return $db;
} catch (PDOException $e) {
    error_log("Erro de conexão: " . $e->getMessage());
    throw new Exception("Falha ao conectar ao banco de dados");
}
