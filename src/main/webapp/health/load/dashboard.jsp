<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='dashboardForm' action='dashboard' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div class='logo'>
			<span>sevhā</span>
		</div>
		<%@include file="../common/mobileMenu.jsp"%>
		<div class='loggedInAs'>Logged in as-
			${sessionScope.profile.firstname}&nbsp;${sessionScope.profile.lastname}</div>
		<div style="z-index: 2; position: relative">
			<div>
				<h3>Dashboard</h3>
				<%-- 				
					<h4>Cared Person selected
						:${sessionScope.selectedCared.key.id}&nbsp;
						${sessionScope.selectedCared.firstName}&nbsp;${sessionScope.selectedCared.lastName}</h4>
						--%>
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
				<c:if test="${fn:length(caredPersons) eq 0}">
					<ul>
						<li>No cared person added</li>
					</ul>
				</c:if>
				<label>Select a Cared Person</label>
				<c:if test="${fn:length(caredPersons) gt 0}">
					<select name='careneedy'>
						<option ${f:select("careneedy",S)}>Select</option>
						<c:forEach var="caredOnes" items="${caredPersons}">
							<option ${f:select("careneedy", caredOnes.key.id)}>${f:h(caredOnes.firstName)}</option>
						</c:forEach>
					</select>
				</c:if>
			</div>
			<div>
				<table>
					<tr>
						<td><a href="javascript:submitForm('MNGPROFILE');">Manage
								Profile</a></td>
						<td></td>
					</tr>
					<tr>
						<td><a href="javascript:submitForm('LISTPHY');">Active
								Physician(s)</a></td>
						<td></td>
					</tr>
					<tr>
						<td><a href="javascript:submitForm('LOADPRES')">Active
								Prescription(s)</a></td>
						<td></td>
					</tr>
					<tr>
						<td><a href="javascript:submitForm('LOADCAREGVR')">Active
								Care giver(s)</a></td>
						<td></td>
					</tr>
					<!--  
						<tr>
							<td><a href="javascript:submitForm('LOADCAREALR')">Care
									Alerts</a></td>
							<td>2</td>
						</tr>
						-->
					<tr>
						<td><a href="javascript:submitForm('LOADCARED')">Manage
								Cared Person(s)</a></td>
						<td></td>
					</tr>

				</table>
			</div>
			<div>
				<!--  <h4>Page Footer</h4> -->
			</div>
		</div>
		</div>
	</form>
<%@include file="../common/footer.jsp"%>