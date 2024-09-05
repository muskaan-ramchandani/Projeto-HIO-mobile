<?php
    header('Content-Type: application/json');
    header('Character-Encoding: utf-8');

    $json_entrada = json_decode(file_get_contents('php://input'));

    $nomeCompleto = $json_entrada->{'nomeCompleto'};
    $nomeUsuario = $json_entrada->{'nomeUsuario'};
    $email = $json_entrada->{'email'};
    $senha = $json_entrada->{'senha'};
    $confirmaSenha = $json_entrada->{'confirmaSenha'};

    try {
        $pdo = new PDO('mysql:host=192.168.1.11;dbname=hio;port=3306;charset=utf8','root','root');
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $sql = 'INSERT INTO Aluno (nomeCompleto, nomeUsuario, email, senha, confirmaSenha) VALUES (:nomeCompleto, :nomeUsuario, :email, :senha, :confirmaSenha)';
        $statement = $pdo->prepare($sql);

        $statement->bindValue(':nomeCompleto', $nomeCompleto, PDO::PARAM_STR);
        $statement->bindValue(':nomeUsuario', $nomeUsuario, PDO::PARAM_STR);
        $statement->bindValue(':email', $email, PDO::PARAM_STR);
        $statement->bindValue(':senha', $senha, PDO::PARAM_STR);
        $statement->bindValue(':confirmaSenha', $confirmaSenha, PDO::PARAM_STR);

        $statement->execute();

        $json_saida = array("email" => $email);
        echo json_encode($json_saida);

    } catch (PDOException $e) {
        echo json_encode(array("error" => $e->getMessage()));
    }
?>
