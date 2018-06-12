<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='alert' action='alert' method='POST'>
		<input name='actionParam' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div>
				<div>
					<h3>Update Alerts</h3>
				</div>
				<div>
					<table>
						<tr>
							<td colspan='2'>Update Alert</td>
						<tr>
						<tr>
							<td colspan='2'>
								<table>
									<tr>
										<td>Self</td>
										<td><input name='_self' type='checkbox' value='' checked /></td>
									</tr>
									<tr>
										<td>Tim</td>
										<td><input name='_cg001' type='checkbox' value='' checked /></td>
									</tr>
									<tr>
										<td>Lisa</td>
										<td><input name='_cg002' type='checkbox' value='' /></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:submitForm('UPDALRT')">Update Alert</a>
				</div>
				<div>
					<a href="javascript:submitForm('LISTALRT')">List Alerts</a>
				</div>
			</div>
		</div>
	</form>
<%@include file="../common/footer.jsp"%>