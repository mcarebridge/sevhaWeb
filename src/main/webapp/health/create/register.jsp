<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='register' action='register' method='POST'
		onload="javascript:resetForm($('#myform'));">
		<input name='actionParam' value='CREREG' type='hidden' />
		<input name='selectedPreCondition' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div class='logout'>
				<span> <a href="javascript:submitForm('APPLN')">Cancel</a>
			</div>
			<div id="contents">
				<div>
					<h3>Register your Care Management Account</h3>
				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<div id='errbox'>
						<div>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
				</c:if>
				<div style="top: 5%;">
					<table>
						<tr>
							<td width="35%">First name</td>
							<td width="65%"><input type='text' id="fname" ${f:text("firstname")}
								length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('firstname')"/></td>
						</tr>
						<tr>
							<td>Last name</td>
							<td><input ${f:text("lastname")} type='text' value=''
								length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('lastname')"/></td>
						</tr>
						<tr>
							<td colspan="2" style="font-size: 0.8em; color: red;">Email
								Id will be your User-Id</td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input type='text' ${f:text("email")} value=''
								length="10" , maxlength="40" onchange="javascript:makeLowerCase('email')"/></td>
						</tr>
						<tr>
							<td>Choose a Password</td>
							<td><input type='password' ${f:text("password")} value=''
								length="8" maxlength="12" /></td>
						</tr>

						<tr>
							<td>Gender</td>
							<td><select name="gender">
									<option ${f:select("gender","0")}>Select</option>
									<option ${f:select("gender","1")}>Male</option>
									<option ${f:select("gender","2")}>Female</option>
									<option ${f:select("gender","3")}>Others</option>
							</select></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">You
								need to at least 21 years old.</td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><input type="text" readonly id="dobdatepicker"
								${f:text("dob")}></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Please
								add City & Country where you live.</td>
						</tr>
						<tr>
							<td>City</td>
							<td><input type='text' ${f:text("city")} value=''
								length="10" , maxlength="20" onchange="javascript:trimSpaces('city')" /></td>
						</tr>
						<tr>
							<td>Country</td>
							<td><input type='text' ${f:text("country")} value=''
								length="10" , maxlength="20" onchange="javascript:trimSpaces('country')"/></td>
						</tr>
						<tr>
							<td>Purpose</td>
							<td><select name="purpose" onchange="showPreCondn();">
									<option ${f:select("purpose","0")}>Select</option>
									<option ${f:select("purpose","1")}>Self Care</option>
									<option ${f:select("purpose","2")}>Caring for someone</option>
									<!--  <option ${f:select("purpose","3")}>Both</option> -->
							</select></td>
						</tr>
						<tr>
							<td>
								<!--  
							<input type='button' value='Register' name="CREREG"
								onclick="javascript:submitForm('CREREG')" />
							-->
								<button type="submit" value='Register' name="CREREG"
									onclick="javascript:submitRegisterForm('CREREG')"
									style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
									class="fa fa-address-card-o">
									<font style="font-family: Arial;">&nbsp;Register&nbsp;</font>
								</button>
							</td>
							<td>
								<!--  <input type='reset' value='Reset' /> -->
								<button type="reset" id="reset"
									style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
									class="fa fa-key">
									<font style="font-family: Arial;">Reset</font>
								</button>
							</td>
						</tr>
					</table>
					<div id="preCondn" style="display: none;">
						<div>Pre-existing Conditions</div>
						<div>
							<div>
								<c:forEach var="existingDisease"
									items="${allPreExistingDiseases}">
									<c:choose>
										<c:when test="${existingDisease.key.id == 1000}">
											<span style="display: none">
												<input class="chk" type="checkbox" name="existingIllness"
													value="${f:h(existingDisease.key.id)}" checked="checked" />${f:h(existingDisease.diseaseName)}
											</span>
										</c:when>
										<c:when test="${existingDisease.key.id != 1000}">
											<span style="display: show">
												<input class="chk" type="checkbox" name="existingIllness"
													value="${f:h(existingDisease.key.id)}" />${f:h(existingDisease.diseaseName)}
											</span>
										</c:when>
									</c:choose>
								</c:forEach>
							</div>
						</div>
					</div>

					<script type="text/javascript">
						function showPreCondn() {

							var _careVal = document.forms[0].purpose.selectedIndex;
							var x = document.getElementById("preCondn");

							if (_careVal == 1) {
								if (x.style.display == "none") {
									x.style.display = "block";
								} else {
									x.style.display = "none";
								}
							} else {
								x.style.display = "none";
							}
						}
					</script>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>