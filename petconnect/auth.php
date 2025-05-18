<?php
use Slim\Http\Request;
use Slim\Http\Response;

$authMiddleware = function (Request $request, Response $response, $next) {
    // Verificar se o token JWT está presente
    $token = $request->getHeaderLine('Authorization');
    
    if (empty($token)) {
        return $response->withStatus(401)->withJson(['error' => 'Token não fornecido']);
    }

    try {
        // Validar token (usando Firebase JWT ou similar)
        $decoded = JWT::decode($token, 'sua_chave_secreta', ['HS256']);
        $request = $request->withAttribute('user_id', $decoded->user_id);
    } catch (Exception $e) {
        return $response->withStatus(401)->withJson(['error' => 'Token inválido']);
    }

    // Se tudo OK, continuar
    return $next($request, $response);
};