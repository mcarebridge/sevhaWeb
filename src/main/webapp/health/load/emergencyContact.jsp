<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='emergencyContatctForm' action='emergencyContact'
		method='POST'>
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
						<i class="icon-i-emergency  fa-2x" aria-hidden="true"></i>&nbsp;List
						Emergency Contact
					</h3>
				</div>
				<div style="position: relative; left: 5%; right: 5%; width: 90%">
					<div>
						<!--  
					<input type="button" value="Add Contact"
						onClick="javascript:submitForm('NEWEMRCNT')" />
					-->
						<h4>
							<a class="list-group-item"
								href="javascript:submitForm('NEWEMRCNT')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add Contact</a>
						</h4>
						<div
							style="position: relative; width: 70%; background: transparent; font-size: 0.85em;">
							<span style="position: relative; left: 5%; color: red;"><i
								class="fa fa-hand-o-right" aria-hidden="true"></i></span>
							<span style="position: relative; left: 5%">&nbsp;Note:</span>
							<div style="position: relative; left: 10%;">
								<span><i class="fa fa-envelope-o" aria-hidden="true"></i>&nbsp;Click
									on email to send Cared Person's stored medical history
									and address details to the emergency contact.</span>
							</div>
							<div style="position: relative; left: 10%;">
								<span><i class="fa fa fa-phone" aria-hidden="true"></i>&nbsp;Click
									on emergency phone numbers to dial.</span>
							</div>
						</div>
					</div>
					<div style="position: relative; top: 10px">
						<c:if test="${fn:length(emergencyContactList) eq 0}">
							<ul>
								<li>No Emergency Contact Added</li>
							</ul>
						</c:if>
						<c:if test="${fn:length(emergencyContactList) gt 0}">
							<table>
								<tr style="border-bottom: 1px dashed #7eb9d0;">
									<th style="border-bottom: 1px solid #7eb9d0; width: 20%">Cared
										Person</th>
									<th style="border-bottom: 1px solid #7eb9d0; width: 80%">Emergency
										Details</th>
								</tr>
								<c:forEach var="emerCntList" items="${emergencyContactList}">
									<tr>
										<td style="border: 1px solid #7eb9d0; width: 20%">${f:h(emerCntList.key.firstName)}
											${f:h(emerCntList.key.lastName)}</td>
										<td style="border: 1px solid #7eb9d0; width: 80%">
											<table style="width: 100%">
												<tr>
													<td style="border: 0px dashed #7eb9d0; width: 30%">Hospital
														/ Clinic:</td>
													<td style="border: 0px dashed #7eb9d0; width: 70%">${f:h(emerCntList.value.providerName)}</td>
												</tr>
												<tr>
													<td>Contact Person:</td>
													<td>${f:h(emerCntList.value.contactPerson)}</td>
												</tr>
												<tr>
													<td>Address:</td>
													<td>${f:h(emerCntList.value.address.model.streetAddress)}</td>
												</tr>
												<tr>
													<td>City:</td>
													<td>${f:h(emerCntList.value.address.model.city)}</td>
												</tr>
												<tr>
													<td style="color: red;"><i class="fa fa-phone"
														aria-hidden="true"></i>&nbsp;Emergency Contact:</td>
													<td><a
														href="tel:${f:h(emerCntList.value.address.model.cellphone)}">${f:h(emerCntList.value.address.model.cellphone)}</a>
													</td>
												</tr>
												<tr>
													<td style="color: red;"><i class="fa fa-envelope-o"
														aria-hidden="true"></i>&nbsp;Emergency Email:</td>
													<td><a href="mailto:"
														${f:h(emerCntList.value.address.model.email)}>${f:h(emerCntList.value.address.model.email)}</a>
												</tr>
												<%-- 
											<tr>
												<td>Land Line:</td>
												<td>${f:h(emerCntList.value.address.model.fixedLine)}</td>
											</tr>
											--%>
											</table>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>