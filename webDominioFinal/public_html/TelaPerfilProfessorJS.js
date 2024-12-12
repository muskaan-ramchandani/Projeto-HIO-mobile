function toggleMenu() {
    var menuContent = document.getElementById('menuContent');
    var menuIcon = document.querySelector('.menu-icon');
    var helperText = document.getElementById('helperText');




    if (menuContent.style.display === 'block') {
        menuContent.style.display = 'none';
        menuIcon.classList.remove('active');
        helperText.style.display = 'none';
    } else {
        menuContent.style.display = 'block';
        menuIcon.classList.add('active');
        helperText.style.display = 'inline';
    }
}

// Obtém o email da URL (se presente)
        const urlParams = new URLSearchParams(window.location.search);
        const email = urlParams.get('email');

        // Verifica se o email foi fornecido na URL
        if (email) {
            // Faz a requisição ao arquivo PHP usando fetch
            fetch('dados.php?email=' + encodeURIComponent(email))
                .then(response => response.json()) // Converte a resposta para JSON
                .then(data => {
                    if (data && data.labels && data.quantidades) {
                        const ctx = document.getElementById('myChart').getContext('2d');
                        const myChart = new Chart(ctx, {
                            type: 'bar', // Tipo de gráfico
                            data: {
                                labels: data.labels, // Labels do eixo X
                                datasets: [{
                                    label: 'Sua contribuição', // Rótulo do conjunto de dados
                                    data: data.quantidades, // Dados do gráfico
                                    backgroundColor: [
                                        '#5271ff', // Cor para Provas anteriores
                                        '#cb6ce6', // Cor para Flashcards
                                        '#f1c40f', // Cor para Vídeos recomendados
                                        '#835ad2', // Cor para Livros
                                        '#e74c3c'  // Cor para Questionários
                                    ],
                                    borderColor: [
                                        '#5271ff', '#cb6ce6', '#f1c40f', '#835ad2', '#e74c3c'
                                    ],
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true // Começar o eixo Y em zero
                                    }
                                },
                                plugins: {
                                    tooltip: {
                                        enabled: true // Habilitar tooltip ao passar o mouse
                                    }
                                }
                            }
                        });
                    } else {
                        alert("Nenhum dado encontrado para o email informado.");
                    }
                })
                .catch(error => {
                    console.error("Erro ao carregar dados:", error);
                    alert("Ocorreu um erro ao carregar os dados.");
                });
        } else {
            alert("Email não fornecido na URL.");
        }
