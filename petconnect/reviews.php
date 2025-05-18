<?php
use Slim\Http\Request;
use Slim\Http\Response;

// POST /reviews - Adicionar avaliação
$app->post('/reviews', function (Request $request, Response $response) {
    $data = $request->getParsedBody();
    $userId = $request->getAttribute('user_id'); // Do middleware de autenticação

    // Validação
    if (empty($data['location_id']) || empty($data['rating'])) {
        return $response->withStatus(400)->withJson(['error' => 'Dados incompletos']);
    }

    $db = $this->get('db');
    $stmt = $db->prepare("
        INSERT INTO reviews (user_id, location_id, rating, comment)
        VALUES (?, ?, ?, ?)
    ");
    $stmt->execute([$userId, $data['location_id'], $data['rating'], $data['comment'] ?? null]);

    return $response->withJson(['success' => true]);
})->add($authMiddleware);

// GET /locations/{id}/reviews - Listar avaliações de um local
$app->get('/locations/{id}/reviews', function (Request $request, Response $response, array $args) {
    $db = $this->get('db');
    $stmt = $db->prepare("
        SELECT r.*, u.name as user_name 
        FROM reviews r
        JOIN users u ON r.user_id = u.id
        WHERE r.location_id = ?
    ");
    $stmt->execute([$args['id']]);
    $reviews = $stmt->fetchAll(PDO::FETCH_ASSOC);

    return $response->withJson($reviews);
});