<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredForm' action='cared' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div class='logout'>
				<span> <a href="javascript:submitForm('LOGOUT')">Logout</a>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id='contents'>
				<div class='loggedInAs'>Welcome&nbsp;
					${sessionScope.profile.firstname}&nbsp;${sessionScope.profile.lastname}!</div>

				<div>
					<div>
						<h3>Dashboard</h3>
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
						<span> <c:if test="${fn:length(caredPersons) eq 0}">
								<ul>
									<li>No cared person added. Please add a cared Person.</li>
								</ul>
							</c:if>
						</span>
						<c:if test="${fn:length(caredPersons) gt 0}">
							<span>Select a Cared Person</span>
							<span> <select name='careneedy'>
									<option ${f:select("careneedy",S)}>Select</option>
									<c:forEach var="caredOnes" items="${caredPersons}">
										<option ${f:select("careneedy", caredOnes.key.id)}>${f:h(caredOnes.firstName)}</option>
									</c:forEach>
							</select>

							</span>
						</c:if>
					</div>
					<div>
						<span></span><span> <a
							href="javascript:submitForm('GENDASHBOARD')">Get Health Card</a></span>
					</div>
					<div style="position: relative; left: 30%;">
						<h4>Vital Parameters</h4>
					</div>
					<div style="position: relative; left: 5%; top: 5%;">
						<table
							style="border-style: dotted; border-width: 1px; border-color: #CCCCCC;">
							<tr>
								<td colspan='3'>Rx Compliance</td>
							</tr>

							<c:forEach var="rxc" items="${requestScope.rxCompliance}">
								<tr>
									<td>${f:h(rxc.key)}</td>
									<td>${f:h(rxc.value)}%</td>
									<td style="width: 2em; background: red"></td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan='3'>Last Recorded Date : <fmt:formatDate
										pattern="YYYY-MMM-dd"
										value="${requestScope.vitalParameter.createdDate}"/>
									</td>
							</tr>
							<tr>
								<td>Last recorded Weight(Kgs)</td>
								<td>${requestScope.vitalParameter.weight}</td>
								<td></td>
							</tr>
							<tr>
								<td>BMI</td>
								<td><fmt:formatNumber type="number" maxFractionDigits="2"
										value="${bmi}" /></td>
								<td style="width: 2em; background: red"></td>
							</tr>
							<tr>
								<td>Temprature(F)</td>
								<td>${requestScope.vitalParameter.temprature}</td>
								<td></td>
							</tr>
							<tr>
								<td>Pulse(bpm)</td>
								<td>${requestScope.vitalParameter.pulse}</td>
								<td></td>
							</tr>
							<tr>
								<td>BP-High(mm Hg)</td>
								<td>${requestScope.vitalParameter.systolicPressure}</td>
								<td></td>
							</tr>
							<tr>
								<td>BP-Low(mm Hg)</td>
								<td>${requestScope.vitalParameter.diastolicPressure}</td>
								<td></td>
							</tr>


							<!--  
							<tr>
								<td>Rx Reaction</td>
								<td>Occured</td>
								<td style="width: 2em; background: red"></td>

							</tr>
							<tr>
								<td>BMI</td>
								<td>26</td>
								<td style="width: 2em; background: orange"></td>
							</tr>
							<tr>
								<td>Blood Sugar</td>
								<td>5.0 mmo/L</td>
								<td style="width: 2em; background: green"></td>
							</tr>
							<tr>
								<td>Blood Pressure</td>
								<td>80/120</td>
								<td style="width: 2em; background: green"></td>

							</tr>
							<tr>
								<td>Temprature</td>
								<td>98.4 F</td>
								<td style="width: 2em; background: green"></td>

							</tr>
							<tr>
								<td>hbCount</td>
								<td>13.0 / gm</td>
								<td style="width: 2em; background: green"></td>
							</tr>
							-->
						</table>
					</div>
					<div>
						<!--  <h4>Page Footer</h4> -->
					</div>
				</div>
			</div>

		</div>

		</div>
	</form>
</body>
<%@include file="../common/footer.jsp"%>