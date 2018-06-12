<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='vitalParametersForm' action='vitalParameters' method='POST'>
		<input name='actionParam' type='hidden' /> <input
			name='caredPersonKey' type='hidden'
			value="${requestScope.caredPersonKey}" />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>
						<i class="icon-i-cardiology  fa-2x" aria-hidden="true"></i>&nbsp;Add
						Vital Parameters for
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}
					</h3>
				</div>
				<div>
					<div>
						<!--  
					<input type="button" name="CREVITPARA" value="Add Parameters"
						onClick="javascript:submitForm('CREVITPARA')" /> <a
						href="javascript:submitForm('LISTVITALPARA')">&lt;&lt;List
						Vital Readings&gt;&gt;</a>
					-->
						<h4>
							<a class="list-group-item"
								href="javascript:submitForm('CREVITPARA')"><i
								class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
								class="list-group-item"
								href="javascript:submitForm('LISTVITALPARA')"><i
								class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
								List</a>&nbsp;
						</h4>
					</div>
					<div>
						<h5>Note : The listed values are last recored Vital Signs.
							Please review and fill latest values.</h5>
						<h5>
							Last Updated Date :
							<fmt:formatDate pattern="MMM,dd YYYY hh:mm a"
								value="${requestScope.createdDate}" />
						</h5>
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
					</div>
					<div>
						<table>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>Height
									(cms)</td>
								<td><input type='text' ${f:text("height")} length="5"
									maxlength="5" onchange="javascript:trimSpaces('height')"/></td>

							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Weight (Kgs)</td>
								<td><input type='text' ${f:text("weight")} length="5"
									maxlength="5" onchange="javascript:trimSpaces('weight')"/></td>
								<!--  
								<td><input name='weight' type='text' ${f:text("weight")} placeholder="multiple of 2" step="0.5"
									length="5" maxlength="5"/></td>
								-->
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Temperature (F)</td>
								<td><input type="text" ${f:text("temperature")}
									length="5" maxlength="5" onchange="javascript:trimSpaces('temperature')"></td>
							</tr>
							<tr>
								<td colspan="2" style="font-size: 0.8em; color: red;"><span
									style="width: 10%; background-color: yellow"> Normal
										body temprature taken orally are in the range of 97F to 99F. </span></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Pulse (BPM)</td>
								<td><input type="text" ${f:text("pulse")} length="5" onchange="javascript:trimSpaces('pulse')"></td>

							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>BP (High) mm Hg</td>
								<td><input type="text" ${f:text("systolicPressure")}
									length="5" maxlength="5" onchange="javascript:trimSpaces('systolicPressue')"></td>

							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>BP (Low) mm Hg</td>
								<td><input type="text" ${f:text("diastolicPressure")}
									length="5" maxlength="5" onchange="javascript:trimSpaces('diastolicPressure')"></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Blood Sugar (Normal)
									(mg/dl)</td>
								<td><input type="text" ${f:text("bloodSugar")} length="5"
									maxlength="5" onchange="javascript:trimSpaces('bloodSugar')"></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Blood Sugar (Fasting)
									(mg/dl)</td>
								<td><input type="text" ${f:text("bloodSugarFasting")}
									length="5" maxlength="5" onchange="javascript:trimSpaces('bloodSugarFasting')"></td>
							</tr>
						</table>
					</div>

				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>