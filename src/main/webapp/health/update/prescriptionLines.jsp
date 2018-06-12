<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>

<script type="text/javascript">
	function disableRx() {
		var _rxbtn = document.getElementById("stopRxBtnId");
		var _rxdiv = document.getElementById('rxUpdateDiv');
		if (_rxbtn.value == "Yes") {
			_rxbtn.value = "No";
			document.prescriptionForm.rxLineDisabled.value = false;
			_rxbtn.text = "Do not stop";
			//_rxdiv.style.display = 'none';
			_rxdiv.style.color = "#000000";
			_rxdiv.style.backgroundColor  = "transparent";
		} else {
			_rxbtn.value = "Yes";
			document.prescriptionForm.rxLineDisabled.value = true;
			_rxdiv.style.display = 'block';
			_rxdiv.style.color = "#FFFFFF";
			_rxdiv.style.backgroundColor  = "#CCCCCC";

		}

		//sah1(document.getElementById("rxUpdateDiv"));
	}
	function sah1(el) {
		try {
			el.disabled = el.disabled ? false : true;
		} catch (E) {
		}
		if (el.childNodes && el.childNodes.length > 0) {
			for (var x = 0; x < el.childNodes.length; x++) {
				sah1(el.childNodes[x]);
			}
		}
	}
</script>

<body>
	<form name='prescriptionForm' action='prescription' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} /> <input name='rxLineDisabled' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>Update Rx Lines</h3>
				</div>
				<div>
					<h3>Rx for
						${sessionScope.selectedCaredPerson.firstName}&nbsp;${sessionScope.selectedCaredPerson.lastName}</h3>
				</div>
				<div>
					<!--  
					<input type="button" value="Update"
						onclick="javascript:submitForm('UPDLINES')" /> <a
						href="javascript:submitFormWithKey('PRESLINES',${f:h(prescriptionKey)})">&lt;&lt;Rx
						Lines&gt;&gt;</a>
					-->

					<h4>
						<a class="list-group-item"
							href="javascript:submitForm('UPDLINES')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>Update</a>&nbsp;
						<a class="list-group-item"
							href="javascript:submitFormWithKey('PRESLINES', ${f:h(prescriptionKey)})"><i
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
				<div id="rxUpdateDiv" style="display: block;">
					<table>
						<tr>
							<td>Rx Start Date</td>
							<td><input type="text" readonly id="datepicker"
								${f:text("rxstartdate")}></td>
						</tr>
						<tr>
							<td colspan='2' style="font-size: 0.8em; color: red;">Note :
								Rx Start date is current date or max a day later</td>
						</tr>
						<tr>
							<td>Drug</td>
							<td><input type='text' ${f:text("drugName")} onchange="javascript:trimSpaces('drugName')"/></td>
						</tr>
						<tr>
							<td colspan='2'>Strength(mg|gm|mcg|ml)</td>
						</tr>
						<tr>
							<td><input type='text' ${f:text("strength")} onchange="javascript:trimSpaces('strength')"/></td>
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
							<td><select name='frequency'>
									<option ${f:select("frequency",'-1')}>Select</option>
									<option ${f:select("frequency",'MOD')}>1-0-0</option>
									<option ${f:select("frequency",'AOD')}>0-1-0</option>
									<option ${f:select("frequency",'EOD')}>0-0-1</option>
									<option ${f:select("frequency",'MATD')}>1-1-0</option>
									<option ${f:select("frequency",'AETD')}>0-1-1</option>
									<option ${f:select("frequency",'METD')}>1-0-1</option>
									<option ${f:select("frequency",'TID')}>1-1-1</option>
									<option ${f:select("frequency",'QID')}>1-1-1-1</option>
									<option ${f:select("frequency",'STAT')}>Immediately</option>
									<option ${f:select("frequency",'SOS')}>If necessary</option>
							</select></td>
						</tr>
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
							<td>Duration (Days)</td>
							<td><input type='text' ${f:text("duration")} onchange="javascript:trimSpaces('duration')"/></td>
						</tr>
					</table>
				</div>
				<div id="rxstop">
					<table>
						<tr>
							<td>
								<button type="button" name="stopRxBtn" id="stopRxBtnId"
									onClick="javascript:disableRx()"
									style="width: 120px; background-color: #FF5733; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
									class="fa fa-lock">
									<font style="font-family: Arial;">&nbsp;Stop Rx
										Line&nbsp;</font>
								</button>
							</td>
							<!--  
							<td><input id="stopRxBtnId" type='reset' name="stopRxBtn"
								bgcolor='red' value='Stop Rx Line'
								onClick="javascript:disableRx()" /></td>
								
							-->
						</tr>
						<tr>
							<td colspan='2' style="color: red;">After stopping the Rx
								Line, the cared person will no longer receive any alerts.
						</tr>
					</table>
				</div>

			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>