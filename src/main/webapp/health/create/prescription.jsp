<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='prescriptionForm' action='prescription' method='POST'>
		<input name='actionParam' type='hidden' value='CREPRES' /> <input
			type='hidden' ${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Rx for
						${sessionScope.selectedCared.firstName}&nbsp;${sessionScope.selectedCared.lastName}</h3>
				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<div id='errbox'>
						<div style='color: #003300'>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
				</c:if>
				<div>
					<h4>
						<c:choose>
							<c:when test = "${fn:length(careGivers) eq 0}">
								Add						
							</c:when>
							<c:when test = "${fn:length(careGivers) gt 0}">
								<a class="list-group-item"
								href="javascript:submitForm('CREPRES')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>
							</c:when>
						</c:choose>

						<a class="list-group-item"
							href="javascript:submitForm('LOADPRES')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>&nbsp;
					</h4>
					<div
						style="left: 5%; width: 50%; padding: 10 px; background-color: #FFFFFF; color: red;">
						<span>Note : Date of Rx start should not be later than 1
							day from Today</span>
					</div>
				</div>
				<div>
					<table>
						<c:if test="${fn:length(physicians) eq 0}">
							<tr>
								<td colspan='2'>No Physician added</td>
							</tr>
						</c:if>
						<c:if test="${fn:length(physicians) gt 0}">
							<tr>
								<td>Date of Rx</td>
								<td><input type="text" id="datepicker"
									${f:text("dateofvisit")}></td>
							</tr>
							<tr>
								<td>Physician</td>
								<td><select name='physician'>
										<option ${f:select("physician",S)}>Select</option>
										<c:forEach var="physician" items="${physicians}">
											<option ${f:select("physician", physician.key.id)}>${f:h(physician.firstname)}-${f:h(physician.physicianSpeciality.model.speciality)}</option>
										</c:forEach>
								</select></td>
							</tr>

							<c:if test="${fn:length(careGivers) eq 0}">
								<tr>
									<td colspan='2'>No Care-givers added</td>
								</tr>
							</c:if>

							<c:if test="${fn:length(careGivers) gt 0}">
								<tr>
									<td>Care Giver</td>
									<td><select name='caregiver'>
											<option ${f:select("caregiver",S)}>Select</option>
											<c:forEach var="caregiver" items="${careGivers}">
												<option ${f:select("caregiver", caregiver.key.id)}>${f:h(caregiver.firstName)}</option>
											</c:forEach>
									</select></td>
								</tr>

								<tr>
									<td>Prescription Tag</td>
									<td><input type="text" ${f:text("prescriptionName")} onchange="javascript:trimSpaces('prescriptionName')"/></td>
								</tr>
								<tr>
									<td colspan='2'>Caregiver Notes : Medical condition in
										brief (500 Chars)</td>
								</tr>
								<tr>
									<td colspan='2'><textarea cols="37" rows='4'
											maxlength='500' ${f:text("medicalCondition")} onchange="javascript:trimSpaces('medicalCondition')"></textarea></td>
								</tr>
							</c:if>
						</c:if>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>