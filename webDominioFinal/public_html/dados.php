<?php
header('Content-Type: application/json');
session_start();

// Informações do banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

try {
    // Conectar ao banco de dados com PDO
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Verifica se o email foi passado via GET
    if (isset($_GET['email'])) {
        $email = $_GET['email'];

        // Consultar as quantidades para cada tipo de conteúdo baseado no email
        $stmt = $pdo->prepare("
            SELECT
                (SELECT COUNT(*) FROM ProvaAnterior WHERE profQuePostou = :email) AS provas,
                (SELECT COUNT(*) FROM Flashcard WHERE profQuePostou = :email) AS flashcards,
                (SELECT COUNT(*) FROM Video WHERE profQuePostou = :email) AS videos,
                (SELECT COUNT(*) FROM Texto WHERE profQuePostou = :email) AS textos,
                (SELECT COUNT(*) FROM Questionario WHERE profQuePostou = :email) AS questionarios
        ");
        $stmt->execute(['email' => $email]);

        // Armazenar os resultados
        $dados = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($dados) {
            echo json_encode([
                'labels' => ['Provas anteriores', 'Flashcards', 'Vídeos recomendados', 'Textos', 'Questionários'],
                'quantidades' => [
                    $dados['provas'],
                    $dados['flashcards'],
                    $dados['videos'],
                    $dados['textos'],
                    $dados['questionarios']
                ]
            ]);
        } else {
            echo json_encode([]); // Retorna um array vazio se não encontrar dados
        }
    } else {
        echo json_encode(['error' => 'Email não fornecido']);
    }
} catch (PDOException $e) {
    echo json_encode(['error' => $e->getMessage()]); // Retorna o erro em formato JSON
}
?>
