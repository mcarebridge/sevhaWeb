<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredForm' action='cared' method='POST'>
		<input name='actionParam' type='hidden' /> <input
			name='selectedPreCondition' type='hidden' /> <input type='hidden'
			${f:text("key")} /> <input type='hidden' ${f:text("addresskey")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Update Cared</h3>
				</div>
				<div>
					<h4>
						<a class="list-group-item"
							href="javascript:submitCaredPersonForm('UPDCAD')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
						<a class="list-group-item"
							href="javascript:submitCaredPersonForm('LISTCAD')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>&nbsp; <a class="list-group-item"
							href="javascript:submitForm('UPDVITALPARA')"><i
							class="fa fa-medkit fa-x aria-hidden="true"></i>Vital Readings</a>&nbsp;
					</h4>
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
					<table>
						<tr>
							<td width="50%"><Label class="mustEnter">*</Label>First name</td>
							<td><input type='text' ${f:text("firstname")} length="20"
								maxlength="length=" 30" onchange="javascript:trimSpaces('firstname')"/></td>
						</tr>
						<tr>
							<td><Label class="mustEnter">*</Label>Last name</td>
							<td><input name='lastname' type='text' ${f:text("lastname")}
								length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('lastname')"/></td>
						</tr>
						<tr>
							<td><Label class="mustEnter">*</Label>Date of Birth</td>
							<td><input type="text" id="dobdatepickercared" ${f:text("dob")}></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Please
								add City & Country where the cared one live.</td>
						</tr>
						<tr>
							<td><Label class="mustEnter">*</Label>Gender</td>
							<td><select name="gender">
									<option ${f:select("gender","0")}>Select</option>
									<option ${f:select("gender","1")}>Male</option>
									<option ${f:select("gender","2")}>Female</option>
									<option ${f:select("gender","3")}>Others</option>
							</select></td>
						</tr>
						<tr>
							<td><Label class="mustEnter">*</Label>City</td>
							<td><input type='text' ${f:text("city")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('city')"/></td>
						</tr>
						<tr>
							<td><Label class="mustEnter">*</Label>Country</td>
							<td><input type='text' ${f:text("country")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('country')" /></td>
						</tr>
						<tr>
							<td>Cellphone</td>
							<td><input type='text' ${f:text("cellphone")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('cellphone')"/></td>
						</tr>
						<tr>
							<td>IMEI#</td>
							<td><input type='text' ${f:text("imei")} length="15"
								maxlength="length=" 15" onchange="javascript:trimSpaces('imei')"/></td>
						</tr>
						<tr>
							<td colspan="2">(Press *#06# on your mobile phone)</td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input type='text' ${f:text("email")} length="10"
								maxlength="length=" 40" onchange="javascript:makeLowerCase('email')"/></td>
						</tr>
						<tr>
							<td>Pre-exisiting Conditions</td>
							<td colspan='1'>
							<c:forEach var="existingDisease"
									items="${allPreExistingDiseases}">
									<c:set var="matchFound"  value='false'/>
									<c:forEach var="preexistingDisease"
										items="${selectedPreExisitingDiseases}">
										<input type="hidden"
											value="${f:h(existingDisease.key.id)} + ${f:h(preexistingDisease.preExisitingDiseases.key.id)}" />
											
											<c:choose>
												<c:when
													test="${existingDisease.key.id ==  preexistingDisease.preExisitingDiseases.key.id && existingDisease.key.id != 100 }">
													<div style="text-indent: 10px"><input class="chk" type="checkbox"
														disabled="disabled" checked="checked"
														name="existingIllness"
														value="${f:h(existingDisease.key.id)}" />${f:h(existingDisease.diseaseName)}</div>
													
													<c:set var="matchFound"  value='true'/>
												</c:when>
											</c:choose>
									</c:forEach>
									
									<c:if test="${!matchFound && existingDisease.key.id != 100}">
									<div style="text-indent: 10px"><input class="chk" type="checkbox"
										name="existingIllness" value="${f:h(existingDisease.key.id)}" />${f:h(existingDisease.diseaseName)}</div>

									</c:if>
								</c:forEach></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>