
<!DOCTYPE html>
<html lang="en">
   <head itemscope="" itemtype="http://schema.org/WebSite">
      <title itemprop="name">PFA</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
      <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>    
      
      <script src="Preview%20Bootstrap%20snippets.%20bs4%20navbar%20with%20dropdown%20animations_fichiers/cache-1598759682-97135bbb13d92c11d6b2a92f6a36685a.js" type="text/javascript"></script> 
      <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.1/jquery.js" integrity="sha512-eKwZNCvuOhxcqGTXAudC9WH2KUKf8Id1cqNoMc6DKZuN8upL22xj3ZkJdckyDd3Gjsi1QHKZ3ug0XQHQkGRNJg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
   </head>
   <body>
    <div id="snippetContent">
       <link rel="stylesheet" href="Preview%20Bootstrap%20snippets.%20bs4%20navbar%20with%20dropdown%20animations_fichiers/bootstrap.css" wfd-invisible="true">
       <script src="Preview%20Bootstrap%20snippets.%20bs4%20navbar%20with%20dropdown%20animations_fichiers/bootstrap.js" wfd-invisible="true"></script> 
       
       <style type="text/css" wfd-invisible="true">
          @import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700,800');
          @import url('https://fonts.googleapis.com/css?family=Lobster');
          html {
             font-size: 90%;
          }
          body {
          font-family: 'Open Sans', sans-serif;
          font-size: 1.6rem;
          font-weight: 500;
          background-image: url(https://blog.integral-system.fr/wp-content/uploads/2020/06/bigdata.jpg)  ;
          }
          .topnav {
             font-size: 0.6rem;
          }
          h1 {
          margin-bottom: 0.5em;
          font-size: 3.6rem;
          }

          p {
          margin-bottom: 0.5em;
          font-size: 1.6rem;
          line-height: 1.6;
          }
          .button {
          display: inline-block;
          margin-top: 20px;
          padding: 8px 25px;
          border-radius: 4px;
          }
          .button-primary {
          position: relative;
          background-color: #5bc0de;
          color: #fff;
          font-size: 1.8rem;
          font-weight: 700;
          transition: color 0.3s ease-in;
          z-index: 1;
          }
          .button-primary:hover {
          color: #5bc0de;
          text-decoration: none;
          }
          .button-primary::after {
          content: '';
          position: absolute;
          bottom: 0;
          left: 0;
          right: 0;
          top: 0;
          background-color: rgb(255, 0, 0);
          border-radius: 4px;
          opacity: 0;
          -webkit-transform: scaleX(0.8);
          -ms-transform: scaleX(0.8);
          transform: scaleX(0.8);
          transition: all 0.3s ease-in;
          z-index: -1;
          }
          .button-primary:hover::after {
          opacity: 1;
          -webkit-transform: scaleX(1);
          -ms-transform: scaleX(1);
          transform: scaleX(1);
          }
          .overlay::after {
          content: '';
          position: absolute;
          bottom: 0;
          left: 0;
          right: 0;
          top: 0;
          background-color: rgba(0, 0, 0, .3);
          }
          .header-area {
          position: relative;
          height: 100vh;
          background: #63aec5;
          background-attachment: fixed;
          background-position: center center;
          background-repeat: no-repear;
          background-size: cover;
          }
          .banner {
          display: flex;
          align-items: center;
          position: relative;
          height: 100%;
          color: #fff;
          text-align: center;
          z-index: 1;
          }
          .banner h1 {
          font-weight: 800;
          }
          .banner p {
          font-weight: 700;
          }
          .navbar {
          position: absolute;
          left: 0;
          top: 0;
          padding: 0;
         
          transition: background 0.6s ease-in;
          z-index: 99999;
          }
          .navbar .navbar-brand {
          font-family: 'Lobster', cursive;
          font-size: 2.5rem;
          }
          .navbar-brande {
          font-family: 'Lobster', cursive;
          font-size: 2.5rem;
          color: orange;
          }
          .navbar .navbar-toggler {
          position: relative;
          height: 50px;
          width: 50px;
          border: none;
          cursor: pointer;
          outline: none;
          }
          .navbar .navbar-toggler .menu-icon-bar {
          position: absolute;
          left: 15px;
          right: 15px;
          height: 2px;
          background-color: rgb(255, 66, 66);
          opacity: 0;
          -webkit-transform: translateY(-1px);
          -ms-transform: translateY(-1px);
          transform: translateY(-1px);
          transition: all 0.3s ease-in;
          }
          .navbar .navbar-toggler .menu-icon-bar:first-child {
          opacity: 1;
          -webkit-transform: translateY(-1px) rotate(45deg);
          -ms-sform: translateY(-1px) rotate(45deg);
          transform: translateY(-1px) rotate(45deg);
          }
          .navbar .navbar-toggler .menu-icon-bar:last-child {
          opacity: 1;
          -webkit-transform: translateY(-1px) rotate(135deg);
          -ms-sform: translateY(-1px) rotate(135deg);
          transform: translateY(-1px) rotate(135deg);
          }
          .navbar .navbar-toggler.collapsed .menu-icon-bar {
          opacity: 1;
          }
          .navbar .navbar-toggler.collapsed .menu-icon-bar:first-child {
          -webkit-transform: translateY(-7px) rotate(0);
          -ms-sform: translateY(-7px) rotate(0);
          transform: translateY(-7px) rotate(0);
          }
          .navbar .navbar-toggler.collapsed .menu-icon-bar:last-child {
          -webkit-transform: translateY(5px) rotate(0);
          -ms-sform: translateY(5px) rotate(0);
          transform: translateY(5px) rotate(0);
          }
          .navbar-dark .navbar-nav .nav-link {
          position:rela;
          color: rgb(255, 255, 255);
          font-size: 1.2rem;
          }
          .navbar-dark .navbar-nav .nav-link:focus, .navbar-dark .navbar-nav .nav-link:hover {
          color: rgb(255, 255, 255);
          }
          .navbar .dropdown-menu {
          padding: 0;
          background-color: rgba(2, 2, 2, 0.9);
          }
          .navbar .dropdown-menu .dropdown-item {
          position: relative;
          padding: 10px 20px;
          color: #fff;
          font-size: 1.4rem;
          border-bottom: 1px solid rgba(255, 255, 255, .1);
          transition: color 0.2s ease-in;
          }
          .navbar .dropdown-menu .dropdown-item:last-child {
          border-bottom: none;
          }
          .navbar .dropdown-menu .dropdown-item:hover {
          background: transparent;
          color: #5bc0de;
          }
          .navbar .dropdown-menu .dropdown-item::before {
          content: '';
          position: absolute;
          bottom: 0;
          left: 0;
          top: 0;
          width: 5px;
          background-color: #5bc0de;
          opacity: 0;
          transition: opacity 0.2s ease-in;
          }
          .navbar .dropdown-menu .dropdown-item:hover::before {
          opacity: 1;
          }
          .navbar.fixed-top {
          position: fixed;
          -webkit-animation: navbar-animation 0.6s;
          animation: navbar-animation 0.6s;
          background-color: rgba(0, 0, 0, .9);
          }
          .navbar.fixed-top.navbar-dark .navbar-nav .nav-link.active {
          color: #5bc0de;
          }
          .navbar.fixed-top.navbar-dark .navbar-nav .nav-link::after {
          background-color: #5bc0de;
          }
          .content {
          padding: 120px 0;
          }
          @media screen and (max-width: 768px) {
          .navbar-brand {
          margin-left: 20px;
          }
          .navbar-brande {
          margin-left: 20px;
          }
          .navbar-nav {
          padding: 0px 20px;
          background-color: rgba(0, 0, 0, .9);
          }
          .navbar.fixed-top .navbar-nav {
          background: transparent;
          }
          }
          @media screen and (min-width: 767px) {
          .banner {
          padding: 0 150px;
          }
          .banner h1 {
          font-size: 5rem;
          }
          .banner p {
          font-size: 2rem;
          }
          .nav-link {
            padding-right: 1.5rem;
            
          }
        }
          .navbar-dark .navbar-nav .nav-link {
          padding: 23px px;
          }
          .navbar-dark .navbar-nav .nav-link::after {
          content: '';
          position: absolute;
          bottom: 15px;
          left: 3%;
          right: 100%;
          height: 1px;
          background-color: rgb(0, 0, 0);
          -webkit-transform: scaleX(0);
          -ms-transform: scaleX(0);
          transform: scaleX(0);
          transition: transform 0.1s ease-in;
          }
          .navbar-dark .navbar-nav .nav-link:hover::after {
          -webkit-transform: scaleX(1);
          -ms-transform: scaleX(1);
          transform: scaleX(1);
          }
          .dropdown-menu {
          min-width: 200px;
          -webkit-animation: dropdown-animation 0.3s;
          animation: dropdown-animation 0.3s;
          -webkit-transform-origin: top;
          -ms-transform-origin: top;
          transform-origin: top;
          }
          }
          @-webkit-keyframes navbar-animation {
          0% {
          opacity: 0;
          -webkit-transform: translateY(-100%);
          -ms-transform: translateY(-100%);
          transform: translateY(-100%);
          }
          100% {
          opacity: 1;
          -webkit-transform: translateY(0);
          -ms-transform: translateY(0);
          transform: translateY(0);
          }
          }
          @keyframes navbar-animation {
          0% {
          opacity: 0;
          -webkit-transform: translateY(-100%);
          -ms-transform: translateY(-100%);
          transform: translateY(-100%);
          }
          100% {
          opacity: 1;
          -webkit-transform: translateY(0);
          -ms-transform: translateY(0);
          transform: translateY(0);
          }
          }
          @-webkit-keyframes dropdown-animation {
          0% {
          -webkit-transform: scaleY(0);
          -ms-transform: scaleY(0);
          transform: scaleY(0);
          }
          75% {
          -webkit-transform: scaleY(1.1);
          -ms-transform: scaleY(1.1);
          transform: scaleY(1.1);
          }
          100% {
          -webkit-transform: scaleY(1);
          -ms-transform: scaleY(1);
          transform: scaleY(1);
          }
          }
          @keyframes dropdown-animation {
          0% {
          -webkit-transform: scaleY(0);
          -ms-transform: scaleY(0);
          transform: scaleY(0);
          }
          75% {
          -webkit-transform: scaleY(1.1);
          -ms-transform: scaleY(1.1);
          transform: scaleY(1.1);
          }
          100% {
          -webkit-transform: scaleY(1);
          -ms-transform: scaleY(1);
          transform: scaleY(1);
          }
          }

          /** input style **/
          /*$primary: #11998e;
          $secondary: #38ef7d;
          $white: #fff;
          $gray: #9b9b9b;*/
          .form__group {
          position: relative;
          padding: 15px 0 0;
          margin-top: 10px;
          width: 50%;
          }

          .form__field {
          font-family: inherit;
          width: 100%;
          border: 0;
          border-bottom: 2px solid #9b9b9b;
          outline: 0;
          font-size: 1.3rem;
          color: #fff;
          padding: 7px 0;
          background: transparent;
          transition: border-color 0.2s;
          }

          .form__field:placeholder {
             color: transparent;
          }

          .form__field:placeholder-shown ~ .form__label {
             font-size: 1.3rem;
             cursor: text;
             top: 20px;
          }
          

          .form__label {
          position: absolute;
          top: 0;
          display: block;
          transition: 0.2s;
          font-size: 1.2rem;
          color: #9b9b9b;
          margin-bottom: 5px;
          }

          .form__field:focus {
       
          padding-bottom: 6px;  
          font-weight: 700;
          border-width: 3px;
          border-image: linear-gradient(to right, #11998e,#ffffff);
          border-image-slice: 1;
          }
          .form__label:focus {
             position: absolute;
             top: 0;
             display: block;
             transition: 0.2s;
             font-size: 1rem;
             color: #11998e;
             font-weight:700;    
          }
          /* reset input */
          .form__field:required, .form__field:invalid{
           box-shadow:none; 
          }
          /* padding between inputs */
          .in_pad {
             margin-right: 200px;
             margin-bottom: 40px;
          }
            h2 {
            margin-top: 30px;
            margin-bottom: 25px;
            font-size: 78px;
            text-transform: uppercase;
            font-weight: 800;
             color: #fff;
            letter-spacing: 1px;
          }


         h2 em {
                 font-style: normal;
                color: #ed563b;
                font-weight: 900;}     
                
                 h6 {
                  margin-top: 0px;
                  font-size: 30px;
                   text-transform: uppercase;
                   font-weight: 800;
                  color: #fff;
                   letter-spacing: 0.5px;
                     }       
.caption {
  text-align: center;
  position: absolute;
  width: 80%;
  left: 50%;
  top: 50%;
  transform: translate(-50%,-50%);
}
.main-button a {
  display: inline-block;
  font-size: 15px;
  padding: 12px 20px;
  background-color: #ed563b;
  color: #fff;
  text-align: center;
  font-weight: 400;
  text-transform: uppercase;
  transition: all .3s;
}

.main-button a:hover {
  background-color: #f9735b;
}


       </style>

       
       <script type="text/javascript" wfd-invisible="true">jQuery(function($) {
          $(window).on('scroll', function() {
          if ($(this).scrollTop() >= 200) {
          $('.navbar').addClass('fixed-top');
          } else if ($(this).scrollTop() == 0) {
          $('.navbar').removeClass('fixed-top');
          }
          });
          
          function adjustNav() {
          var winWidth = $(window).width(),
          dropdown = $('.dropdown'),
          dropdownMenu = $('.dropdown-menu');
          
          if (winWidth >= 768) {
          dropdown.on('mouseenter', function() {
          $(this).addClass('show')
              .children(dropdownMenu).addClass('show');
          });
          
          dropdown.on('mouseleave', function() {
          $(this).removeClass('show')
              .children(dropdownMenu).removeClass('show');
          });
          } else {
          dropdown.off('mouseenter mouseleave');
          }
          }
          
          $(window).on('resize', adjustNav);
          
          adjustNav();
          });
       </script> 
    </div>

    <!-- Navigation bar -->
    <div class="topnav">
       <nav class="navbar navbar-expand-md navbar-dark">
          <div class="container">
             <a href="#" class="navbar-brand">Mapping </a>
             <a href="#" class="navbar-brand" style="color: orange;">Studio</a>
              <button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav" wfd-invisible="true"> <span class="menu-icon-bar"></span> <span class="menu-icon-bar"></span> <span class="menu-icon-bar"></span> </button>

          </div>
       </nav>
    </div>
    <div class="video-overlay header-text"></div>
    <div class="caption">
        <h6>DATA MAPPING</h6>
        <h2>easy with our <em>Studio</em></h2>
        <div class="main-button scroll-to-section">
         <a href="/dbconfig">GET CONNECTED TO YOUR DATA</a>
     </div>
    </div>
    
    </div>


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>


<script>
$('.custom-file-input').on('change', function() { 
	   let fileName = $(this).val().split('\\').pop(); 
	   $(this).next('.custom-file-label').addClass("selected").html(fileName); 
});
	
</script>

</html>