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




//grafico
const ctx = document.getElementById('myChart').getContext('2d');




const myChart = new Chart(ctx, {
    type: 'bar', // Tipo de gráfico
    data: {
        labels: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho'], // Labels do eixo X
        datasets: [{
            label: 'Sua contribuição', // Rótulo do conjunto de dados
            data: [12, 19, 3, 5, 2, 3], // Dados do gráfico
            backgroundColor: [
                '#cb6ce6', // Cor para Janeiro
                '#5271ff', // Cor para Fevereiro
                '#ff914d', // Cor para Março
                '#18b9cd', // Cor para Abril
                '#835ad2', // Cor para Maio
                '#cb6ce6'  // Cor para Junho
            ],
            borderColor: [
                '#cb6ce6', // Borda para Janeiro
                '#5271ff', // Borda para Fevereiro
                '#ff914d', // Borda para Março
                '#18b9cd', // Borda para Abril
                '##835ad2', // Borda para Maio
                '#cb6ce6'  // Borda para Junho
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
