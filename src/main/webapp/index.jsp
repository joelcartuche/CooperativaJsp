<%-- 
    Document   : index.jsp
    Created on : 25 mar 2022, 12:24:33
    Author     : jede
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Cunstom css link -->
    <link rel="stylesheet" href="css/socioPage.css">

    <title>Cooperativa de Taxis - TaxoPel</title>
</head>

<body>
    <!-- #main container -->
    <div class="container">
        <!-- #HEADER SECTION -->
        <header>
            <nav class="navbar">

                <div class="logo">TAXOPEL</div>

                <button class="navbar-toggle-btn">
                    <span class="one"></span>
                    <span class="two"></span>
                    <span class="three"></span>
                </button>

                <ul class="navbar-nav">
                    <li><a href="#home">Home</a></li>
                    <li><a href="#service">Servicios</a></li>
                </ul>

                <a href="Login"><button class="btn-primary">Iniciar Sesión</button></a>

            </nav>
        </header>

        <main>
            <!-- #HOME SECTION -->
            <section class="home" id="home">
                <div class="home-img-box">
                    <img src="images/Piggybank_Outline.png" alt="Image for Easybank Banner" class="home-img" width="100%">
                </div>
                <div class="wrapper">
                    <h1 class="home-title"> Caja de Ahorro y Crédito TAXOPEL
                    </h1>
                    <p class="home-text">
                        Su vida financiera con TAXOPEL, será una ventanilla única para ahorrar, invertir y mucho más.
                    </p>
                    <a href="Login"><button class="btn-primary">Iniciar Sesión</button></a>
                </div>
            </section>

            <!-- #SERVICE SECTION -->
            <section class="service" id="service">
                <h2 class="section-title">¿Por qué elegir TAXOPEL?</h2>
                <p class="section-text">
                    Brindamos servicios de calidad a la ciudadanía, apliando nuestra covertura de servicios y pormoviendo la inclusión financiera, 
                    a través de una gestión moderna y auto-sostenible.
                </p>
                <div class="service-card-group">

                    <div class="service-card">
                        <img src="images/icon-online.svg" alt="Icon for online banking" class="card-icon">
                        <h3 class="card-title">Banca en línea</h3>
                        <p class="card-text">
                            Nuestra moderna aplicacione web le permiten realizar un seguimiento de sus finanzas
                            estés donde estés en el mundo.
                        </p>
                    </div>

                    <div class="service-card">
                        <img src="images/icon-budgeting.svg" alt="Icon for online banking" class="card-icon">
                        <h3 class="card-title">Presupuesto simple</h3>
                        <p class="card-text">
                            Vea exactamente a dónde va su dinero cada mes. Recibe notificaciones cuando estés
                            cerca de llegar a sus límites.
                        </p>
                    </div>

                    <div class="service-card">
                        <img src="images/icon-onboarding.svg" alt="Icon for online banking" class="card-icon">
                        <h3 class="card-title">Incorporación rápida</h3>
                        <p class="card-text">
                            No hacemos sucursales. Abra su cuenta en minutos en línea y comience a tomar el control
                            de sus finanzas de inmediato.
                        </p>
                    </div>


                </div>
            </section>

        </main>

        <!-- #FOOTER -->
        <footer>
            <div class="wrapper-flex">
                <div>
                    <div class="footer-brand">
                        TAXOPEL
                    </div>

                    <div class="social-link">
                        <a href="#">
                            <ion-icon name="logo-facebook"></ion-icon>
                        </a>
                        <a href="#">
                            <ion-icon name="logo-twitter"></ion-icon>
                        </a>
                        <a href="#">
                            <ion-icon name="logo-instagram"></ion-icon>
                        </a>
                    </div>

                </div>

                <div class="footer-nav">
                    <ul>
                        <li><a href="#home">Home</a></li>
                        <li><a href="#service">Servicios</a></li>
                    </ul>
                    <ul>
                        <li><a href="#">Soporte</a></li>
                        <li><a href="#">Privacy Policy</a></li>
                    </ul>
                </div>
            </div>
            <div class="wrapper">
                <a href="Login"><button class="btn-primary">Iniciar Sesión</button></a>
                <p class="copyright">&copy; TAXOPEL. All Rights Reserved</p>
            </div>
        </footer>

    </div>

    <!-- custom js -->
    <script>

        const navbarToggleBtn = document.querySelector('.navbar-toggle-btn');
        const navbarNav = document.querySelector('.navbar-nav');

        const navbarToggleFunc = function () {
            navbarToggleBtn.classList.toggle('active');
            navbarNav.classList.toggle('active');
        }

        navbarToggleBtn.addEventListener('click', navbarToggleFunc);

    </script>

    <!-- ionicon link -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>

</html>
