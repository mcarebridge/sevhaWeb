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
					<h3>
						<i class="icon-i-pharmacy  fa-2x" aria-hidden="true"></i>&nbsp;Active
						prescription(s) for
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}
					</h3>
					<span style="left: 10%;">
						<h5>Add Prescription and prescription lines for each clinical
							visit</h5>
					</span>
				</div>
				<c:if test="${sessionScope.physicianFound eq false}">
					<Label style='color: red; text-align: center'>No registered
						Physician found. Please add at least one Physician</Label>
				</c:if>
				<c:if test="${sessionScope.physicianFound eq true}">
					<div>
						<h4>
							<a class="list-group-item"
								href="javascript:submitForm('NEWPRES')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add
								Rx&nbsp;</a>&nbsp;
						</h4>
					</div>
					<div>
						<table
							style="position: relative; min-width: 50%; border-spacing: 2px; border: 1px; border-style: solid; border-color: #cccccc;">

							<tr>
								<th>Rx for</th>
								<th>Edit Rx</th>
								<th>Given Rx Line(s)</th>
							</tr>
							<c:if test="${fn:length(prescriptionList) eq 0}">
								<tr>
									<td colspan='3' style="text-align: center">No
										prescriptions found for
										${sessionScope.selectedCared.firstName}&nbsp;${sessionScope.selectedCared.lastName}
									</td>
							</c:if>

							<c:if test="${fn:length(prescriptionList) gt 0}">
								<c:forEach var="prescription" items="${prescriptionList}">
									<tr style="background-color: #FFFFFF;">
										<td style="padding: 5px;">${f:h(prescription.prescriptionTag)}</td>
										<td style="padding: 5px;"><a
											href="javascript:submitFormWithKey('LDUPDPRES',${f:h(prescription.key.id)})">EDIT</a></td>
										<td style="padding: 5px;"><a
											href="javascript:submitFormWithKey('PRESLINES',${f:h(prescription.key.id)})">+
												Rx Lines</a></td>
									</tr>
								</c:forEach>
							</c:if>
						</table>
					</div>
				</c:if>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>