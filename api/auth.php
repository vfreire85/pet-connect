use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

$app->post('/login', function (Request $request, Response $response) {
    $db = $this->get('db');
    $data = $request->getParsedBody();

    // Busca o usuário no PostgreSQL
    $stmt = $db->prepare("SELECT id, password FROM users WHERE email = ?");
    $stmt->execute([$data['email']]);
    $user = $stmt->fetch();

    // Verifica a senha (use password_hash() no cadastro)
    if (!$user || !password_verify($data['password'], $user['password'])) {
        return $response->withStatus(401)->withJson(['error' => 'Credenciais inválidas']);
    }

    // Gera um token simples (em produção, use JWT)
    $token = base64_encode(random_bytes(32));
    
    return $response->withJson([
        'token' => $token,
        'user_id' => $user['id']
    ]);
});
