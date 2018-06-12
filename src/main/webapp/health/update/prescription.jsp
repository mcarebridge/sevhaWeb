<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='prescriptionForm' action='prescription' method='POST'>
		<input name='actionParam' type='hidden' value='UPDPRES' /> <input
			type='hidden' ${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h4>Update a Prescription
						${sessionScope.selectedCared.firstName}&nbsp;${sessionScope.selectedCared.lastName}</h4>
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
					<!--  
					<input type='submit' value='Update' /><a
						href="javascript:submitForm('LOADPRES')">&lt;&lt;List
						Rx&gt;&gt;</a>
					-->
					<h4>
						<a class="list-group-item" href="javascript:submitForm('UPDPRES')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
						<a class="list-group-item"
							href="javascript:submitForm('LOADPRES')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>
					</h4>
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
								<td>Physician</td>
								<td><select name='physician'>
										<option ${f:select("physician",S)}>Select</option>
										<c:forEach var="physician" items="${physicians}">
											<option ${f:select("physician", physician.key.id)}>${f:h(physician.firstname)}</option>
										</c:forEach>
								</select></td>
							</tr>
						</c:if>
						<c:if test="${fn:length(careGivers) eq 0}">
							<tr>
								<td colspan='2'>No cared person added</td>
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
						</c:if>
						<tr>
							<td>Prescription Tag</td>
							<td><input type="text" ${f:text("prescriptionName")} onchange="javascript:trimSpaces('prescriptionName')"/></td>
						</tr>
						<tr>
							<td colspan='2'>Medical Problem in brief (500 Chars)</td>
						</tr>
						<tr>
							<td colspan='2'><textarea cols='40' rows='4' maxlength='500'
									name='medicalCondition' onchange="javascript:trimSpaces('medicalCondition')">${requestScope.medicalCondition} </textarea>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>