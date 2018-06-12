<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='prescriptionForm' action='prescription' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h4>Rx and Symptom Details for
						${sessionScope.selectedCared.firstName}&nbsp;${sessionScope.selectedCared.lastName}</h4>
				</div>
				<div style="position: relative; left: 5%">
					<div style="padding: 3px 0 3px 0;">
						<span style="background-color: #CCCCCC"> &nbsp;Rx Taken (<font color="#008000"><i
								class="fa fa-check" aria-hidden="true"></i></font>) or Skipped (<font
							color="#ff0000"><i class="fa fa-times" aria-hidden="true"></i></font>)
						</span> <span style="left:65%; width:35%"> <a href="javascript:submitForm('DASHBOARD');"><i
								class="fa fa-tachometer" aria-hidden="true"></i>&nbsp; Back to Dashboard</a>
						</span>
					</div>
					<div style="align: center; background-color: #EEEEEE; width: 90%;">
						<table>
							<c:forEach var="cpRxAndSymptomsList" items="${rxAndSymptomsList}">
								<tr>
									<table style="width: 100%;">
										<tr
											style="background-color: #CCCCCC; padding: 2px 2px 10px 2px; border-spacing: 10px; border-collapse: separate;">
											<td colspan="2"><fmt:formatDate
													value="${cpRxAndSymptomsList.snapShotDate}"
													pattern="dd-MMM-YY" /></td>
										</tr>
										<!-- List Rx -->
										<c:forEach var="cpRxListKV"
											items="${cpRxAndSymptomsList.cpRxComplianceKV}">
											<tr style="background-color: #FFFFFF;">
												<td style="width: 20%; text-indent: 5px;"><c:out
														value="${f:h(cpRxListKV.key.drugName)}" /></td>
												<td style="width: 80%; text-indent: 5px;"><c:forEach
														var="cpRxList" items="${cpRxListKV.value}">
														<ul
															style="list-style-type: none; margin: 0; padding: 0; overflow: hidden;">
															<fmt:formatDate value="${cpRxList.rxScheduledTimestamp}"
																pattern="a" var="day_night" />

															<fmt:formatDate value="${cpRxList.rxScheduledTimestamp}"
																pattern="H" var="rx_time" />

															<li
																style="float: left; padding: 2px 8px 2px 2px;
																<c:choose>
																	<c:when test="${rx_time < 17}">color:orange;</c:when>
																	<c:when test="${rx_time > 17}">color:blue;</c:when>
																</c:choose>
																">

																<c:choose>
																	<c:when test="${rx_time < 17}">
																		<i class="fa fa-sun-o" aria-hidden="true"></i>
																	</c:when>
																	<c:when test="${rx_time > 17}">
																		<i class="fa fa-moon-o" aria-hidden="true"></i>
																	</c:when>
																</c:choose>
															</li>

															<li style="float: left; padding: 2px 8px 2px 2px;"><fmt:formatDate
																	value="${cpRxList.rxScheduledTimestamp}" pattern="hh a" /></li>
															<li style="float: left; padding: 2px 8px 2px 2px;"><c:choose>
																	<c:when test="${cpRxList.rxStatus == 'TAKEN'}">
																		<font color="#008000"><i class="fa fa-check"
																			aria-hidden="true"></i></font>
																	</c:when>
																	<c:when test="${cpRxList.rxStatus == 'SKIPPED'}">
																		<font color="#ff0000"><i class="fa fa-times"
																			aria-hidden="true"></i></font>
																	</c:when>
																	<c:when test="${cpRxList.rxStatus == ''}">
																		<font color="#ff0000"><i class="fa fa-times"
																			aria-hidden="true"></i></font>
																	</c:when>
																</c:choose></li>
														</ul>
													</c:forEach></td>
											</tr>
										</c:forEach>
										<tr>
											<td colspan='3'
												style="color: red; background-color: transparent;"><i
												class="fa fa-heartbeat" aria-hidden="true"></i>&nbsp;Symptoms
												reported</td>
										</tr>
										<!-- List Symptopms -->
										<tr>
											<td colspan="2">
												<ul
													style="list-style-type: none; margin: 0; padding: 0; overflow: hidden;">
													<c:forEach var="cpSympList"
														items="${cpRxAndSymptomsList.caredPersonSymptomLog}">
														<li style="float: left; padding: 2px 2px 2px 2px;">${f:h(cpSympList.preExistingDiseaseCommonSymptom.model.symptom)}</li>
													</c:forEach>
												</ul>
											</td>
										</tr>
									</table>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>