<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ContasApp</title>
<!-- Folhas de estilo CSS -->
<link rel="stylesheet" href="../resources/css/bootstrap.min.css" type="text/css" />
<!-- estilo CSS para as mensagens de erro de validação -->
<style>
	label.error { color: #d9534f; } /* cor do texto da mensagem de erro */
	input.error { border: 2px solid #d9534f; } /* cor (erro) nos campos input text */
</style>
</head>
<body>
	<!-- componente navbar -->
	<jsp:include page="/WEB-INF/views/admin/components/navbar.jsp"></jsp:include>
	
	<!-- componente messages -->
	<jsp:include page="/WEB-INF/views/admin/components/messages.jsp"></jsp:include>
	
	<div class="container mt-4">
		<div class="card">
			<div class="card-body">
				<h4>Dashboard Principal</h4>
				<p>Resumo de contas no período de datas:</p>
				<hr/>
				
				<form id="formConsulta">
					<div class="row mb-2">
						<div class="col-md-3">
							<input type="date" id="dataInicio" name="dataInicio"
								class="form-control" value="${data_inicio}"/>
						</div>
						<div class="col-md-3">
							<input type="date" id="dataFim" name="dataFim"
								class="form-control" value="${data_fim}"/>
						</div>
						<div class="col-md-6">
							<input type="submit" value="Pesquisar Contas" class="btn btn-success"/>
						</div>
					</div>
				</form>
				
				<div class="row mt-2">
					<div class="col-md-6">
					
						<div class="table-responsive">
							<table class="table table-sm table-striped table-bordered">
								<thead>
									<tr>
										<th>Data</th>
										<th>Valor</th>
										<th>Tipo</th>
										<th>Nome da conta</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${listagem_contas}" var="conta">
										<tr>
											<td>
												<fmt:formatDate value="${conta.data}" pattern="EEE dd/MM/yyyy"/>
											</td>
											<td>
												<fmt:formatNumber value="${conta.valor}" type="currency" currencySymbol="R$"/>
											</td>
											<td>
												<c:if test="${conta.tipo == 1}">
													<span class="badge bg-success">RECEBER</span>
												</c:if>
												<c:if test="${conta.tipo == 2}">
													<span class="badge bg-danger">PAGAR</span>
												</c:if>
											</td>
											<td>
												${conta.nome}
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="4">
											Quantidade de contas: ${listagem_contas.size()}
										</td>
									</tr>
								</tfoot>								
							</table>
						</div>
					
					</div>
					<div class="col-md-6">
						<div id="grafico"></div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<!-- Arquivos JavaScript -->
	<script src="../resources/js/bootstrap.bundle.min.js"></script>
	<script src="../resources/js/jquery-3.7.0.min.js"></script>
	<script src="../resources/js/jquery.validate.min.js"></script>
	<script src="../resources/js/additional-methods.min.js"></script>
	<script src="../resources/js/messages_pt_BR.min.js"></script>
	<script src="../resources/js/highcharts.js"></script>
	<script>		
		$(document).ready(function(){	
			
			$("#formConsulta").validate({
				rules: {
					"dataInicio": {
						required: true
					},
					"dataFim": {
						required: true
					}
				}
			});	
			
			 Highcharts.chart('grafico', {
			 chart: {
			 type: 'pie'
			 },
			 title: {
			 text: 'Total de Receitas e Despesas'
			 },
			 plotOptions: {
			 pie: {
			 innerSize: '60%',
			 dataLabels: {
			 enabled: true,
			 format: '<b>{point.name}</b>: {point.y:.2f}'
			 }
			 }
			 },
			 series: [{
			 name: 'Valores',
			 data: [
			 {
			 name: 'Receitas',
			 y: ${total_receitas},
			 color: '#66bb6a'
			 },
			 {
			 name: 'Despesas',
			 y: ${total_despesas},
			 color: '#ef5350'
			 }
			 ]
			 }]
			 });			
		})
	</script>
</body>
</html>



