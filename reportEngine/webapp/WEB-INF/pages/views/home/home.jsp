<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<div class="row-fluid">
		<form:form id="ftForm" action="/reportEngineV2/home"
			modelAttribute="homeObj" method="post" role="form">
			<div class="form-group">
				<div class="col-md-2">
					<form:select class="form-control" path="ente" items="${enti}" />
				</div>
			</div>
			<form:button type="submit">Invio</form:button>
			
		</form:form>
	</div>
</body>
</html>