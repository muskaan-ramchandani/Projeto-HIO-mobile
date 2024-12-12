<?php
// Configurações do banco de dados
$servername = "127.0.0.1:3306";
$username = "u740411060_user";
$password = "OWYzZ?o2";
$dbname = "u740411060_hio";

// Conexão com o banco de dados
try {
    $pdo = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    echo "<p>Erro ao conectar ao banco de dados: " . htmlspecialchars($e->getMessage()) . "</p>";
    exit;
}

// Consulta para obter os dados
$sql = "SELECT * FROM PerguntasForum ORDER BY dataPublicacao DESC";
$stmt = $pdo->query($sql);

// Verifica se há dados para exibir
if ($stmt->rowCount() > 0) {
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        $id = htmlspecialchars($row['id']);
        $emailAluno = htmlspecialchars($row['emailAluno']);
        $titulo = htmlspecialchars($row['titulo']);
        $pergunta = htmlspecialchars($row['pergunta']);
        $dataPublicacao = htmlspecialchars(date("d/m/Y", strtotime($row['dataPublicacao'])));
        $siglaOlimpiadaRelacionada = htmlspecialchars($row['siglaOlimpiadaRelacionada']);

        // HTML para exibir cada pergunta
        echo "
        <div class=\"retangulo\">
            <img src=\"iconePerfilVazioRedonda.png\" alt=\"Imagem perfil\" class=\"top-left-image\">
            <div class=\"retangulo-texto-container\">
                <div class=\"retangulo-texto\">$titulo</div>
                <div class=\"usuario-texto\">$emailAluno</div>
            </div>
            <div class=\"retangulo-pergunta\">
                $pergunta
            </div>
 <div class=\"retangulo-resposta\">
           Respostas <span onclick=\"exibirRespostas('$id')\">Clique aqui para exibir</span>
        </div>



            <div class=\"retangulo-botao-container\">
                <button 
                    class=\"retangulo-botao\" 
                    onclick=\"abrirModal('$id', '$titulo', '$pergunta')\"
                >
                    Responder pergunta
                </button>
            </div>
            <div class=\"retangulo-data\">$dataPublicacao • $siglaOlimpiadaRelacionada</div>
        </div>";
    }
} else {
    echo "<p>Nenhuma pergunta encontrada no fórum.</p>";
}

// Função para buscar respostas com base no id da pergunta
function obterRespostas($pdo, $idPergunta) {
    $sql = "SELECT emailProf, resposta, dataResposta FROM RespostasForum WHERE idPergunta = :idPergunta ORDER BY dataResposta DESC";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':idPergunta', $idPergunta);
    $stmt->execute();
    return $stmt->fetchAll(PDO::FETCH_ASSOC);
}
?>

<!-- Modal -->
<div id="responderModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="fecharModal()">&times;</span>
        <h2>Responder Pergunta</h2>
        <p class="pergunta-title-modal">Pergunta:</p>
        <p id="modalPerguntaTexto"></p>
        <form id="formResposta" method="POST" action="processarResposta.php">
            <input type="hidden" name="idPergunta" id="modalIdPergunta">
            <input type="hidden" name="emailProf" id="modalEmailProf" value="<?php echo isset($_GET['email']) ? htmlspecialchars($_GET['email']) : ''; ?>"> 
            <textarea name="resposta" id="campoEntrada" placeholder="Digite aqui sua resposta ..." required></textarea>
            <div class="botao-container">
                <button type="submit" id="responderBtnModal">Responder</button>
                <button type="button" id="cancelarBtnModal" onclick="fecharModal()">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal de Respostas -->
<div id="exibirRespostasModal" class="modal">
    <div class="modal-content">
        <span class="close-btn" onclick="fecharModalRespostas()">&times;</span>
        <h2>Respostas dos Professores</h2>
        <div id="respostasConteudo">
            <!-- As respostas serão carregadas aqui -->
        </div>
    </div>
</div>


<!-- JavaScript -->
<script>
    // Função para abrir o modal e preencher com os dados da pergunta
    function abrirModal(id, titulo, pergunta) {
        document.getElementById('modalIdPergunta').value = id;
        document.getElementById('modalPerguntaTexto').textContent = pergunta;
        document.getElementById('responderModal').style.display = 'block';
    }

    // Função para fechar o modal
    function fecharModal() {
        document.getElementById('responderModal').style.display = 'none';
    }

    // Fecha o modal se o usuário clicar fora do conteúdo
    window.onclick = function (event) {
        const modal = document.getElementById('responderModal');
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    };
    
    // Função para abrir o modal de respostas
function exibirRespostas(idPergunta) {
    const modal = document.getElementById('exibirRespostasModal');
    const respostasConteudo = document.getElementById('respostasConteudo');

    // Limpar conteúdo anterior
    respostasConteudo.innerHTML = '<p>Carregando...</p>';

    // Fazer uma requisição para buscar as respostas
    fetch(`buscarRespostas.php?idPergunta=${idPergunta}`)
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                respostasConteudo.innerHTML = '<p>Nenhuma resposta encontrada.</p>';
            } else {
                respostasConteudo.innerHTML = data.map(resposta => `
                    <div class="resposta-item">
                        <strong>${resposta.emailProf}</strong> - ${new Date(resposta.dataResposta).toLocaleDateString('pt-BR')}
                        <p>${resposta.resposta}</p>
                    </div>
                `).join('');
            }
        })
        .catch(error => {
            respostasConteudo.innerHTML = '<p>Erro ao carregar as respostas.</p>';
            console.error('Erro:', error);
        });

    modal.style.display = 'block';
}

// Função para fechar o modal de respostas
function fecharModalRespostas() {
    document.getElementById('exibirRespostasModal').style.display = 'none';
}

</script>
