<?php
function validateLocationData(array $data) {
    $errors = [];

    if (empty($data['name'])) {
        $errors['name'] = 'Nome do local é obrigatório';
    }

    if (empty($data['latitude']) || !is_numeric($data['latitude'])) {
        $errors['latitude'] = 'Latitude inválida';
    }

    if (empty($data['longitude']) || !is_numeric($data['longitude'])) {
        $errors['longitude'] = 'Longitude inválida';
    }

    return $errors;
}