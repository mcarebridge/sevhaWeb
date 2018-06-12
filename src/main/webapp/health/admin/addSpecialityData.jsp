<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='adminForm' action='adminData' method='POST'>
		<input name='actionParam' type='hidden' value='ADDSPECIALITY' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div id="contents">
				<div>
					<h3>Create Speciality</h3>
				</div>
				<div></div>
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
							<td>Speciality</td>
							<td><input type="text" name="speciality" value=""/></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="Add Data"/></td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:submitForm('DATACONTROL')">Back to Admin Data</a>
				</div>
			</div>
		</div>
	</form>
</body>
<%@include file="../common/footer.jsp"%>