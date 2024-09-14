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
