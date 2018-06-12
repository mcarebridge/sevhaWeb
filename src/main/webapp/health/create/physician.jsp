<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='physician' action='physician' method='POST'>
		<input name='actionParam' type='hidden' value='CREPHY'
			onsubmit="addphy.disabled = true; addphy.value = 'Please wait...';return true;" />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Add a Physician</h3>
				</div>
				<!--  
				<div>
					<input type='submit' name="CREPHY" value='Create'
						onclick="javascript:submitForm('CREPHY')" /> <a
						href="javascript:submitForm('LISPHY')">&lt;&lt;List
						Physician&gt;&gt;</a>
				</div>
				-->
				<div style="position: relative; left: 5%; width: 70%">
					<div style="position: relative; text-align: right; height: 1.5em;">
						<h4>
							<a class="list-group-item" href="javascript:submitForm('CREPHY')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
								class="list-group-item" href="javascript:submitForm('LISPHY')"><i
								class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
								List</a>&nbsp;
						</h4>
					</div>
					<c:if test="${requestScope.showErrBox == true}">
						<div id='errbox'>
							<div>* Missing information</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>

					</c:if>
					<div>
						<table>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>First
									name</td>
								<td><input ${f:text("firstname")} type='text' length="20"
									maxlength="30" onchange="javascript:trimSpaces('firstname')"/></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Last name</td>
								<td><input ${f:text("lastname")} type='text' length="20"
									maxlength="30" onchange="javascript:trimSpaces('lastname')"/></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Speciality /
									Department</td>
								<td><select name='speciality'>
										<option ${f:select("speciality",'S')}>Select</option>
										<c:forEach var="physpeciality" items="${physicianList}">
											<option ${f:select("speciality",physpeciality.key.id)}>${f:h(physpeciality.speciality)}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Affiliated Hospital /
									Clinic</td>
								<td><input ${f:text("hospital")} type='text' length="20"
									maxlength=50" onchange="javascript:trimSpaces('hospital')"/></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>City</td>
								<td><input ${f:text("city")} type='text' length="20"
									maxlength="30" onchange="javascript:trimSpaces('city')"/></td>
							</tr>
							<tr>
								<td>Pin/ZIP</td>
								<td><input ${f:text("zip")} type='text' length="10"
									maxlength="10" onchange="javascript:trimSpaces('zip')"/></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Country</td>
								<td><input ${f:text("country")} onchange="javascript:trimSpaces('country')" type='text' length="20"
									maxlength="30"/></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Phone (Fixed/ Land)</td>
								<td><input ${f:text("fixedphone")} type='text'
									value='Fixed Phone#' length="15" maxlength="15" onchange="javascript:trimSpaces('fixedphone')"/></td>
							</tr>
							<tr>
								<td>Cellphone</td>
								<td><input ${f:text("cellphone")} type='text'
									value='Cell Phone#' length="15" maxlength="15" onchange="javascript:trimSpaces('cellphone')"/></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type='text' ${f:text("email")} length="10"
									maxlength="40" onchange="javascript:makeLowerCase('email')"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div>
					<Label class="mustEnter">*required Data</Label>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>