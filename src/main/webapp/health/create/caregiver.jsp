<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caregiverform' action='caregiver' method='PSOT'>
		<input name='actionParam' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Add a Care giver</h3>
				</div>
				<div>
					<!--  
					<input type="button" name="CRECAG" value="Add Care Giver"
						onclick="javascript:submitForm('CRECAG')" /> <a
						href="javascript:submitForm('LISTCAG')">&LT;&LTList Care
						Givers&GT;&GT;</a>
					-->

					<h4>
						<a class="list-group-item" href="javascript:submitForm('CRECAG')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
							class="list-group-item" href="javascript:submitForm('LISTCAG')"><i
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
							<td width="50%">First name</td>
							<td><input ${f:text("firstname")} type='text' length="20"
								maxlength="30" onchange="javascript:trimSpaces('firstname')"/></td>
						</tr>
						<tr>
							<td>Last name</td>
							<td><input ${f:text("lastname")} type='text' length="20"
								maxlength="30" onchange="javascript:trimSpaces('lastname')"/></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">You
								need to at least 21 years old.</td>
						</tr>
						<tr>
							<td>Date of Birth</td>
							<td><input type="text" id="dobdatepicker" ${f:text("dob")}></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Please
								add City & Country where the care-giver lives.</td>
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
							<td>City</td>
							<td><input type='text' ${f:text("city")} length="10"
								maxlength="30" onchange="javascript:trimSpaces('city')"/></td>
						</tr>
						<tr>
							<td>Country</td>
							<td><input type='text' ${f:text("country")} length="10"
								maxlength="30" onchange="javascript:trimSpaces('country')"/></td>
						</tr>
						<tr>
							<td>Cellphone</td>
							<td><input ${f:text("cellphone")} type='text' length="10"
								maxlength="15" onchange="javascript:trimSpaces('cellphone')"/></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Accepted format +countrycode-number</td>
						</tr>
						<tr>
							<td>Email</td>
							<td><input ${f:text("email")} type='text' length="10"
								maxlength="40" onchange="javascript:makeLowerCase('email')"/></td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>