<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='prescriptionForm' action='prescription' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} /> <input type='hidden' ${f:text("rxlinekey")} />
		<input type='hidden' ${f:text("rxcreatetype")} />


		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Create Update Rx Lines</h3>
				</div>
				<div>
					<h3>Rx for
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}</h3>
				</div>
				<div style="position: relative; width: 70%; left: 5%;">
					<div style="text-align: right; height: 1.5em;">
						<!--  
					<input type="button" name="CRELINES" value="+Rx Line"
						onclick="javascript:submitForm('CRELINES')" /> <a
						href="javascript:submitForm('PRESLINES')">List Rx Lines</a> 
					-->
						<a class="list-group-item"
							href="javascript:submitForm('CRELINES')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Add</a>&nbsp; <a
							class="list-group-item" href="javascript:submitForm('PRESLINES')"><i
							class="fa fa-address-book fa-x" aria-hidden="true"></i>Back to
							List</a>
					</div>

					<c:if test="${requestScope.showErrBox == true}">
						<div id='errbox'>
							<div style='color: #003300'>* Missing information</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>
					</c:if>

					<div style="position: relative; top: 5%; background: #FFFFFF;">
						<table style="padding: 0 5px 0;">
							<tr>
								<td>Rx Start Date</td>
								<td><input type="text" readonly id="datepicker"
									${f:text("rxstartdate")}></td>
							</tr>
							<tr>
								<td colspan='2' style="font-size: 0.8em; color: red;">Note
									: Rx Start date is current date or max a day later</td>
							</tr>
							<tr style="color: #0066CC; font-weight: bold;">
								<td>Drug/Medication Name</td>
								<td><input type='text' ${f:text("drugName")} maxlength="30" onchange="javascript:trimSpaces('drugName')"/></td>
							</tr>

							<tr
								style="color: #0066CC; font-weight: bold; background-color: #F9E79F;">
								<td>Strength / Count</td>
								<td style="left: 20%">Unit</td>
							</tr>
							<tr
								style="color: #0066CC; font-weight: bold; background-color: #F9E79F;">
								<td><input type='text' style="" ${f:text("strength")}
									maxlength="5" onchange="javascript:trimSpaces('strength')"/></td>
								<td><select name='unit'>
										<option ${f:select("unit",'-1')}>Select</option>
										<option ${f:select("unit",'MG')}>mg</option>
										<option ${f:select("unit",'GM')}>gram</option>
										<option ${f:select("unit",'MCG')}>Micro Gram</option>
										<option ${f:select("unit",'ML')}>ml</option>
										<option ${f:select("unit",'TS')}>TableSpoon</option>
										<option ${f:select("unit",'UT')}>Unit</option>
										<option ${f:select("unit",'TB')}>Tablet</option>
										<option ${f:select("unit",'DP')}>Drops</option>
								</select></td>
							</tr>
							<tr>
								<td>Route</td>
								<td><select name='route'>
										<option ${f:select("route",'-1')}>Select</option>
										<option ${f:select("route",'PO')}>Oral</option>
										<option ${f:select("route",'PR')}>Local</option>
										<option ${f:select("route",'IM')}>Nasal</option>
										<option ${f:select("route",'IV')}>Rectal</option>
										<option ${f:select("route",'ID')}>Intravenous</option>
										<option ${f:select("route",'IN')}>Subcutaneous</option>
										<option ${f:select("route",'TP')}>Intramuscular</option>
								</select></td>
							</tr>
							<tr>
								<td>Frequency</td>
								<td><select onchange="rxScheduleBuilder();"
									name='frequency'>
										<option ${f:select("frequency",'-1')}>Select</option>
										<option ${f:select("frequency",'MOD')}>1-0-0</option>
										<option ${f:select("frequency",'AOD')}>0-1-0</option>
										<option ${f:select("frequency",'EOD')}>0-0-1</option>
										<option ${f:select("frequency",'MATD')}>1-1-0</option>
										<option ${f:select("frequency",'AETD')}>0-1-1</option>
										<option ${f:select("frequency",'METD')}>1-0-1</option>
										<option ${f:select("frequency",'TID')}>1-1-1</option>
										<option ${f:select("frequency",'QID')}>1-1-1-1</option>
										<option ${f:select("frequency",'QWK')}>Once a Week</option>
										<option ${f:select("frequency",'BIS')}>Twice a Week</option>
										<option ${f:select("frequency",'TIW')}>Thrice a Week</option>
										<!--  <option ${f:select("frequency",'STAT')}>Immediately</option> -->
										<option ${f:select("frequency",'SOS')}>If necessary</option>
								</select></td>
							</tr>
							<!--  
							<tr
								style="color: #0066CC; font-weight: bold;">
								<td>Rx Start Time</td>
							-->
							<!--  <td style="text-align:center">8am - 4pm - 9pm</td>-->
							<!--  
								<td><input type='text' ${f:text("1stRxLine")} onchange="rxScheduleBuilder();"/>
							</tr>
							<tr>
								<td>Rx Schedule</td>
								<td><div id='rxlinesection'
										style="color: #0066CC; font-weight: bold; background-color: #CCCCCC;">
										<input type='text' readonly ${f:text("rxSelectSchdl")} />
									</div></td>
							</tr>
							-->
							<tr>
								<td>Meal option</td>
								<td><select name='mealoption'>
										<option ${f:select("mealoption",'-1')}>Select</option>
										<option ${f:select("mealoption",'AC')}>Before Meal</option>
										<option ${f:select("mealoption",'PC')}>After Meal</option>
								</select></td>
							</tr>
							<!--  
						<tr>
							<td>How much</td>
							<td><input type='text' ${f:text("howmuch")} /></td>
						</tr>
						-->
							<tr>
								<td>Duration in (Days or Weeks)</td>
								<td><input type='text' ${f:text("duration")} onchange="javascript:trimSpaces('duration')"/></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>