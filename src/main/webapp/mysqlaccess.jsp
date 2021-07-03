<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head itemscope="" itemtype="http://schema.org/WebSite">
        <title itemprop="name">PFA</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>    
        
        <script src="Preview%20Bootstrap%20snippets.%20bs4%20navbar%20with%20dropdown%20animations_fichiers/cache-1598759682-97135bbb13d92c11d6b2a92f6a36685a.js" type="text/javascript"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.11.1/jquery.js" integrity="sha512-eKwZNCvuOhxcqGTXAudC9WH2KUKf8Id1cqNoMc6DKZuN8upL22xj3ZkJdckyDd3Gjsi1QHKZ3ug0XQHQkGRNJg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
		<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <script type="text/javascript">
            function handleClick()
            {
              var count = document.getElementById("gimmecount").value ; 
              var value = ""; 
              for (let i = 1 ; i< count; i++){
                  console.log(i) ; 
                  if (i == count -1 ){
                      value += document.getElementById("th"+i).getAttribute("value") + "," + document.getElementById("td"+i).options[document.getElementById("td"+i).selectedIndex].value ;
                  }
                  else if (i < count-1){
                      value += document.getElementById("th"+i).getAttribute("value") + "," + document.getElementById("td"+i).options[document.getElementById("td"+i).selectedIndex].value + ";";
                  }
              }
          
              document.getElementById('tableTextId').value = value ; 
              
              
              console.log(value) ;
              //console.log(count) ; 
              //console.log(document.getElementById("th1").getAttribute("value")) ; 
              //console.log(document.getElementById("th1").innerText) ; 
              //console.log(document.getElementById("1").innerText);
              //console.log(document.getElementById("1").getAttribute("value"));
           }
            
          </script>

</head>

<style type="text/css" wfd-invisible="true">
    @import url('https://fonts.googleapis.com/css?family=Open+Sans:400,700,800');
    @import url('https://fonts.googleapis.com/css?family=Lobster');
    html {
       font-size: 90%;
    }
   
    .topnav {
       font-size: 0.6rem;
    }
    h1 {
    margin-bottom: 0.5em;
    font-size: 3.6rem;
    }
    h2 {
       padding-bottom: 10px;
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
    width: 100%;
    transition: background 0.6s ease-in;
    z-index: 99999;
    }
    .navbar .navbar-brand {
    font-family: 'Lobster', cursive;
    font-size: 2.5rem;
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
    position: relative;
    color: rgb(255, 255, 255);
    font-size: 1.6rem;
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
    color: white;
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
    .navbar-nav {
    padding: 0 20px;
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
    .navbar-dark .navbar-nav .nav-link {
    padding: 23px 15px;
    }
    .navbar-dark .navbar-nav .nav-link::after {
    content: '';
    position: absolute;
    bottom: 15px;
    left: 30%;
    right: 30%;
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
       margin-right: 50px;
       margin-bottom: 40px;
    }

/* ==========================================================================
#FONT
========================================================================== */
.font-robo {
font-family: "Roboto", "Arial", "Helvetica Neue", sans-serif;
}

/* ==========================================================================
#GRID
========================================================================== */
.row {
display: -webkit-box;
display: -webkit-flex;
display: -moz-box;
display: -ms-flexbox;
display: flex;
-webkit-flex-wrap: wrap;
-ms-flex-wrap: wrap;
flex-wrap: wrap;
}

.row-space {
-webkit-box-pack: justify;
-webkit-justify-content: space-between;
-moz-box-pack: justify;
-ms-flex-pack: justify;
justify-content: space-between;
}

.col-2 {
width: -webkit-calc((100% - 60px) / 2);
width: -moz-calc((100% - 60px) / 2);
width: calc((100% - 60px) / 2);
}

@media (max-width: 767px) {
.col-2 {
width: 100%;
}
}

/* ==========================================================================
#BOX-SIZING
========================================================================== */
/**
* More sensible default box-sizing:
* css-tricks.com/inheriting-box-sizing-probably-slightly-better-best-practice
*/
html {
-webkit-box-sizing: border-box;
-moz-box-sizing: border-box;
box-sizing: border-box;
}

* {
padding: 0;
margin: 0;
}

*, *:before, *:after {
-webkit-box-sizing: inherit;
-moz-box-sizing: inherit;
box-sizing: inherit;
}

/* ==========================================================================
#RESET
========================================================================== */
/**
* A very simple reset that sits on top of Normalize.css.
*/
body,
h1, h2, h3, h4, h5, h6,
blockquote, p, pre,
dl, dd, ol, ul,
figure,
hr,
fieldset, legend {
margin: 0;
padding: 0;
}

/**
* Remove trailing margins from nested lists.
*/
li > ol,
li > ul {
margin-bottom: 0;
}

/**
* Remove default table spacing.
*/
table {
border-collapse: collapse;
border-spacing: 0;
}

/**
* 1. Reset Chrome and Firefox behaviour which sets a `min-width: min-content;`
*    on fieldsets.
*/
fieldset {
min-width: 0;
/* [1] */
border: 0;
}

button {
outline: none;
background: none;
border: none;
}

/* ==========================================================================
#PAGE WRAPPER
========================================================================== */
.page-wrapper {
min-height: 100vh;
}

body {
font-family: "Roboto", "Arial", "Helvetica Neue", sans-serif;
font-weight: 400;
font-size: 14px;
}

h1, h2, h3, h4, h5, h6 {
font-weight: 400;
}

h1 {
font-size: 36px;
}

h2 {
font-size: 30px;
}

h3 {
font-size: 24px;
}

h4 {
font-size: 18px;
}

h5 {
font-size: 15px;
}

h6 {
font-size: 13px;
}

/* ==========================================================================
#BACKGROUND
========================================================================== */
.bg-blue {
background: #2c6ed5;
}

.bg-red {
background-image: url(https://besthqwallpapers.com/Uploads/10-9-2020/140796/thumb2-blue-hexagons-4k-3d-art-creative-honeycomb.jpg);



}

/* ==========================================================================
#SPACING
========================================================================== */
.p-t-100 {
padding-top: 100px;
}

.p-t-180 {
padding-top: 150px;
}

.p-t-20 {
padding-top: 20px;
}

.p-t-30 {
padding-top: 30px;
}

.p-b-100 {
padding-bottom: 5px;
}

/* ==========================================================================
#WRAPPER
========================================================================== */
.wrapper {
margin: 0 auto;
}

.wrapper--w960 {
max-width: 960px;
}

.wrapper--w680 {
max-width: 680px;
}

/* ==========================================================================
#BUTTON
========================================================================== */
.btn {
line-height: 40px;
display: inline-block;
padding: 0 80px;
cursor: pointer;
color: #fff;
font-family: "Roboto", "Arial", "Helvetica Neue", sans-serif;
-webkit-transition: all 0.4s ease;
-o-transition: all 0.4s ease;
-moz-transition: all 0.4s ease;
transition: all 0.4s ease;
font-size: 14px;
font-weight: 700;
}

.btn--radius {
-webkit-border-radius: 3px;
-moz-border-radius: 3px;
border-radius: 3px;
}

.btn--green {
background: #57b846;
}

.btn--green:hover {
background: #4dae3c;
}

/* ==========================================================================
#DATE PICKER
========================================================================== */
td.active {
background-color: #2c6ed5;
}
.caption {
text-align: center;
position: absolute;
width: 80%;
left: 50%;
top: 240%;
transform: translate(-50%,-50%);
}

input[type="date" i] {
padding: 14px;
}

.table-condensed td, .table-condensed th {
font-size: 14px;
font-family: "Roboto", "Arial", "Helvetica Neue", sans-serif;
font-weight: 400;
}

.daterangepicker td {
width: 40px;
height: 30px;
}

.daterangepicker {
border: none;
-webkit-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
-moz-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
display: none;
border: 1px solid #e0e0e0;
margin-top: 5px;
}

.daterangepicker::after, .daterangepicker::before {
display: none;
}

.daterangepicker thead tr th {
padding: 10px 0;
}

.daterangepicker .table-condensed th select {
border: 1px solid #ccc;
-webkit-border-radius: 3px;
-moz-border-radius: 3px;
border-radius: 3px;
font-size: 14px;
padding: 5px;
outline: none;
}

/* ==========================================================================
#FORM
========================================================================== */
input {
outline: none;
margin: 0;
border: none;
-webkit-box-shadow: none;
-moz-box-shadow: none;
box-shadow: none;
width: 100%;
font-size: 14px;
font-family: inherit;
}

/* input group 1 */
/* end input group 1 */
.input-group {
position: relative;
margin-bottom: 32px;
border-bottom: 1px solid #e5e5e5;
}

.input-icon {
position: absolute;
font-size: 18px;
color: #ccc;
right: 8px;
top: 50%;
-webkit-transform: translateY(-50%);
-moz-transform: translateY(-50%);
-ms-transform: translateY(-50%);
-o-transform: translateY(-50%);
transform: translateY(-50%);
cursor: pointer;
}

.input--style-2 {
padding: 9px 0;
color: #666;
font-size: 16px;
font-weight: 500;
}

.input--style-2::-webkit-input-placeholder {
/* WebKit, Blink, Edge */
color: #808080;
}

.input--style-2:-moz-placeholder {
/* Mozilla Firefox 4 to 18 */
color: #808080;
opacity: 1;
}

.input--style-2::-moz-placeholder {
/* Mozilla Firefox 19+ */
color: #808080;
opacity: 1;
}

.input--style-2:-ms-input-placeholder {
/* Internet Explorer 10-11 */
color: #808080;
}

.input--style-2:-ms-input-placeholder {
/* Microsoft Edge */
color: #808080;
}

/* ==========================================================================
#SELECT2
========================================================================== */
.select--no-search .select2-search {
display: none !important;
}

.rs-select2 .select2-container {
width: 100% !important;
outline: none;
}

.rs-select2 .select2-container .select2-selection--single {
outline: none;
border: none;
height: 36px;
}

.rs-select2 .select2-container .select2-selection--single .select2-selection__rendered {
line-height: 36px;
padding-left: 0;
color: #808080;
font-size: 16px;
font-family: inherit;
font-weight: 500;
}

.rs-select2 .select2-container .select2-selection--single .select2-selection__arrow {
height: 34px;
right: 4px;
display: -webkit-box;
display: -webkit-flex;
display: -moz-box;
display: -ms-flexbox;
display: flex;
-webkit-box-pack: center;
-webkit-justify-content: center;
-moz-box-pack: center;
-ms-flex-pack: center;
justify-content: center;
-webkit-box-align: center;
-webkit-align-items: center;
-moz-box-align: center;
-ms-flex-align: center;
align-items: center;
}

.rs-select2 .select2-container .select2-selection--single .select2-selection__arrow b {
display: none;
}

.rs-select2 .select2-container .select2-selection--single .select2-selection__arrow:after {
font-family: "Material-Design-Iconic-Font";
content: '\f2f9';
font-size: 18px;
color: #ccc;
-webkit-transition: all 0.4s ease;
-o-transition: all 0.4s ease;
-moz-transition: all 0.4s ease;
transition: all 0.4s ease;
}

.rs-select2 .select2-container.select2-container--open .select2-selection--single .select2-selection__arrow::after {
-webkit-transform: rotate(-180deg);
-moz-transform: rotate(-180deg);
-ms-transform: rotate(-180deg);
-o-transform: rotate(-180deg);
transform: rotate(-180deg);
}

.select2-container--open .select2-dropdown--below {
border: none;
-webkit-border-radius: 3px;
-moz-border-radius: 3px;
border-radius: 3px;
-webkit-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
-moz-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
border: 1px solid #e0e0e0;
margin-top: 5px;
overflow: hidden;
}

/* ==========================================================================
#TITLE
========================================================================== */

.title {
text-transform: uppercase;
font-weight: 700;
margin-bottom: 37px;
}

/* ==========================================================================
#CARD
========================================================================== */
.card {
overflow: hidden;
-webkit-border-radius: 3px;
-moz-border-radius: 3px;
border-radius: 3px;
background: rgb(255, 255, 255)
}

.card-2 {
-webkit-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
-moz-box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
box-shadow: 0px 8px 20px 0px rgba(0, 0, 0, 0.15);
-webkit-border-radius: 10px;
-moz-border-radius: 10px;
border-radius: 10px;
width: 100%;
display: table;
}

.card-2 .card-heading {
background: url(https://upload.wikimedia.org/wikipedia/commons/b/b2/Database-mysql.svg) top left/cover no-repeat;
width: 29.1%;
display: table-cell;
}
.card-headinge {
background: url(https://www.freeiconspng.com/thumbs/sql-server-icon-png/sql-server-icon-8.png) top left/cover no-repeat;
width: 29.1%;
display: table-cell;
}
.card-headingee {
background: url(https://img.icons8.com/color/452/microsoft-access-2019.png) top left/cover no-repeat;
width: 29.1%;
display: table-cell;
}

.card-2 .card-body {
display: table-cell;
padding: 80px 60px;
padding-bottom: 5px;
}

@media (max-width: 767px) {
.card-2 {
display: block;
}
.card-2 .card-heading {
width: 100%;
display: block;
padding-top: 300px;
background-position: left center;
}
.card-2 .card-body {
display: block;
padding: 60px 50px;
}
}
.main-button a:hover {
background-color: #f9735b;
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
    (function ($) {
'use strict';
/*==================================================================
    [ Daterangepicker ]*/
try {
    $('.js-datepicker').daterangepicker({
        "singleDatePicker": true,
        "showDropdowns": true,
        "autoUpdateInput": false,
        locale: {
            format: 'DD/MM/YYYY'
        },
    });

    var myCalendar = $('.js-datepicker');
    var isClick = 0;

    $(window).on('click',function(){
        isClick = 0;
    });

    $(myCalendar).on('apply.daterangepicker',function(ev, picker){
        isClick = 0;
        $(this).val(picker.startDate.format('DD/MM/YYYY'));

    });

    $('.js-btn-calendar').on('click',function(e){
        e.stopPropagation();

        if(isClick === 1) isClick = 0;
        else if(isClick === 0) isClick = 1;

        if (isClick === 1) {
            myCalendar.focus();
        }
    });

    $(myCalendar).on('click',function(e){
        e.stopPropagation();
        isClick = 1;
    });

    $('.daterangepicker').on('click',function(e){
        e.stopPropagation();
    });


} catch(er) {console.log(er);}
/*[ Select 2 Config ]
    ===========================================================*/

try {
    var selectSimple = $('.js-select-simple');

    selectSimple.each(function () {
        var that = $(this);
        var selectBox = that.find('select');
        var selectDropdown = that.find('.select-dropdown');
        selectBox.select2({
            dropdownParent: selectDropdown
        });
    });

} catch (err) {
    console.log(err);
}


})(jQuery);
</script>



<body style="background-image: url(https://www.teahub.io/photos/full/257-2579582_wallpaper-linear-orange-gradient-blue-dark-orange-medium.jpg);">
    <div class="topnav">
        <nav class="navbar navbar-expand-md navbar-dark">
            <div class="container">
                <a href="#" class="navbar-brand">Mapping </a>
                <a href="#" class="navbar-brand" style="color: orange;">Studio</a>
                 <button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav" wfd-invisible="true"> <span class="menu-icon-bar"></span> <span class="menu-icon-bar"></span> <span class="menu-icon-bar"></span> </button>
              <div id="main-nav" class="collapse navbar-collapse">
                 <ul class="navbar-nav ml-auto">
                    <li><a href="/" class="nav-item nav-link active">Home</a></li>
                    <li><a href="/dbconfig" class="nav-item nav-link">BD Configuration</a></li>
                    <li><a href="/configmapping" class="nav-item nav-link" >Mapping Configuration</a></li>
                    
                    <li class="dropdown">
                       <a href="#" class="nav-item nav-link" data-toggle="dropdown">Injection</a>
                       <div class="dropdown-menu" wfd-invisible="true">
                           <a href="/injectmysqlsqlserver" class="dropdown-item">MySQL to SQLServer</a> 
                           <a href="/injectsqlservermysql" class="dropdown-item">SQLServer to MySQL</a> 
                           <a  href="/injectaccessmysql"class="dropdown-item">Access to MySQL</a> 
                           <a href="/injectmysqlmysql2" class="dropdown-item">MySQL to MySQL2</a> 
                           <a href="/injectmysql2mysql" class="dropdown-item">MySQL2 to MySQL</a>
                       </div>
                    </li>
                 </ul>
              </div>
           </div>
        </nav>
     </div>
     <br><br><br><br>
<c:set var="count" value="1" scope="page" />
<div class = "container p-5">
<form method = "post" action = "/mysqlaccessmapfields">
<table class="table">
  <thead class="thead-dark">
    <tr>
      <th scope="col" style="background-color: orange;">MySQL tables</th>
      <th scope="col" style="background-color: #299fe4;">Access tables</th>

    </tr>
  </thead>
  <tbody>
  <c:forEach var = "mysqltable" items = "${mysqltables}">
    <tr id = "${count}" value ="workplz">
    
      <th scope="row" style="color: white;"id = "th${count}" value = "${mysqltable}"><c:out value="${mysqltable}"/></th>
      
      <td class = "tddd"><select class="custom-select" id = "td${count}">
      <option value = "No Mapping">No Mapping</option>
      <c:forEach var = "accesstable" items = "${accesstables}">
      <option value = "${accesstable}"><c:out value="${accesstable}"/></option>
      </c:forEach>
       
      </select></td>

    </tr>
    <c:set var="count" value="${count + 1}" scope="page"/>
    </c:forEach>
    
    </tbody>
    
    
    </table>
    <input type="hidden" name="tableValue" id="tableTextId" />  
    
    <input type="hidden" name="counto" id="gimmecount" value = "${count}"/>  
    <button class="btn btn-dark" style = "margin-left :48%" type="submit" onclick="handleClick();">Submit</button>
</form>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>

</html>