<?php
// Conexão com o banco de dados
$host = '127.0.0.1:3306';
$dbname = 'u740411060_hio';
$username = 'u740411060_user';
$password = 'OWYzZ?o2';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<script>alert('Erro ao conectar ao banco de dados: " . addslashes($e->getMessage()) . "');</script>";
    exit;
}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Recuperar os dados enviados pelo formulário
    $tipo = $_POST['tipo'] ?? '';
    $dataOcorrencia = $_POST['dataOcorrencia'] ?? '';
    $horarioEvento = $_POST['horarioEvento'] ?? '';
    $link = $_POST['link'] ?? '';
    $sigla = $_POST['sigla'] ?? '';

    // Verificar se todos os campos obrigatórios foram preenchidos
    if (empty($tipo) || empty($dataOcorrencia) || empty($horarioEvento) || empty($sigla)) {
        echo "<script>alert('Preencha todos os campos obrigatórios.');</script>";
        exit;
    }

    // Dividir horário em início e fim
    $horarios = explode('-', $horarioEvento);
    $horarioComeco = isset($horarios[0]) ? trim($horarios[0]) : null;
    $horarioFim = isset($horarios[1]) ? trim($horarios[1]) : null;

    try {
        // Preparar a query para inserir os dados na tabela
        $sql = "INSERT INTO Eventos (tipo, dataOcorrencia, horarioComeco, horarioFim, link, siglaOlimpiadaPertencente)
                VALUES (:tipo, :dataOcorrencia, :horarioComeco, :horarioFim, :link, :sigla)";

        // Preparar a execução da query
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':tipo', $tipo);
        $stmt->bindParam(':dataOcorrencia', $dataOcorrencia);
        $stmt->bindParam(':horarioComeco', $horarioComeco);
        $stmt->bindParam(':horarioFim', $horarioFim);
        $stmt->bindParam(':link', $link);
        $stmt->bindParam(':sigla', $sigla);

        // Executar e verificar se a inserção foi bem-sucedida
        if ($stmt->execute()) {
            echo "<script>alert('Evento adicionado com sucesso!');</script>";
        } else {
            echo "<script>alert('Erro ao adicionar evento.');</script>";
        }
    } catch (PDOException $e) {
        echo "<script>alert('Erro ao adicionar evento: " . addslashes($e->getMessage()) . "');</script>";
    }
}
?>

<?php
function traduzirData($dataOcorrencia) {
    $meses = [
        'January' => 'janeiro',
        'February' => 'fevereiro',
        'March' => 'março',
        'April' => 'abril',
        'May' => 'maio',
        'June' => 'junho',
        'July' => 'julho',
        'August' => 'agosto',
        'September' => 'setembro',
        'October' => 'outubro',
        'November' => 'novembro',
        'December' => 'dezembro'
    ];

   
  $dia = date("d", strtotime($dataOcorrencia));
  $mes = strtoupper(date("F", strtotime($dataOcorrencia))); // Get month name in uppercase

  // Check if month exists in translation array (case-insensitive)
  if (array_key_exists($mes, array_change_key_case($meses, CASE_UPPER))) {
    $mesTraduzido = array_change_key_case($meses, CASE_UPPER)[$mes];
  } else {
    // Handle cases where month might not be in English (fallback)
    $mesTraduzido = $mes; // Use original month name
  }

  $ano = date("Y", strtotime($data));

  return $dia . " de " . $mesTraduzido . " de " . $ano;
}
?>