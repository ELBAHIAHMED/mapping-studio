<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Injection Configuration</title>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Computime</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    <ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="/">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/dbconfig">BD Configuration</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/configmapping">Mapping Configuration</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/injectionconf">Injection Configuration</a>
      </li>
       <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Injection
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="/injectmysqlsqlserver">MySQL to SQLServer</a>
          <a class="dropdown-item" href="/injectsqlservermysql">SQLServer to MySQL</a>
          <a class="dropdown-item" href="/injectaccessmysql">Access to MySQL</a>    
          <a class="dropdown-item" href="/injectmysqlmysql2">MySQL to MySQL2</a>  
          <a class="dropdown-item" href="/injectmysql2mysql">MySQL2 to MySQL</a>       
          
        </div>
      </li>
    </ul>
  </div>
</nav>

<div class = "container p-5">
<div style = "margin: 0 auto;
    width:80% ">
<form method = "post" action = "/injectionconfsave" >

<div>
<div class="form-check form-check-inline">
  <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1" name= "mysqlmysql2">
  <label class="form-check-label" for="inlineCheckbox1">MySQL to MySQL2</label>
</div>
<div class="form-check form-check-inline">
  <input class="form-check-input" type="checkbox" id="inlineCheckbox2" value="mysql2mysql" name= "mysql2mysql">
  <label class="form-check-label" for="inlineCheckbox2">MySQL2 to MySQL</label>
</div>
<div class="form-check form-check-inline">
  <input class="form-check-input" type="checkbox" id="inlineCheckbox3" value="option3" name= "sqlservermysql">
  <label class="form-check-label" for="inlineCheckbox3">SQLServer to MySQL</label>
</div>
<div class="form-check form-check-inline">
  <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1" name= "mysqlsqlserver">
  <label class="form-check-label" for="inlineCheckbox1">MySQL to SQLServer</label>
</div>
<div class="form-check form-check-inline">
  <input class="form-check-input" type="checkbox" id="inlineCheckbox1" value="option1" name= "accessmysql">
  <label class="form-check-label" for="inlineCheckbox1">Access to MySQL</label>
</div>
</div>
<button type = "submit" class= "btn btn-dark" style = "margin: 15px 0 0 30%">Submit</button>




</form>
</div>


</div>

<!-- style = "margin: 15px 0 0 30%" -->


<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

</body>



</html>