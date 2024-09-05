<?php
    header('Content-Type: application/json');
    header('Character-Encoding: utf-8');

    $json_entrada = json_decode(file_get_contents('php://input'));

    $email = $json_entrada->{'email'};

    try {
        $pdo = new PDO('mysql:host=localhost;dbname=hio;port=3306;charset=utf8', 'root', 'root');
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = 'SELECT COUNT(*) AS count FROM Aluno WHERE email = :email';
        $statement = $pdo->prepare($sql);
        $statement->bindValue(':email', $email, PDO::PARAM_STR);
        $statement->execute();

        $result = $statement->fetch(PDO::FETCH_ASSOC);
        $emailExiste = $result['count'] > 0;

        echo json_encode(array("emailExiste" => $emailExiste));

    } catch (PDOException $e) {
        // Retornando erro em caso de falha
        echo json_encode(array("error" => $e->getMessage()));
    }
?>
