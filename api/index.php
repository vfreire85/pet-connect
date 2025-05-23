<?php
// /var/www/html/api/index.php

require __DIR__ . '/vendor/autoload.php';
require __DIR__ . '/db.php'; // Conexão com o banco

use Slim\Factory\AppFactory;

$app = AppFactory::create();

// Adiciona o PDO no container do Slim
$app->getContainer()->set('db', function() {
    return require __DIR__ . '/db.php';
});

// Middlewares
$app->addBodyParsingMiddleware();
$app->addErrorMiddleware(true, true, true);

// Rotas
require __DIR__ . '/auth.php';
require __DIR__ . '/locations.php';
require __DIR__ . '/notifications.php';
require __DIR__ . '/reviews.php';

$app->add(function ($request, $handler) {
    $response = $handler->handle($request);
    return $response
        ->withHeader('Access-Control-Allow-Origin', '*')
        ->withHeader('Access-Control-Allow-Headers', 'Content-Type');
});

$app->run();
