<?php
use Slim\Http\Request;
use Slim\Http\Response;

// Função para enviar notificação via FCM
function sendNotification($userId, $message) {
    $db = $this->get('db'); // Acesso ao banco de dados
    $tokens = [];

    // 1. Busca os tokens FCM do usuário no banco de dados
    $stmt = $db->prepare("SELECT fcm_token FROM user_tokens WHERE user_id = ?");
    $stmt->execute([$userId]);
    $tokens = $stmt->fetchAll(PDO::FETCH_COLUMN);

    // 2. Monta o payload da notificação
    $payload = [
        'registration_ids' => $tokens,
        'notification' => [
            'title' => 'Pet Connect',
            'body' => $message,
            'icon' => 'ic_notification'
        ],
        'data' => [
            'click_action' => 'OPEN_LOCATIONS_ACTIVITY' // Ação personalizada no app
        ]
    ];

    // 3. Envia para o FCM
    $ch = curl_init('https://fcm.googleapis.com/fcm/send');
    curl_setopt($ch, CURLOPT_HTTPHEADER, [
        'Authorization: key=' . $_ENV['FCM_SERVER_KEY'], // Chave no .env
        'Content-Type: application/json'
    ]);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($payload));
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    $response = curl_exec($ch);
    curl_close($ch);

    return json_decode($response, true);
}

// Rota para teste (opcional)
$app->post('/send-test-notification', function (Request $request, Response $response) {
    $data = $request->getParsedBody();
    $result = sendNotification($data['user_id'], $data['message']);
    return $response->withJson($result);
});