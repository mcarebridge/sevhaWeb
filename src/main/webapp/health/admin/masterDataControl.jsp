<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='adminForm' action='adminData' method='POST'>
		<input name='actionParam' type='hidden' value='ADDMASTER' />
		<div id="main">
			<div class='logo'>
				<span>mCareBridge</span>
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
							<td>Entity Type</td>
							<td><input type="text" name="entityType" value="" /></td>
						</tr>
						<tr>
							<td>Master Data</td>
							<td colspan='1'><textarea name="masterdata" cols="50"
									rows='	20'></textarea></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" name="Add Data" /></td>
						</tr>
					</table>
				</div>
				<div>
					<a href="javascript:submitForm('DATACONTROL')">Back to Admin
						Data</a>
				</div>
			</div>
		</div>
	</form>
</body>
<%@include file="../common/footer.jsp"%>