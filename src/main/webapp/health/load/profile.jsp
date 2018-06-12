<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='profileupd' action='register' method='POST'>
		<input name='actionParam' value='UPDPRO' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>
						<i class="fa fa-user-o fa-2x" aria-hidden="true"></i>&nbsp;Update
						your profile
					</h3>
				</div>
				<div>
					<c:if test="${requestScope.showErrBox == true}">
						<div id='errbox'>
							<div style='color: #003300'>* Missing information</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>
					</c:if>
					<div>
						<!--  
					<input type='button' value='Update'
						onClick="javascript:submitForm('UPDPRO')" />
					-->
						<h4>
							<a class="list-group-item" href="javascript:submitForm('UPDPRO')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
						</h4>
					</div>
					<div>
						<table>
							<tr>
								<td>First name</td>
								<td><input type='text' ${f:text("firstname")} length="20"
									maxlength="30" readonly/></td>
							</tr>
							<tr>
								<td>Last name</td>
								<td><input type='text' ${f:text("lastname")} length="20"
									maxlength="length=" 30" readonly /></td>
							</tr>
							<tr>
								<td>Gender</td>
								<td><select name="gender" disabled>
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
								<td><input type="text" id="dobdatepicker" ${f:text("dob")} readonly></td>
							</tr>
							<tr>
								<td colspan='2' style="font-size: 0.8em; color: red;">Please
									add City & Country where you live.</td>
							</tr>
							<tr>
								<td>City</td>
								<td><input type='text' ${f:text("city")} length="10"
									, maxlength="20" readonly/></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><input type='text' ${f:text("country")} length="10"
									, maxlength="20" readonly/></td>
							</tr>
							<tr>
								<td>Purpose</td>
								<td><select name="purpose" disabled>
										<option ${f:select("purpose","0")}>Select</option>
										<option ${f:select("purpose","1")}>Self Care</option>
										<option ${f:select("purpose","2")}>Caring for someone</option>
										<option ${f:select("purpose","3")}>Both</option>
								</select></td>
							</tr>
							<tr>
								<td>Email</td>
								<td><input type='text' ${f:text("email")} value=''
									length="10" , maxlength="40" readonly /></td>
							</tr>
							<tr>
								<td>Choose a Password</td>
								<td><input type='password' ${f:text("password")} value=''
									length="10" , maxlength="10" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>