 
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
    die("Erro ao conectar ao banco de dados: " . $e->getMessage());
}

// Recuperar a sigla da URL
$sigla = $_GET['sigla'] ?? '';

if (empty($sigla)) {
    echo "Sigla não especificada na URL.";
    exit;
}

// Consultar eventos relacionados à sigla
try {
    $sql = "SELECT dataOcorrencia, tipo, horarioComeco, horarioFim, link 
            FROM Eventos 
            WHERE siglaOlimpiadaPertencente = :sigla 
            ORDER BY dataOcorrencia, horarioComeco";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':sigla', $sigla);
    $stmt->execute();
    $eventos = $stmt->fetchAll(PDO::FETCH_ASSOC);
} catch (PDOException $e) {
    echo "Erro ao buscar eventos: " . $e->getMessage();
    exit;
}

?>

 
 
 
 
 
 
 <!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
    <link rel="stylesheet" href="TelaEventoOlimpiada.css">


</head>
<body>


 <?php
try {
    $pdo = new PDO($dsn, $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // Capturar a sigla da URL
    if (isset($_GET['sigla'])) {
        $sigla = $_GET['sigla'];

        // Buscar os dados da olimpíada correspondente
        $sql = "SELECT nome, icone, cor FROM Olimpiada WHERE sigla = :sigla";
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':sigla', $sigla);
        $stmt->execute();

        $olimpiada = $stmt->fetch(PDO::FETCH_ASSOC);
    } else {
        header('Location: TelaInicialProfessorHTML.php');
        exit();
    }
} catch (PDOException $e) {
    echo "Erro: " . $e->getMessage();
}
?>
<div class="barra">
    <div class="logo-container">
       <img src="iconeCalendario.png">
        <div class="text"><?php echo htmlspecialchars($olimpiada['nome']); ?> - <?php echo htmlspecialchars($sigla); ?></div>
    </div>
</div>


    <!-- Botoes do calendario -->
<div class="botao-container">
    <img src="btnVoltarBRANCO.png" alt="Voltar" class="botao-calendario" onclick="voltarMes()">
    <img src="btnVoltarEAvancarExercicioDireita.png" alt="Avançar" class="botao-calendario" onclick="avancarMes()">
</div>


   <!-- Eventos -->
   <div class="eventos">
    Eventos
    <button class="botao-adicionar" onclick="openModal()">Adicionar eventos</button>
    </div>


    <!-- Modal -->
<div id="modal" class="modal">
    <div class="modal-content">
        <h2 class="modal-title">    Adicionar evento na Olimpíada <?= htmlspecialchars($sigla) ?></h2>
        <form method="POST" action="TelaEventoOlimpiada.php">
            <!-- Campo oculto para enviar a sigla da Olimpíada -->
            <input type="hidden" name="sigla" value="<?= htmlspecialchars($_GET['sigla'] ?? '') ?>">

            <label class="label">Tipo de evento:</label>
            <input type="text" name="tipo" class="input" placeholder="Ex: Inscrição, prova da fase 1" required>
           
            <label class="label">Data:</label>
            <input type="date" name="dataOcorrencia" class="input" required>
           
            <label class="label">Horário (começo e fim):</label>
            <input type="text" name="horarioEvento" class="input" placeholder="Ex: 14:00 - 16:00" required>
           
            <label class="label">Link (em caso de avaliação online ou inscrição):</label>
            <input type="url" name="link" class="input" placeholder="Ex: http://example.com">
           
            <button type="submit" class="submit-btn">Adicionar Evento</button>
            <button type="button" class="cancel-btn" onclick="closeModal()">Cancelar</button>
        </form>
    </div>
</div>


   <div class="retangulo">
    <?php if (empty($eventos)): ?>
        <p>Nenhum evento encontrado para a sigla especificada.</p>
    <?php else: ?>
        <?php foreach ($eventos as $evento): ?>
            <div class="conteudo data">
                <?= strftime("%d de %B de %Y", strtotime($evento['dataOcorrencia'])) ?>
    </div>
    <div class="conteudo inscricao"><?= htmlspecialchars($evento['tipo']) ?></div>
    <div class="conteudo horario"><strong>Horário:</strong> <?= htmlspecialchars($evento['horarioComeco']) ?> - <?= htmlspecialchars($evento['horarioFim']) ?></div>
    <div class="conteudo link">
        <strong>Link:</strong> <a href="<?= htmlspecialchars($evento['link']) ?>" target="_blank"><?= htmlspecialchars($evento['link']) ?></a>
    </div>
    <div class="linha-separadora"></div>
<?php endforeach; ?>

    <?php endif; ?>
</div>


 <?php
 
try {
    $sql = "SELECT dataOcorrencia, tipo, horarioComeco, horarioFim, link 
            FROM Eventos 
            WHERE siglaOlimpiadaPertencente = :sigla";
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':sigla', $sigla);
    $stmt->execute();
    $eventos = $stmt->fetchAll(PDO::FETCH_ASSOC);

    // Enviar eventos para o JavaScript
    echo "<script>const eventos = " . json_encode($eventos) . ";</script>";
} catch (PDOException $e) {
    echo "Erro ao buscar eventos: " . $e->getMessage();
}

?>
<div class="wrapper">
    <div class="days-week">
        <span>Segunda</span>
        <span>Terça</span>
        <span>Quarta</span>
        <span>Quinta</span>
        <span>Sexta</span>
        <span>Sábado</span>
        <span>Domingo</span>
    </div>
    <div class="number-days">
        <!-- Os dias serão preenchidos dinamicamente -->
    </div>
</div>




   


    <!-- js do calendario-->
     <script>


        let el = document.querySelector('.number-days');
        for(i=0; i<=31; i++){
            el.innerHTML+='<span>'+i+'</span>';


        }


     // Função para abrir o modal
        function openModal() {
            document.getElementById("modal").style.display = "block";
        }


     // Função para fechar o modal
        window.onclick = function(event) {
            const modal = document.getElementById("modal");
            if (event.target === modal) {
                modal.style.display = "none";
            }
        };


        // Função para fechar o modal
    function closeModal() {
    document.getElementById("modal").style.display = "none";
    }

document.addEventListener('DOMContentLoaded', () => {
    const numberDaysContainer = document.querySelector('.number-days');

    // Obtém o mês e o ano atuais
    const today = new Date();
    const currentMonth = today.getMonth(); // Mês (0 a 11)
    const currentYear = today.getFullYear();

    // Função para obter o número de dias no mês
    function getDaysInMonth(month, year) {
        return new Date(year, month + 1, 0).getDate();
    }

    // Obter o primeiro dia do mês
    function getFirstDayOfMonth(month, year) {
        return new Date(year, month, 1).getDay();
    }

    // Preenche o calendário
    function renderCalendar() {
        numberDaysContainer.innerHTML = ''; // Limpar o conteúdo anterior

        const daysInMonth = getDaysInMonth(currentMonth, currentYear);
        const firstDay = getFirstDayOfMonth(currentMonth, currentYear);

        // Adicionar espaços para os dias do mês anterior
        for (let i = 0; i < firstDay; i++) {
            numberDaysContainer.innerHTML += '<span class="empty"></span>';
        }

        // Preencher os dias do mês
        for (let day = 1; day <= daysInMonth; day++) {
            const date = `${currentYear}-${String(currentMonth + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;

            // Verifica se a data tem evento
            const hasEvent = eventos.some(evento => evento.dataOcorrencia === date);

            const dayElement = document.createElement('span');
            dayElement.textContent = day;

            // Aplica a classe 'event-day' se houver evento
            dayElement.className = hasEvent ? 'event-day' : 'normal-day';

            if (hasEvent) {
                // Adicionar tooltip ou evento de clique
                const evento = eventos.find(evento => evento.dataOcorrencia === date);
                dayElement.title = `Evento: ${evento.tipo} - ${evento.horarioComeco} às ${evento.horarioFim}`;

                // Exibir um alert com os detalhes do evento ao clicar
                dayElement.onclick = () => {
                    alert(`Evento: ${evento.tipo}\nHorário: ${evento.horarioComeco} às ${evento.horarioFim}\nLink: ${evento.link}`);
                };
            }

            numberDaysContainer.appendChild(dayElement);
        }
    }

    renderCalendar();
});


     </script>
   
</body>
</html>