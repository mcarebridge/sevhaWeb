<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='updatePhysician' action='physician' action='POST'>
		<input name='actionParam' type='hidden' value='UPDPHY' /> <input
			type='hidden' ${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Update Physician</h3>
				</div>

				<div style="position: relative; width: 70%; left:5%" >
					<div>
						<!--  
					<input type='button' value='Update'
						onClick="javascript:submitForm('UPDPHY')" /> <a
						href="javascript:submitForm('LISPHY')">&lt;&lt;List
						Physicians&gt;&gt;</a>
					-->
						<h4>
							<a class="list-group-item" href="javascript:submitForm('UPDPHY')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
							<a class="list-group-item" href="javascript:submitForm('LISPHY')"><i
								class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
								List</a>
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
								<td>First name</td>
								<td><input type='text' ${f:text("firstname")} length="20"
									maxlength="length=" 30" onchange="javascript:trimSpaces('firstname')"/></td>
							</tr>
							<tr>
								<td>Last name</td>
								<td><input type='text' ${f:text("lastname")} length="20"
									maxlength="30" onchange="javascript:trimSpaces('lastname')"/></td>
							</tr>
							<tr>
								<td>Speciality / Department</td>
								<td><select name='speciality'>
										<option ${f:select("speciality",'S')}>Select</option>
										<c:forEach var="physpeciality"
											items="${physicianListSpeciality}">
											<option ${f:select("speciality",physpeciality.key.id)}>${f:h(physpeciality.speciality)}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td>Affiliated Hospital / Clinic</td>
								<td><input ${f:text("hospital")} length="20"
									maxlength="length=" 50" onchange="javascript:trimSpaces('hospital')"/></td>
							</tr>
							<tr>
								<td>City</td>
								<td><input ${f:text("city")} length="20"
									maxlength="length=" 30" onchange="javascript:trimSpaces('city')"/></td>
							</tr>
							<tr>
								<td>Pin/ZIP</td>
								<td><input type='text' ${f:text("zip")} length="10"
									maxlength="length=" 20" onchange="javascript:trimSpaces('zip')"/></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input ${f:text("country")} length="20"
									maxlength="length=" 20" onchange="javascript:trimSpaces('country')"/></td>
							</tr>
							<tr>
								<td>Phone (Fixed/ Land)</td>
								<td><input ${f:text("fixedphone")} length="10"
									maxlength="length=" 20" onchange="javascript:trimSpaces('fixedphone')" /></td>
							</tr>
							<tr>
								<td>Cellphone</td>
								<td><input ${f:text("cellphone")} length="10"
									maxlength="length=" 20" onchange="javascript:trimSpaces('cellphone')"/></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type='text' ${f:text("email")} length="10"
									maxlength="length=" 40" onchange="javascript:makeLowerCase('email')"/></td>
							</tr>

						</table>
					</div>
				</div>
				<div>
					<!--  <h4>Page Footer</h4> -->
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>