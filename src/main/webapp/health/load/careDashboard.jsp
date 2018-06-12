<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredForm' action='cared' method='POST'">
		<input name='actionParam' type='hidden' /> <input
			name='selectedCaredId' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div>
				<%@include file="../common/mobileMenu.jsp"%>
				<div class='logo'>
					<span>sevhā</span>
				</div>
			</div>

			<div id='contents1'>
				<div class='loggedInAs'>
					<span><i class="fa fa-user-o" aria-hidden="true"></i></span> <span>
						Welcome&nbsp;
						${sessionScope.profile.firstname}&nbsp;${sessionScope.profile.lastname}!
						<%-- Time offset by :${sessionScope.timezoneoffset}&nbsp mins. --%>
				</div>
				</span>
				<div>
					<div id="subHeader">
						<span class="fa fa-tachometer fa-2x" aria-hidden="true"></span>&nbsp;Wellness
						Dashboard
					</div>
					<div
						style="position: relative; padding-top: 10px; background-color: #transparent;">
						<c:if test="${requestScope.showErrBox == true}">
							<div id='errbox'>
								<div style='color: #003300'>* Missing information</div>
								<c:forEach var="e" items="${f:errors()}">
									<li>${f:h(e)}</li>
								</c:forEach>
							</div>
						</c:if>
						<div
							style="position: relative; padding: 10px 10px 0 5px; left: 4%; right: 6%; width: 80%; border: 1px; border-style: dotted; height: 3em; border-radius: 5px 5px 5px 5px; background-color: #E0F8D8;">
							<div>
								<c:if test="${fn:length(caredPersons) eq 0}">
									<span style="left: 5%; padding: 3px;">No Cared Person
										Found.</span>
								</c:if>
							</div>
							<div>
								<c:if test="${fn:length(caredPersons) gt 0}">
									<span
										style="left: 5%; background-color: #FFDB4C; border: 2px; border-style: solid; border-color: #BBBBBB; padding: 3px; border-radius: 4px;"><a
										href="javascript:submitForm('GENDASHBOARD')"><i
											class="fa fa-cogs" aria-hidden="true"></i>&nbsp;Show </a> </span>
									<span style="width: 10px; background: transparent;">&nbsp;</span>
									<span style="background-color: transparent; padding: 3px;">
										<select name='careneedy' onchange="setCPtoSession()">
											<option value='S'>Select</option>
											<c:forEach var="caredOnes" items="${caredPersons}">
												<option ${f:select("careneedy", caredOnes.key.id)}
													${caredOnes.key.id == sessionScope.selectedCaredId ? 'selected' : ''}>

													${f:h(caredOnes.firstName)}</option>
											</c:forEach>
									</select>
									</span>
								</c:if>
							</div>
						</div>

						<div
							style="position: relative; left: 4%; padding: 0 0 0 0; right: 0%; width: 82%; background-color: #FFFFFF; border-radius: 5px 5px 5px 5px;">
							<h4
								style="position: relative; left: 0%; top: 0%; align: left; background-color: transparent; width: 80%;">Health
								Scorecard</h4>
						</div>
						<div
							style="position: relative; left: 2%; padding: 5px 0 0 0; right: 5%; width: 90%; background-color: transparent; border-radius: 5px 5px 5px 5px;">
							<h4
								style="position: relative; left: 2%; top: 10%; align: left; background-color: transparent; width: 90%;">Rx
								Compliance & Vital Signs of
								${sessionScope.selectedCared.firstName}</h4>
							<div
								style="position: relative; bottom: 10%; width: 100%; background-color: transparent;">
								<table
									style="width: 100%; border-collapse: separate; border-spacing: 2px; border: 1px; border-style: dashed; border-color: #cccccc;">
									<c:forEach var="rxc" items="${requestScope.rxCompliance}">
										<tr>
											<td>${f:h(rxc.key)}</td>
											<td>${f:h(rxc.value)}%</td>
											<td style="width: 2em; background: red"></td>
										</tr>
									</c:forEach>
									<tr>
										<td colspan='3' style="text-align: left;"><span
											class="icon-i-family-practice fa-2x" aria-hidden="true"></span>&nbsp;Rx
											Compliance during last 7 days</td>
									</tr>
									<tr>
										<th>Prescription</th>
										<th>Compliance</th>
										<th>Health Impact</th>
									</tr>

									<c:forEach var="rxcomplianceData"
										items="${requestScope.rxcomplianceData}">
										<tr style="border: 1px; border-style: solid;">
											<td style="width: 50%; border: 0px; border-style: solid;">${f:h(rxcomplianceData.complianceItem)}</td>
											<td style="width: 25%; border: 0px; border-style: solid;">${f:h(rxcomplianceData.complianceValue)}&nbsp;${f:h(rxcomplianceData.unit)}</td>
											<td
												style="width:25%; color : ${f:h(rxcomplianceData.alertIndicator)}">
												<c:choose>
													<c:when test="${rxcomplianceData.alertIndicator == 'RED'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;RED
													</c:when>
													<c:when
														test="${rxcomplianceData.alertIndicator == 'GREEN'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;GREEN
													</c:when>
													<c:when
														test="${rxcomplianceData.alertIndicator == 'ORANGE'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;AMBER
													</c:when>
												</c:choose>
											</td>
										</tr>
									</c:forEach>
									<c:choose>
										<c:when test="${fn:length(requestScope.rxcomplianceData) > 0}">
											<tr>
												<td colspan='3' style="text-align: right"><a
													href="javascript:submitForm('RXSYMPDTLS')">Click here
														for Details</a></td>
											</tr>
										</c:when>
										<c:when
											test="${fn:length(requestScope.rxcomplianceData) == 0}">
											<tr>
												<td colspan='3'>No Rx Data to display</td>
											</tr>
										</c:when>
									</c:choose>
									<tr>
										<td colspan='3' style="text-align: left;"><span
											class="icon-i-diabetes-education fa-2x" aria-hidden="true"></span>&nbsp;Rx
											Symptoms Reported Today</td>
									</tr>
									<!-- List Symptopms -->
									<tr>
										<th colspan='3'>Symptom</th>
									</tr>

									<c:choose>
										<c:when test="${fn:length(cpSymtomLogList) == 0}">
											<tr>
												<td colspan='3'>No Symptoms are reported</td>
											</tr>
										</c:when>
										<c:when test="${fn:length(cpSymtomLogList) > 0}">
											<c:forEach var="cpSympLog"
												items="${requestScope.cpSymtomLogList}">
												<tr>
													<td colspal='3'>
														${f:h(cpSympLog.preExistingDiseaseCommonSymptom.model.symptom)}
													</td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>
									<c:if test="${fn:length(cpSymtomLogList) > 0}">
										<tr>
											<td colspan='3'
												style="width: 2em; color: ${f:h(symptomAlertLevel)}"><c:choose>
													<c:when test="${symptomAlertLevel == 'RED'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;RED. The Symptoms reported by ${sessionScopes.selectedCared.firstName} need immdiate attention. Please consult your family physician or rush to emergency.
												</c:when>
													<c:when test="${symptomAlertLevel == 'ORANGE'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;AMBER. Please connect with ${sessionScopes.selectedCared.firstName} and if needed consult family physician
												</c:when>
													<c:when test="${symptomAlertLevel == 'GREEN'}">
														<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;GREEN. Mild Symptoms reported. Please check Vital Parameters.
												</c:when>
												</c:choose></td>
										</tr>
									</c:if>
									<tr>
										<td colspan='1' style="text-align: left;"><span
											class="icon-cardiology fa-2x" aria-hidden="true"></span>&nbsp;Vital
											Sign Compliance</td>
										<td colspan="2">Last Recorded Date : <fmt:formatDate
												pattern="MMM,dd YYYY hh:mm a"
												value="${requestScope.vitalParameter.createdDate}" /></td>
									</tr>
									<tr>
										<td colspan="3" style="width: 2em; color: ORANGE"><i
											class="fa fa-circle" aria-hidden="true"></i>&nbsp; AMBER
											:Please connect with Cared Person and if needed consult
											family physician</td>
									</tr>
									<tr>
										<td colspan="3" ; style="width: 2em; color: RED"><i
											class="fa fa-circle" aria-hidden="true"></i>&nbsp; RED :
											Please visit nearest EMERGENCY</td>
									</tr>
									<tr>
										<th style="width: 50%; border: 0px; border-style: solid;">Vital
											Sign</th>
										<th style="width: 25%; border: 0px; border-style: solid;">Measured
											Value</th>
										<th style="width: 25%; border: 0px; border-style: solid;">Health
											Impact</th>
									</tr>


									<c:choose>
										<c:when
											test="${fn:length(requestScope.vitalcomplianceData) == 0}">
											<tr>
												<td colspan='3'>No Vital Data is added.</td>
											</tr>
											<tr>
												<td colspan='3' style="text-align: right"><a
													href="javascript:submitForm('VITALPARA')">Click here
														to add Vital Details</a></td>
											</tr>
										</c:when>
										<c:when
											test="${fn:length(requestScope.vitalcomplianceData) > 0}">
											<c:forEach var="vitalcomplianceData"
												items="${requestScope.vitalcomplianceData}">
												<tr>
													<td>${f:h(vitalcomplianceData.complianceItem)}</td>
													<td>${f:h(vitalcomplianceData.complianceValue)}&nbsp;${f:h(vitalcomplianceData.unit)}</td>
													<td
														style="width: 2em; color: ${f:h(vitalcomplianceData.alertIndicator)}">
														<c:choose>
															<c:when
																test="${vitalcomplianceData.alertIndicator == 'RED'}">
																<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;RED
													</c:when>
															<c:when
																test="${vitalcomplianceData.alertIndicator == 'GREEN'}">
																<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;GREEN
													</c:when>
															<c:when
																test="${vitalcomplianceData.alertIndicator == 'ORANGE'}">
																<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;AMBER
													</c:when>

															<c:when
																test="${vitalcomplianceData.alertIndicator == 'GRAY'}">
																<i class="fa fa-circle" aria-hidden="true"></i>&nbsp;N/A
													</c:when>
														</c:choose>

													</td>
												</tr>
											</c:forEach>

										</c:when>
									</c:choose>

								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>