<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html
<html>
<head>
<meta charset="UTF-8">
<title>Estoque</title>
<link rel="icon" href="imagens/favicon.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Editar produto</h1>
	<form action="update" name="frmProduto">
		<table>
			<tr>
				<td><input type="text" name="id" id="caixa3"
					readonly="readonly"
					value="<%out.print(request.getAttribute("id"));%>"></td>
			</tr>
			<tr>
				<td><input type="text" name="nome" class="caixa1"
					value="<%out.print(request.getAttribute("nome"));%>"></td>
			</tr>
			<tr>
				<td><input type="number" name="quantidade" class="caixa2"
					value="<%out.print(request.getAttribute("quantidade"));%>"></td>
			</tr>
			<tr>
				<td><input type="number" name="valor" class="caixa2"
					value="<%out.print(request.getAttribute("valor"));%>"></td>
			</tr>
		</table>
		<input type="button" value="Salvar" class="botao" onclick="validar()">
	</form>

	<script type="text/javascript" src="scripts/validador.js"></script>
</body>
</html>