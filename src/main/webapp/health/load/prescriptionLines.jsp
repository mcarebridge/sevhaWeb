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
					<h3>Rx
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}</h3>
				</div>
				<div>
					<!--  
					<input type="button" value="Add Rx Line"
						onClick="javascript:submitForm('LDCRELINES')" /> <span><span><a
							href="javascript:submitForm('LOADPRES')">&lt;&lt;Your
								Rx&gt;&gt;</a></span>
					-->
					<h4>
						<a class="list-group-item"
							href="javascript:submitForm('LDCRELINES')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add Rx
							Line&nbsp;</a>&nbsp; <a class="list-group-item"
							href="javascript:submitForm('LOADPRES')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to Rx
							List</a>
					</h4>
				</div>
				<div>
					<c:if test="${requestScope.showErrBox == true}">
						<div class='errbox'>
							<div style='color: #003300'>* Missing information</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>
					</c:if>
					<div>
						<table
							style="position: relative; min-width: 50%; border-spacing: 2px; border: 1px; border-style: solid; border-color: #cccccc;">
							<tr>
								<td colspan='6'><div>Active Rx Lines for
										${f:h(prescription.prescriptionTag)}</div>
									<div>
										<font style="background: #FF5733">Substitute drug has been
											added</font>
									</div></td>
							</tr>
							<tr>
								<th>St. Date</th>
								<th>Drug</th>
								<th>When</th>
								<th>Expiry Date</th>
								<th>Edit</th>
								<th>Reorder</th>
							</tr>
							<c:if test="${fn:length(prescriptionLinesList) eq 0}">
								<tr style="background-color: #FFFFFF;">
									<td colspan='5'>No prescription lines found. Please click
										on 'Add Rx Line' to add.</td>
								</tr>
							</c:if>
							<c:forEach var="prescriptionLines"
								items="${prescriptionLinesList}">
								<tr
									style="background-color: #FFFFFF;
								<c:if test="${prescriptionLines.reordered == true}">
								color:#CCCCCC;
								</c:if>
								">
									<td><fmt:formatDate
											value="${prescriptionLines.rxstartdate}" pattern="dd-MMM-YY" /></td>
									<td
										style="padding: 5px;
										<c:if test="${f:h(prescriptionLines.substituteFound == true)}">
												background:red;
										</c:if> ">
										<c:if test="${prescriptionLines.rxExpired == false}">
											<a
												href="javascript:submitFormWithKey('LOADSUBS',${f:h(prescriptionLines.key.id)})">${f:h(prescriptionLines.drugName)}</a>
										</c:if> <c:if test="${prescriptionLines.rxExpired == true}">
											${f:h(prescriptionLines.drugName)}
										</c:if>
									</td>
									<td style="padding: 5px;">${f:h(prescriptionLines.frequencyForDisplay)}</td>
									<!--  <td style="text-align: right;">${f:h(prescriptionLines.duration)}Day(s)</td> -->
									<td style="padding: 5px;"><fmt:formatDate
											value="${prescriptionLines.rxEnddate}" pattern="dd-MMM-YY" /></td>

									<c:if test="${(prescriptionLines.stopRxLine == false)}">
										<c:choose>
											<c:when test="${prescriptionLines.rxExpired == true}">
												<c:if
													test="${(prescriptionLines.reordered == false) || (prescriptionLines.reordered == '')}">
													<td>Expired</td>
													<td><a class="list-group-item"
														href="javascript:submitFormWithKey('REORDLINES',${f:h(prescriptionLines.key.id)})">Reorder</a></td>
												</c:if>

												<c:if test="${prescriptionLines.reordered == true}">
													<td>Expired</td>
													<td>Reordered</td>
												</c:if>
											</c:when>

											<c:when test="${prescriptionLines.rxExpired == false}">
												<td style="padding: 5px;"><a
													href="javascript:submitFormWithKey('EDTLINES',${f:h(prescriptionLines.key.id)})">Update</a></td>
												<td></td>
											</c:when>
										</c:choose>
									</c:if>
									<c:if test="${(prescriptionLines.stopRxLine == true)}">
										<td style="color:#FF5733">Stopped</td>
										<td>No Reorder</td>
									</c:if>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>