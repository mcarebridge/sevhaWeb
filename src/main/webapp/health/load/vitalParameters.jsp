<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='vitalParametersForm' action='vitalParameters' method='POST'>
		<input name='actionParam' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>List Vital Parameters for ${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}</h3>
				</div>
				<div style="position: relative; left: 5%">
					<div>
						<c:if test="${requestScope.showErrBox == true}">
							<div id='errbox'>
								<div style='color: #003300'>* Missing information</div>
								<c:forEach var="e" items="${f:errors()}">
									<li>${f:h(e)}</li>
								</c:forEach>
							</div>
						</c:if>
					</div>
					<div>
						<div style="position: relative;">
							<table>
								<tr>
									<td>Last recorded Weight(Kgs)</td>
									<td>${f:h(lastTakenWeight)}</td>
								</tr>
								<tr>
									<td>Last recorded Height(cms)</td>
									<td>${f:h(lastTakenHeight)}</td>
								</tr>
								<tr>
									<td>Your BMI</td>
									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											value="${bmi}" /></td>
								</tr>
							</table>
						</div>
						<div>Last 7 recorded readings</div>
						<div>
							<table style="background-color: transparent;">
								<tr>
									<th>Date</th>
									<th>Temp(F)</th>
									<th>Pulse(bpm)</th>
									<th>BP(mm Hg)</th>
									<th>Blood Sugar(mg/dl)</th>
									<th>Blood Sugar Fasting(mg/dl)</th>
								</tr>
								<%
									int i = 0;
								%>
								<c:forEach var="vitalList" items="${vitalParaList}">
									<tr style="background-color: #FFFFFF">
										<td><fmt:formatDate pattern="MMM dd yy, hh:mm a "
												value="${vitalList.createdDate}" /></td>
										<!--  <td>${f:h(vitalList.height)}</td>-->
										<!--  <td>${f:h(vitalList.weight)}</td> -->
										<td style="text-align: right;">${f:h(vitalList.temprature)}&nbspF</td>
										<td style="text-align: right;">${f:h(vitalList.pulse)}</td>
										<td style="text-align: right;">${f:h(vitalList.systolicPressure)}/
											${f:h(vitalList.diastolicPressure)}</td>
										<td style="text-align: right;">${f:h(vitalList.bloodSugar)}</td>
										<td style="text-align: right;">${f:h(vitalList.bloodSugarFasting)}</td>
									</tr>
									<%
										i++;
									%>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>