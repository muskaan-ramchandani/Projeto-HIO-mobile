<?php
session_start();

// Supomos que o código foi armazenado em uma variável de sessão
$codigoGerado = $_SESSION['codigoVerificacao'] ?? null;
$codigoInserido = $_POST['codigo'] ?? '';

if ($codigoGerado && $codigoInserido == $codigoGerado) {
    echo "Código de verificação correto!";
    // Realize a ação desejada (ex: redirecionar o usuário, permitir o acesso, etc.)
} else {
    echo "Código de verificação inválido. Tente novamente.";
}
?>
