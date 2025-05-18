<?php
use Slim\Http\Request;
use Slim\Http\Response;

$app->get('/locations/search', function (Request $request, Response $response) {
    $params = $request->getQueryParams();
    $db = $this->get('db'); // Assumindo que o PDO está disponível
    $query = "SELECT * FROM locations WHERE 1=1";

    // Inicializa $filters como array vazio
    $filters = [];
    $queryParams = [];

    // Filtro por horário (ex: locais abertos agora)
    if (isset($params['open_now'])) {
        $currentTime = date('H:i:s');
        $query .= " AND opening_hours->>'open' <= '$currentTime' 
                   AND opening_hours->>'close' >= '$currentTime'";
    }

    // Filtro por tipo (veterinary ou adoption)
    if (isset($params['type'])) {
        $query .= " AND location_type = :type";
    }

    // Query base
    $query = "
        SELECT 
            id, name, address, latitude, longitude, 
            location_type, phone, email, website,
            ST_Distance(
                geo_point, 
                ST_MakePoint(:lng, :lat)::geography
            ) AS distance
        FROM locations
        WHERE 1=1
    ";

    // Filtro de geolocalização (lat, lng, radius)
    if (isset($params['lat']) && isset($params['lng']) && isset($params['radius'])) {
        $query .= " AND ST_DWithin(
            geo_point,
            ST_MakePoint(:lng, :lat)::geography,
            :radius
        )";
        $queryParams[':lat'] = (float)$params['lat'];
        $queryParams[':lng'] = (float)$params['lng'];
        $queryParams[':radius'] = (int)$params['radius'];
    }

    // Filtro por tipo (veterinary ou adoption)
    if (isset($params['type']) && in_array($params['type'], ['veterinary', 'adoption'])) {
        $query .= " AND location_type = :type";
        $queryParams[':type'] = $params['type'];
    }

    // Ordenação por distância (se coordenadas existirem)
    if (isset($params['lat']) && isset($params['lng'])) {
        $query .= " ORDER BY distance ASC";
    }

    // Executa a consulta
    try {
        $stmt = $db->prepare($query);
        $stmt->execute($queryParams);
        $locations = $stmt->fetchAll(PDO::FETCH_ASSOC);

        return $response->withJson([
            'success' => true,
            'data' => $locations
        ]);
    } catch (PDOException $e) {
        return $response->withStatus(500)->withJson([
            'success' => false,
            'error' => 'Erro no banco de dados: ' . $e->getMessage()
        ]);
    }
});