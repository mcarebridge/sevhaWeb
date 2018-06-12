<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='' action='POST'>
		<input name='actionParam' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Substitute Drug</h3>
				</div>
				<div>
					<table>
						<tr>
							<td>Substitute Drug</td>
							<td><input name='substitute' type='text' value='Substitute'
								length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('substitute')"/></td>
						</tr>
						<tr>
							<td>Strength</td>
							<td><input name='strength' type='text' value='Strength'
								length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('strength')"/></td>
						</tr>
						<tr>
							<td>Reason for using substitute</td>
							<td><select name='subsreason'><option value='0'>Select</option>
									<option value='1'>Not available</option>
									<option value='2'>Cost of Prescribed drug</option>
									<option value='3'>Suggested by Drugist</option>
							</select></td>
						</tr>
						<tr>
							<td>Substitution Suggested by</td>
							<td><select name='suggestedBy'><option value='0'>Select</option>
									<option value='1'>Pharmacy</option>
									<option value='2'>Original Physician</option>
									<option value='3'>Other Physician</option>
									<option value='4'>Self</option>
									<option value='5'>Others</option>
							</select></td>
						</tr>
					</table>
				</div>
				<div>
					<span><a href="/health/substitute?actionParam=CRESUBS">Update
							Substitute</a></span> <span><a href="/health/dashboard">Dashboard</a></span>
				</div>
			</div>
		</div>
	</form>
<%@include file="../common/footer.jsp"%>