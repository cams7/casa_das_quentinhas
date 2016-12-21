<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Casa das Marmitas</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/">Início</a></li>

				<li><a href="<c:url value='/cliente/list' />">Clientes</a></li>
				<li><a href="<c:url value='/pedido/list' />">Pedidos</a></li>
				<li><a href="<c:url value='/produto/list' />">Produtos</a></li>
				<li><a href="<c:url value='/empresa/list' />">Empresas</a></li>
				<li><a href="<c:url value='/entregador/list' />">Entregadores</a></li>
				<li><a href="<c:url value='/taxa/list' />">Taxas</a></li>
				<li><a href="<c:url value='/funcionario/list' />">Funcionários</a></li>
				<li><a href="<c:url value='/usuario/list' />">Usuários</a></li>

				<!--li><a href="#">Opções</a></li>
        <li><a href="#">Perfil</a></li>
        <li><a href="#">Ajuda</a></li-->
				<li><a href="#"><span class="glyphicon glyphicon-log-in"></span>
						Login</a></li>
			</ul>
		</div>
	</div>
</nav>