<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caregiverForm' action='caregiver' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Update a Care Giver</h3>
				</div>
				<div>
					<!--  
					<input type="button" value="Update"
						onClick="javascript:submitForm('UPDCAG')" /> <a
						href="javascript:submitForm('LISTCAG')">&LT;&LT;List Care
						Givers&GT;&GT;</a>
					-->
					<h4>
						<a class="list-group-item" href="javascript:submitForm('UPDCAG')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
						<a class="list-group-item" href="javascript:submitForm('LISTCAG')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>
					</h4>
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
					<table>
						<tr>
							<td>First name</td>
							<td><input type='text' ${f:text("firstname")} length="20"
								maxlength="length=" 30" onchange="javascript:trimSpaces('firstname')"/></td>
						</tr>
						<tr>
							<td>Last name</td>
							<td><input type='text' ${f:text("lastname")} length="20"
								maxlength="length=" 30" onchange="javascript:trimSpaces('lastname')"/></td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><input type="text" id="dobdatepicker" ${f:text("dob")}></td>

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
							<td colspan='2' style="font-size: 0.8em; color: red;">Please
								add City & Country where the care-giver lives.</td>
						</tr>
						<tr>
							<td>City</td>
							<td><input type='text' ${f:text("city")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('city')"/></td>
						</tr>
						<tr>
							<td>Country</td>
							<td><input type='text' ${f:text("country")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('country')"/></td>
						</tr>
						<tr>
							<td>Cellphone</td>
							<td><input type='text' ${f:text("cellphone")} length="10"
								maxlength="length=" 20" onchange="javascript:trimSpaces('cellphone')"/></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Accepted format +countrycode-number</td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input type='text' ${f:text("email")} length="10"
								maxlength="length=" 40" onchange="javascript:makeLowerCase('email')"/></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>