/**
 * ZakiBooking - Main Script (user-facing UI)
 */
document.addEventListener('DOMContentLoaded', function () {

    if (document.getElementById('main-slider')) {
        new Splide('#main-slider', {
            type: 'loop',
            perPage: 1,
            autoplay: true,
            interval: 4000,
            pauseOnHover: true,
        }).mount();
    }

    document.querySelectorAll('.alert.alert-dismissible').forEach(function (alert) {
        setTimeout(function () {
            var bsAlert = bootstrap.Alert.getOrCreateInstance(alert);
            if (bsAlert) bsAlert.close();
        }, 5000);
    });

    const navDock = document.querySelector('.premium-nav-dock');
    if (navDock) {
        window.addEventListener('scroll', function() {
            if (window.scrollY > 50) {
                navDock.classList.add('scrolled');
            } else {
                navDock.classList.remove('scrolled');
            }
        });
    }

    var currentPath = window.location.pathname;
    document.querySelectorAll('.premium-nav-dock .nav-link').forEach(function (link) {
        if (link.getAttribute('href') === currentPath) {
            link.classList.add('active');
        }
    });
});
