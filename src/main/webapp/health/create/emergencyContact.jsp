<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body onLoad="checkERStatus()";>
	<form name='caredForm' action='emergencyContact' method='POST'>
		<input name='actionParam' type='hidden' /> <input
			name='selectedPreCondition' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents" style="height: 100%;">
				<div>
					<h3>Enter Emergency Contact</h3>
				</div>
				<div>
					<h5>Note : Add one emergency contact for Cared Person</h5>
				</div>
				<div>
					<div
						style="position: relative; left: 5%; height: 2em; font-size: 0.95em;">
						<span> <c:if test="${fn:length(caredPersonERStatus) eq 0}">
								<ul>
									<li>No cared person added</li>
								</ul>
								<h4>
									<a class="list-group-item"
										href="javascript:submitForm('NEWCAD')"><i
										class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add
										a Cared&nbsp;</a>&nbsp;
								</h4>
							</c:if>
						</span>
						<c:if test="${fn:length(caredPersonERStatus) gt 0}">
							<span> Select a Cared Person <select name='careneedy'
								onChange="checkERStatus()";>
									<%-- <option ${f:select("careneedy",S)}>Select</option> --%>
									<c:forEach var="caredOnes" items="${caredPersonERStatus}">
										<option ${f:select("careneedy", caredOnes.key.key.id)}>${f:h(caredOnes.key.firstName)}-${f:h(caredOnes.value)}</option>
									</c:forEach>
							</select>
							</span>
						</c:if>
					</div>

					<!--  This block is shown or hidden based on if the emergency details have already been set -->
					<c:if test="${fn:length(caredPersonERStatus) gt 0}">
						<div id="emerDetails" style="display: block;">
							<div style="position: relative; left: 5%; top: 10px;">
								<a class="list-group-item"
									href="javascript:submitForm('CREEMR')"><i
									class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add a
									Contact</a>&nbsp; <a class="list-group-item"
									href="javascript:submitForm('EMERGENCY')"><i
									class="fa fa-address-book fa-x" aria-hidden="true"></i>&nbsp;Back
									to List</a>
							</div>

							<c:if test="${requestScope.showErrBox == true}">
								<div id='errbox' style="left: 5%; top: 10px;">
									<div style='color: #003300; top: 10px;'>* Missing
										information</div>
									<c:forEach var="e" items="${f:errors()}">
										<li>${f:h(e)}</li>
									</c:forEach>
								</div>
							</c:if>
							<div style="position: relative; left: 5%; top: 15px;">
								<table>
									<tr>
										<td width="50%"><Label class="mustEnter">*</Label>Hospital/Clinic</td>
										<td><input type='text' ${f:text("providerName")}
											length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('providerName')"/></td>
									</tr>
									<tr>
										<td><Label class="mustEnter">*</Label>Contact Person</td>
										<td><input name='contactPerson' type='text'
											${f:text("contactPerson")} length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('contactPerson')"/></td>
									</tr>
									<tr>
										<td><Label class="mustEnter">*</Label>Street Address</td>
										<td colspan='2'><textarea rows='4' maxlength='100' onchange="javascript:trimSpaces('streetAddress')"
												${f:text("streetAddress")}>${requestScope.streetAddress}</textarea></td>
									</tr>
									<tr>
										<td><Label class="mustEnter">*</Label>City</td>
										<td><input type='text' ${f:text("city")} length="10"
											maxlength="length=" 20" onchange="javascript:trimSpaces('city')" /></td>
									</tr>
									<tr>
										<td><Label class="mustEnter">*</Label>Country</td>
										<td><input type='text' ${f:text("country")} length="10"
											maxlength="length=" 20" onchange="javascript:trimSpaces('country')"/></td>
									</tr>
									<tr>
										<td><Label class="mustEnter">*</Label>Emergency Contact
											Number</td>
										<td><input type='text' ${f:text("cellphone")} length="10"
											maxlength="length=" 20" onchange="javascript:trimSpaces('cellphone')"/></td>
									</tr>
									<tr>
										<td colspan='2' style="font-size: 0.8em; color: red;">Enter
											only numbers starting with area code.</td>
									</tr>
									<%-- 
						<tr>
							<td><Label class="mustEnter">*</Label>Fixed / Land Line</td>
							<td><input type='text' ${f:text("fixedLine")} length="10"
								maxlength="length=" 20"/></td>
						</tr>
						--%>
									<tr>
										<td><Label class="mustEnter">*</Label>Email</td>
										<td><input type='text' ${f:text("email")} length="10"
											maxlength="length=" 40" onchange="javascript:makeLowerCase('email')" /></td>
									</tr>
								</table>
							</div>
						</div>
				</div>
				</c:if>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>