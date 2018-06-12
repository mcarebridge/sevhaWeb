<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='alertForm' action='alert' method='POST'>
		<input name='actionParam' type='hidden' />
		<div
			style="width: auto; height: auto; margin: 0 auto; align: center; background-color: transparent; border-style: none; border-width: 1px 1px 1px 1px;">
			<div id='body'>
				<div>
					<h3>Manage Alerts</h3>
				</div>
				<div>
					<div>
						<table>
							<tr>
								<td>Alert &gt; Blood Pressure</td>
								<td><a href="javascript:submitForm('NEWALRT')">Create</a> /
									<a href="javascript:submitForm('LDUPDALRT')">Edit</a></td>
							</tr>
							<tr>
								<td>Alert &gt; Diabetes</td>
								<td><a href="javascript:submitForm('NEWALRT')">Create</a> /
									<a href="javascript:submitForm('LDUPDALRT')">Edit</a></td>
							</tr>
							<tr>
								<td>Alert &gt; Stress</td>
								<td><a href="javascript:submitForm('NEWALRT')">Create</a> /
									<a href="javascript:submitForm('LDUPDALRT')">Edit</a></td>
							</tr>
						</table>
					</div>
					<div>
						<a href="javascript:submitForm('DASHBOARD')">Dashboard</a>
					</div>
				</div>
			</div>
	</form>
<%@include file="../common/footer.jsp"%>