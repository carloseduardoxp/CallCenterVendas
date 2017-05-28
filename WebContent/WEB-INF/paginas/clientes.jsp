<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cadastro de clientes</title>
</head>
<body>
	<form method="POST" action="clienteServlet">
		 <input type="hidden" name="codigo" value="${cliente.codigo}"/>
	     Codigo <input type="text" disabled name="codigo" value="${cliente.codigo}"/> <br>
	     Nome <input type="text" name="nome" value="${cliente.nome}"/> <br>
	     % desconto <input type="text"  name="percentualDesconto" value="${cliente.percentualDesconto}"/> <br>
	     Cpf <input type="text"  name="cpf" value="${cliente.cpf}"/> <br>
	     Email <input type="text"  name="email" value="${cliente.email}"/> <br>
		 <input type="submit" value="Enviar" />
	</form>

	<font color="red"><h2>${mensagem}</h2></font>

	<h4>Clientes cadastrados</h4>
	<table border="1">
		<tr>
			<th>Codigo</th>
			<th>Nome Fantasia</th>			
			<th>% Desconto</th>
			<th>Cpf</th>
			<th>Email</th>
			<th></th>
			<th></th>
		</tr>
		<c:forEach var="f" items="${clientes}">
		<tr>
			<td>${f.codigo}</td>		
			<td>${f.nome}</td>
			<td>${f.percentualDesconto}</td>
			<td>${f.cpf}</td>
			<td>${f.email}</td>
			<td><a href=clienteServlet?acao=editar&codigo=${f.codigo}>Editar</a>
			<td><a href=clienteServlet?acao=excluir&codigo=${f.codigo}>Excluir</a>
		</tr>	
		</c:forEach>	
	</table>
</body>
</html>