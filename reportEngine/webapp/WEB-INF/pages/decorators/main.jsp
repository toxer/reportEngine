<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="frontend/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="frontend/jquery/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="frontend/bootstrap/js/bootstrap.min.js"></script>
<!-- Angular -->
<link href="frontend/angular/angular-csp.css" rel="stylesheet">
<script src="frontend/angular/angular.min.js"></script>

<title><sitemesh:write property='title' /></title>
<sitemesh:write property='head' />
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#navbar-ex-collapse">
					<span class="sr-only">Toggle navigation</span><span
						class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>Brand</span></a>
			</div>
			<div class="collapse navbar-collapse" id="navbar-ex-collapse">
				<ul class="nav navbar-nav navbar-right">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#">Gestione report<br></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="section">
		<div class="container">
			<sitemesh:write property='body' />
		</div>
	</div>
	<footer class="section section-primary">
		<div class="container">
		
		</div>
	</footer>
</body>
</html>

