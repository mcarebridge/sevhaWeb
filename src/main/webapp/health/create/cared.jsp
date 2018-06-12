<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredForm' action='cared' method='POST'">
		<input name='actionParam' type='hidden' /> <input
			name='selectedPreCondition' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Add a Cared</h3>
				</div>
				<div>
					<!--  
					<input name="CRECAD" type="button" value="Create Cared"
						onClick="javascript:submitCaredPersonForm('CRECAD')" /> <a
						href="javascript:submitForm('LISTCAD')">&lt;&lt;List
						Cared&gt;&gt;</a>
					-->
					<h4>
						<a class="list-group-item"
							href="javascript:submitCaredPersonForm('CRECAD')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
							class="list-group-item" href="javascript:submitForm('LISTCAD')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>&nbsp;
					</h4>

				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<!--  
					<div class='errbox'>
						<div style='color: #003300'>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
					-->
					
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
							<td style:"font-size:8px;"><input type="text" id="dobdatepickercared" ${f:text("dob")}></td>

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
								maxlength="length=" 20" onchange="javascript:trimSpaces('country')"/></td>
						</tr>
						<tr>
							<td>Cellphone</td>
							<td><input type='text' ${f:text("cellphone")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('cellphone')"/></td>
						</tr>
						<tr>
							<td>IMEI# of the Cared Person's mobile</td>
							<td><input type='password' ${f:text("imei")} length="15"
								maxlength="15" onchange="javascript:trimSpaces('imei')"/></td>
						</tr>
						<tr>
							<td colspan="2" style="font-size: 0.8em; color: red;">
							<span style="width:10%; background-color: yellow">
							(Press
								*#06# on your Cared Person's phone). This is needed to authenticate the cared person with mCareBridge.
							</span>
							</td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input type='text' ${f:text("email")} length="10"
								maxlength="length=" 40" onchange="javascript:makeLowerCase('email')"/></td>
						</tr>
						<tr>
							<td>Pre-existing Conditions</td>
							<td colspan='2'><c:forEach var="existingDisease"
									items="${allPreExistingDiseases}">
									<c:choose>
										<c:when test="${existingDisease.key.id == 1000}">
											<div style="display:none"><input class="chk"
												type="checkbox" name="existingIllness"
												value="${f:h(existingDisease.key.id)}" checked="checked" />${f:h(existingDisease.diseaseName)}
											</div>
										</c:when>
										<c:when test="${existingDisease.key.id != 1000}">
											<div style="display: show"><input class="chk"
												type="checkbox" name="existingIllness"
												value="${f:h(existingDisease.key.id)}" />${f:h(existingDisease.diseaseName)}
											</div>
										</c:when>
									</c:choose>
								</c:forEach></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>