<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='substitueForm' action='substitute' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">

				<div>
					<h3>Substitute Drug for
						${requestScope.prescriptionLines.drugName}</h3>
					<h3>Rx for
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}</h3>
				</div>
				<div style="position: relative; left:5%;">
					<div>
						<%-- 
					<input type="button" name="CRESUBS" value="+Substitute"
						onclick="javascript:submitForm('CRESUBS')" /> <a
						href="javascript:submitForm('PRESLINES')">&lt;&ltRx
						Lines&gt;&gt;</a>
					--%>

						<h4>
							<a class="list-group-item"
								href="javascript:submitForm('CRESUBS')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
								class="list-group-item"
								href="javascript:submitForm('PRESLINES')"><i
								class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
								List</a>&nbsp;
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
								<td>Substitute Drug</td>
								<td><input type='text' ${f:text("substitutedrug")}
									length="20" maxlength="length=" 30" onchange="javascript:trimSpaces('substitutedrug')"/></td>
							</tr>
							<tr>
								<td>Strength</td>
								<td><input type='text' ${f:text("strength")} length="20"
									maxlength="length=" 30" onchange="javascript:trimSpaces('strength')"/></td>
							</tr>
							<tr>
								<td>Reason for using substitute</td>
								<td><select name="subsreason">
										<option ${f:select("subsreason","0")}>Select</option>
										<option ${f:select("subsreason","1")}>Not available</option>
										<option ${f:select("subsreason","2")}>Cost of
											Prescribed drug</option>
										<option ${f:select("subsreason","3")}>Suggested by
											Drugist</option>
								</select></td>
							</tr>
							<tr>
								<td>Substitution Suggested by</td>
								<td><select name="suggestedBy">
										<option ${f:select("suggestedBy","0")}>Select</option>
										<option ${f:select("suggestedBy","1")}>Drugist</option>
										<option ${f:select("suggestedBy","2")}>Original
											Physician</option>
										<option ${f:select("suggestedBy","3")}>Other
											Physician</option>
										<option ${f:select("suggestedBy","4")}>Self</option>
										<option ${f:select("suggestedBy","5")}>Others</option>
								</select></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>