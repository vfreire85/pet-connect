<?php
// /var/www/html/api/locations.php

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

$app->get('/locations/search', function (Request $request, Response $response) {
    $db = $this->get('db');
    $params = $request->getQueryParams();

    try {
        // Exemplo: Busca por proximidade usando PostGIS
        $stmt = $db->prepare("
            SELECT id, name, address, 
                   ST_Distance(location, ST_MakePoint(:lng, :lat)::geography) AS distance
            FROM locations
            WHERE ST_DWithin(location, ST_MakePoint(:lng, :lat)::geography, :radius)
            ORDER BY distance
        ");
        
        $stmt->execute([
            ':lat' => $params['lat'],
            ':lng' => $params['lng'],
            ':radius' => $params['radius'] ?? 5000
        ]);

        $locations = $stmt->fetchAll();

        $response->getBody()->write(json_encode([
            'success' => true,
            'data' => $locations
        ]));
        
        return $response->withHeader('Content-Type', 'application/json');

    } catch (PDOException $e) {
        error_log($e->getMessage());
        $response->getBody()->write(json_encode([
            'success' => false,
            'error' => 'Erro no banco de dados'
        ]));
        return $response->withStatus(500);
    }
});

$app->post('/locations', function (Request $request, Response $response) {
    $db = $this->get('db');
    $data = $request->getParsedBody();

    // Validação básica
    if (empty($data['name']) || empty($data['latitude']) || empty($data['longitude'])) {
        return $response->withStatus(400)->withJson(['error' => 'Dados incompletos']);
    }

    try {
        $stmt = $db->prepare("
            INSERT INTO locations (name, address, latitude, longitude, location)
            VALUES (:name, :address, :lat, :lng, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))
            RETURNING id
        ");

        $stmt->execute([
            ':name' => $data['name'],
            ':address' => $data['address'] ?? '',
            ':lat' => (float)$data['latitude'],
            ':lng' => (float)$data['longitude']
        ]);

        $locationId = $stmt->fetchColumn();
        
        return $response->withJson(['id' => $locationId], 201);

    } catch (PDOException $e) {
        error_log("Erro ao criar local: " . $e->getMessage());
        return $response->withStatus(500)->withJson(['error' => 'Falha ao cadastrar local']);
    }
});
