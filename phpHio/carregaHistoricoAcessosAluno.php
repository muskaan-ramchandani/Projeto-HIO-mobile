<?php
header('Content-Type: application/json');
header('Character-Encoding: utf-8');

$servername = "localhost"; 
$username = "root";        
$password = "root";            
$dbname = "hio";      

try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo json_encode(["message" => "Erro na conexão com o banco de dados"]);
    exit;
}

$emailAluno = $_GET['emailAluno'] ?? '';

if (empty($emailAluno)) {
    echo json_encode(["message" => "Não foi possível detectar o aluno"]);
    exit;
}

$resultados = [];

$queries = [
    "provas" => "
        SELECT p.id, p.anoDaProva, p.estado, p.fase, p.profQuePostou, p.siglaOlimpiadaPertencente, p.arquivoPdf
        FROM ProvaAnterior p
        JOIN HistoricoAcessoAluno h ON p.id = h.idMaterial AND h.tipoMaterial = 'ProvaAnterior'
        WHERE h.emailAluno = :emailAluno",
    "textos" => "
        SELECT t.id, t.titulo, t.texto, t.profQuePostou, t.idConteudoPertencente
        FROM Texto t
        JOIN HistoricoAcessoAluno h ON t.id = h.idMaterial AND h.tipoMaterial = 'Texto'
        WHERE h.emailAluno = :emailAluno",
    "videos" => "
        SELECT v.id, v.capa, v.titulo, v.link, v.profQuePostou, v.idConteudoPertencente
        FROM Video v
        JOIN HistoricoAcessoAluno h ON v.id = h.idMaterial AND h.tipoMaterial = 'Video'
        WHERE h.emailAluno = :emailAluno",
    "flashcards" => "
        SELECT f.id, f.imagem, f.titulo, f.texto, f.profQuePostou, f.idConteudoPertencente
        FROM Flashcard f
        JOIN HistoricoAcessoAluno h ON f.id = h.idMaterial AND h.tipoMaterial = 'Flashcard'
        WHERE h.emailAluno = :emailAluno",
    "questionarios" => "
        SELECT q.id, q.titulo, q.profQuePostou, q.idConteudoPertencente
        FROM Questionario q
        JOIN HistoricoAcessoAluno h ON q.id = h.idMaterial AND h.tipoMaterial = 'Questionario'
        WHERE h.emailAluno = :emailAluno"
];

foreach ($queries as $tipo => $sql) {
    $statement = $pdo->prepare($sql);
    $statement->bindParam(':emailAluno', $emailAluno);
    $statement->execute();

    $dados = [];
    while ($result = $statement->fetch(PDO::FETCH_ASSOC)) {
        if ($tipo === "videos") {
            if (isset($result['capa'])) {
                $result['capa'] = base64_encode($result['capa']);
            }
        }
        if ($tipo === "flashcards") {
            if (isset($result['imagem'])) {
                $result['imagem'] = base64_encode($result['imagem']);
            }
        }
        if ($tipo === "provas") {
            if (isset($result['arquivoPdf'])) {
                $result['arquivoPdf'] = base64_encode($result['arquivoPdf']);
            }
        }
        $dados[] = (object) $result;
    }
    $resultados[$tipo] = $dados;
}

echo json_encode($resultados);

$pdo = null;
?>
