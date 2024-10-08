<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Helper in Olympics</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap">
    <link rel="stylesheet" href="TelaEventoOlimpiada.css">
</head>
<body>


        <div class="botao-voltar-container">
            <img src="C:/Users/Muskaan Ramchandani/Projeto-HIO-mobile/Imagens Mobile HIO/btnVoltarBRANCO.png" alt="Botão Voltar" class="botao-voltar">
            <div class="botao-voltar-texto">Voltar</div>
        </div>
    </div>


    <!-- Botoes do calendario -->
<div class="botao-container">
    <img src="Imagens_Mobile_HIO/btnVoltarEAvancarExercicioEsquerda.png" alt="Voltar" class="botao-calendario" onclick="voltarMes()">
    <img src=Imagens_Mobile_HIO/btnVoltarEAvancarExercicioDireita.png" alt="Avançar" class="botao-calendario" onclick="avancarMes()">
</div>


   <!-- Eventos -->
   <div class="eventos">
    Eventos
    <button class="botao-adicionar" onclick="openModal()">Adicionar eventos</button>
    </div>


    <!-- Modal -->
 <div id="modal" class="modal">
    <div class="modal-content">
        <h2 class="modal-title">Adicionar evento em OBF</h2>
        <form>
            <label class="label">Tipo de evento:</label>
            <input type="text" class="input" placeholder="Ex: Inscrição, prova da fase 1">
           
            <label class="label">Data:</label>
            <input type="date" class="input">
           
            <label class="label">Horário (começo e fim):</label>
            <input type="text" class="input" placeholder="Ex: 14:00 - 16:00">
           
            <label class="label">Link (em caso de avaliação online ou inscrição):</label>
            <input type="url" class="input" placeholder="Ex: http://example.com">
           
            <button type="submit" class="submit-btn">Adicionar Evento</button>
            <button type="button" class="cancel-btn" onclick="closeModal()">Cancelar</button>


        </form>
    </div>
</div>


     <!-- Retângulo com barra de rolagem -->
     <div class="retangulo">
        <!-- Conteúdo repetido -->
        <div class="conteudo data">22 de setembro, 2024</div>
        <div class="conteudo inscricao">Inscrição</div>
        <div class="conteudo horario"><strong>Horário:</strong> 10:00 AM</div>
        <div class="conteudo link"><strong>Link:</strong> https://link_para_inscricao</div>
        <div class="linha-separadora"></div>


        <div class="conteudo data">22 de setembro, 2024</div>
        <div class="conteudo inscricao">Inscrição</div>
        <div class="conteudo horario"><strong>Horário:</strong> 10:00 AM</div>
        <div class="conteudo link"><strong>Link:</strong>  https://link_para_inscricao</div>
        <div class="linha-separadora"></div>


        <div class="conteudo data">22 de setembro, 2024</div>
        <div class="conteudo inscricao">Primeira fase</div>
        <div class="conteudo horario"><strong>Horário:</strong> 10:00 AM</div>
        <div class="conteudo link"><strong>Link:</strong>  https://link_para_inscricao</div>
        <div class="linha-separadora"></div>


        <div class="conteudo data">22 de setembro, 2024</div>
        <div class="conteudo inscricao">Resultado da primeira fase</div>
        <div class="conteudo horario"><strong>Horário:</strong> 10:00 AM</div>
        <div class="conteudo link"><strong>Link:</strong> https://link_para_inscricao</div>
        <div class="linha-separadora"></div>


        <div class="conteudo data">22 de setembro, 2024</div>
        <div class="conteudo inscricao">Inscrição</div>
        <div class="conteudo horario"><strong>Horário:</strong> 10:00 AM</div>
        <div class="conteudo link"><strong>Link:</strong>  https://link_para_inscricao</div>
        <div class="linha-separadora"></div>
    </div>


    <!--calendario-->
    <div class="wrapper">
        <div class="days-week">
            <span>Segunda</span>
            <span>Terça</span>
            <span>Quarta</span>
            <span>Quinta</span>
            <span>Sexta</span>
            <span>Sabado</span>
            <span>Domingo</span>
        </div>


    <div class="number-days">
        <span class="mes-anterior">30</span>
        <span class="mes-anterior">31</span>
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


     </script>
   
</body>
</html>