<?php
header("Content-Type: application/json; charset=UTF-8");

require_once 'db.php';

$requestMethod = $_SERVER['REQUEST_METHOD'];

switch ($requestMethod) {
    case 'GET':
        if (isset($_GET['entity'])) {
            switch ($_GET['entity']) {
                case 'user':
                    if (isset($_GET['id'])) getUser($pdo, $_GET['id']);
                    else getUsers($pdo);
                    break;
                case 'pet':
                    if (isset($_GET['id'])) getPet($pdo, $_GET['id']);
                    else getPets($pdo);
                    break;
                case 'location':
                    if (isset($_GET['id'])) getLocation($pdo, $_GET['id']);
                    else getLocations($pdo);
                    break;
                case 'review':
                    if (isset($_GET['id'])) getReview($pdo, $_GET['id']);
                    else getReviews($pdo);
                    break;
                case 'favorite':
                    if (isset($_GET['id'])) getFavorite($pdo, $_GET['id']);
                    else getFavorites($pdo);
                    break;
                case 'adoption':
                    if (isset($_GET['id'])) getAdoptionAnimal($pdo, $_GET['id']);
                    else getAdoptionAnimals($pdo);
                    break;
            }
        }
        break;

    case 'POST':
        if (isset($_GET['entity'])) {
            switch ($_GET['entity']) {
                case 'user':
                    addUser($pdo);
                    break;
                case 'pet':
                    addPet($pdo);
                    break;
                case 'location':
                    addLocation($pdo);
                    break;
                case 'review':
                    addReview($pdo);
                    break;
                case 'favorite':
                    addFavorite($pdo);
                    break;
                case 'adoption':
                    addAdoptionAnimal($pdo);
                    break;
            }
        }
        break;

    case 'PUT':
        if (isset($_GET['entity'])) {
            switch ($_GET['entity']) {
                case 'user':
                    updateUser($pdo);
                    break;
                case 'pet':
                    updatePet($pdo);
                    break;
                case 'location':
                    updateLocation($pdo);
                    break;
                case 'review':
                    updateReview($pdo);
                    break;
                case 'favorite':
                    updateFavorite($pdo);
                    break;
                case 'adoption':
                    updateAdoptionAnimal($pdo);
                    break;
            }
        }
        break;

    case 'DELETE':
        if (isset($_GET['entity'])) {
            switch ($_GET['entity']) {
                case 'user':
                    deleteUser($pdo);
                    break;
                case 'pet':
                    deletePet($pdo);
                    break;
                case 'location':
                    deleteLocation($pdo);
                    break;
                case 'review':
                    deleteReview($pdo);
                    break;
                case 'favorite':
                    deleteFavorite($pdo);
                    break;
                case 'adoption':
                    deleteAdoptionAnimal($pdo);
                    break;
            }
        }
        break;

    default:
        echo json_encode(["error" => "Método não permitido"]);
        break;
}

// Funções de CRUD para Usuários
function getUsers($pdo) {
    $sql = "SELECT * FROM users";
    $stmt = $pdo->query($sql);
    $users = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($users);
}

function getUser($pdo, $id) {
    $sql = "SELECT * FROM users WHERE id = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$id]);
    $user = $stmt->fetch(PDO::FETCH_ASSOC);
    echo json_encode($user ? $user : ["error" => "Usuário não encontrado"]);
}

function addUser($pdo) {
    $data = json_decode(file_get_contents("php://input"));
    if (!isset($data->name) || !isset($data->email) || !isset($data->password)) {
        echo json_encode(["error" => "Dados incompletos"]);
        return;
    }
    $name = $data->name;
    $email = $data->email;
    $password = password_hash($data->password, PASSWORD_BCRYPT);
    $sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$name, $email, $password]);
    echo json_encode(["message" => "Usuário adicionado com sucesso!"]);
}

// Funções de CRUD para Locais
function getLocations($pdo) {
    $sql = "SELECT * FROM locations";
    $stmt = $pdo->query($sql);
    $locations = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($locations);
}

function getLocation($pdo, $id) {
    $sql = "SELECT * FROM locations WHERE id = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$id]);
    $location = $stmt->fetch(PDO::FETCH_ASSOC);
    echo json_encode($location ? $location : ["error" => "Local não encontrado"]);
}

function addLocation($pdo) {
    $data = json_decode(file_get_contents("php://input"));
    if (!isset($data->name) || !isset($data->address) || !isset($data->latitude) || !isset($data->longitude)) {
        echo json_encode(["error" => "Dados incompletos"]);
        return;
    }
    $name = $data->name;
    $address = $data->address;
    $phone = isset($data->phone) ? $data->phone : null;
    $description = isset($data->description) ? $data->description : null;
    $latitude = $data->latitude;
    $longitude = $data->longitude;
    $location = "POINT($longitude $latitude)"; // GeoJSON para o ponto de geolocalização
    $sql = "INSERT INTO locations (name, address, phone, description, latitude, longitude, location) 
            VALUES (?, ?, ?, ?, ?, ?, ST_GeomFromText(?))";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$name, $address, $phone, $description, $latitude, $longitude, $location]);
    echo json_encode(["message" => "Local adicionado com sucesso!"]);
}

// Funções de CRUD para Avaliações
function getReviews($pdo) {
    $sql = "SELECT * FROM reviews";
    $stmt = $pdo->query($sql);
    $reviews = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($reviews);
}

function getReview($pdo, $id) {
    $sql = "SELECT * FROM reviews WHERE id = ?";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$id]);
    $review = $stmt->fetch(PDO::FETCH_ASSOC);
    echo json_encode($review ? $review : ["error" => "Avaliação não encontrada"]);
}

function addReview($pdo) {
    $data = json_decode(file_get_contents("php://input"));
    if (!isset($data->user_id) || !isset($data->location_id) || !isset($data->rating)) {
        echo json_encode(["error" => "Dados incompletos"]);
        return;
    }
    $user_id = $data->user_id;
    $location_id = $data->location_id;
    $rating = $data->rating;
    $comment = isset($data->comment) ? $data->comment : null;
    $sql = "INSERT INTO reviews (user_id, location_id, rating, comment) 
            VALUES (?, ?, ?, ?)";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$user_id, $location_id, $rating, $comment]);
    echo json_encode(["message" => "Avaliação adicionada com sucesso!"]);
}

// Funções de CRUD para Favoritos
function getFavorites($pdo) {
    $sql = "SELECT * FROM favorites";
    $stmt = $pdo->query($sql);
    $favorites = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($favorites);
}

function addFavorite($pdo) {
    $data = json_decode(file_get_contents("php://input"));
    if (!isset($data->user_id) || !isset($data->location_id)) {
        echo json_encode(["error" => "Dados incompletos"]);
        return;
    }
    $user_id = $data->user_id;
    $location_id = $data->location_id;
    $sql = "INSERT INTO favorites (user_id, location_id) VALUES (?, ?)";
    $stmt = $pdo->prepare($sql);
    $stmt->execute([$user_id, $location_id]);
    echo json_encode(["message" => "Local favoritado com sucesso!"]);
}

// Funções de CRUD para Animais de Adoção
function getAdoptionAnimals($pdo) {
    $sql = "SELECT * FROM adoption_animals";
    $stmt = $pdo->query($sql);
    $animals = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($animals);
}

$app->post('/favorites', function (Request $request, Response $response) {
    $data = $request->getParsedBody();
    $userId = $request->getAttribute('user_id'); // Obtido do middleware de autenticação
    
    // Validação básica
    if (empty($data['location_id'])) {
        return $response->withStatus(400)->withJson(['error' => 'location_id é obrigatório']);
    }

    $db = $this->get('db'); // Assumindo que o PDO está no container DI
    try {
        $stmt = $db->prepare("INSERT INTO favorites (user_id, location_id) VALUES (?, ?)");
        $stmt->execute([$userId, $data['location_id']]);
        
        return $response->withJson(['success' => true]);
    } catch (PDOException $e) {
        return $response->withStatus(500)->withJson(['error' => 'Erro ao favoritar: ' . $e->getMessage()]);
    }
});
