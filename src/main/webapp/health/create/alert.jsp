<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='alertForm' action='alert' method='POST'>
		<input name='actionParam' type='hidden' />
		<div>
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div class='logout'>
				<span> <a href="javascript:submitForm('LOGOUT')">Logout</a>
			</div>
			<div id='body'>
				<div>
					<h3>Manage Alerts</h3>
				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<div class='errbox'>
						<div style='color: #003300'>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
				</c:if>
				<div>
					<table>
						<tr>
							<td colspan='2'>Add a Alert</td>
						<tr>
						<tr>
							<td colspan='2'>
								<table>
									<tr>
										<td>Self</td>
										<td><input type='checkbox' ${f:text("caregiver")} /></td>
									</tr>
									<tr>
										<td>Tim</td>
										<td><input type='checkbox' ${f:text("caregiver")} /></td>
									</tr>
									<tr>
										<td>Lisa</td>
										<td><input type='checkbox' ${f:text("caregiver")} /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:submitForm('CREALRT')">Create Alert</a>
				</div>
				<div>
					<a href="javascript:submitForm('LISTALRT')">List Alerts</a>
				</div>
			</div>
		</div>
	</form>
	<div id="footer">
		<div
			style="position: relative; top: 30%; left: 25%; right: 25%; width: 50%; text-align: center;">visit
			us at www.mcarebridge.com</div>
	</div>
<%@include file="../common/footer.jsp"%>