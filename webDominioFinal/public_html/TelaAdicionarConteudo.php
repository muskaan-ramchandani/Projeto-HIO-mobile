<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Recuperando os dados enviados pelo formulário
    $titulo = $_POST['titulo'] ?? '';
    $subtitulo = $_POST['subtitulo'] ?? '';
    $sigla = $_POST['sigla'] ?? '';
  $email = $_POST['email'] ?? '';
    // Validando os dados
    if (empty($titulo) || empty($subtitulo) || empty($sigla)) {
        echo "<script>alert('Todos os campos devem ser preenchidos.'); window.history.back();</script>";
        exit;
    }

    try {
        // Conexão com o banco de dados
        $pdo = new PDO('mysql:host=127.0.0.1:3306;dbname=u740411060_hio;charset=utf8', 'u740411060_user', 'OWYzZ?o2');
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        // Inserir no banco de dados
        $sql = "INSERT INTO Conteudo (titulo, subtitulo, siglaOlimpiadaPertencente) VALUES (:titulo, :subtitulo, :sigla)";
        $stmt = $pdo->prepare($sql);
        
        // Vinculando os parâmetros
        $stmt->bindParam(':titulo', $titulo);
        $stmt->bindParam(':subtitulo', $subtitulo);
        $stmt->bindParam(':sigla', $sigla);
        
        // Executando a inserção
        $stmt->execute();

        // Exibindo uma mensagem de sucesso
               echo "<script>
                alert('Conteúdo adicionado com sucesso!');
                window.location.href = 'TelaOlimpiadaProfessorHTML.php?email=" . urlencode($email) . "&sigla=" . urlencode($sigla) . "';
              </script>";

    } catch (PDOException $e) {
        // Caso ocorra um erro, exibe a mensagem de erro
        echo "<script>alert('Erro ao adicionar conteúdo: " . $e->getMessage() . "'); window.history.back();</script>";
    }
}
?>
