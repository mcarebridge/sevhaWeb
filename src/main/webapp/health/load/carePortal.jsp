<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredPortalForm' action='caredPortal' method='POST'">
		<input name='actionParam' type='hidden' /> <input
			name='selectedCaredId' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div class='logo'>
			<span><a href="\"
				style="color: #FFFFFF; visited: #FFFFFF; text-decoration: none;">sevhā</a></span>
		</div>
		<div id="main" style="margin: 0% 0% 0% 0%;">
			<div>
				<div id="contents" style="margin: 3% 0% 0% 0%;">
					<div>
						<h3>Validate Vital Signs</h3>
					</div>
					<div>
						<span><i class="fa fa-bullhorn" aria-hidden="true"></i>&nbsp;<Label
							class="mustEnter">*</Label> Must fill values </span>&nbsp;
					</div>
					<br>
					<c:if test="${requestScope.showErrBox == true}">
						<div id='errbox'>
							<div>
								<i class="fa fa-bell" aria-hidden="true"></i>&nbsp;Missing
								information
							</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>
					</c:if>
					<div id="basic" class="carePortalCal">
						<table>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>Purpose</td>
								<td><select name="purpose">
										<option ${f:select("purpose","0")}>Select</option>
										<option ${f:select("purpose","1")}>Self Care</option>
										<option ${f:select("purpose","2")}>Caring for someone</option>
								</select></td>
							</tr>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>Date of
									Birth</td>
								<td><input readonly id="dobdatepickercared"
									${f:text("dob")}></td>
							</tr>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>Gender</td>
								<td><select name="gender">
										<option ${f:select("gender","0")}>Select</option>
										<option ${f:select("gender","1")}>Male</option>
										<option ${f:select("gender","2")}>Female</option>
										<option ${f:select("gender","3")}>Others</option>
								</select></td>
							</tr>
							<tr>
								<td style="margin-left: 2%;">Email (get results in mail)</td>
								<td><input type="text" ${f:text("email")} length="40"
									maxlength="50" onchange="javascript:makeLowerCase('email')"></td>
							</tr>
							<tr>
								<td width="50%"><Label class="mustEnter">*</Label>Height
									(cms)</td>
								<td><input type='text' ${f:text("height")} length="4"
									maxlength="4" onfocusout="toFeet();"
									onchange="javascript:trimSpaces('height')" />
									<div id="caredHeight" style="position: relative; padding: 3px;">
									</div></td>
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Weight (Kgs)</td>
								<td><input type='text' ${f:text("weight")} length="4"
									maxlength="4" onfocusout="toLBS();"
									onchange="javascript:trimSpaces('weight')" />
									<div id="caredWeight" style="position: relative; padding: 3px;"></div></td>
								<!--  
								<td><input name='weight' type='number' ${f:text("weight")} placeholder="multiple of 2" step="0.5"
									length="5" maxlength="5"/></td>
								-->
							</tr>
							<tr>
								<td><Label class="mustEnter">*</Label>Oral Temperature (F)</td>
								<!--  
								<td><input type="text" ${f:text("temperature")} length="4"
									maxlength="4"></td>
									
								-->
								<td><select name='temperature'>
										<option ${f:select("temperature","-1")}>Select</option>
										<option ${f:select("temperature","98.3")}>Normal(97-99)F</option>
										<option ${f:select("temperature","100")}>Fever
											&gt;100F</option>
										<option ${f:select("temperature","103")}>High Fever
											&gt;103F</option>
								</select></td>
							</tr>
						</table>
					</div>
					<br>
					<div style="top: 5%; margin-left: 2%">
						<c:choose>
							<c:when test="${requestScope.advmeterFlag == 'none'}">
								<span> <i id="meterSwitch" class="fa fa-plus-circle"
									style="display: block;" aria-hidden="true">&nbsp;<a
										href="javascript:showAdvMeter()">Validate all vitals</a></i>
								</span>
							</c:when>
							<c:when test="${requestScope.advmeterFlag == 'block'}">
								<span> <i id="meterSwitch" class="fa fa-minus-circle"
									style="display: block; color: red" aria-hidden="true">&nbsp;<a
										href="javascript:showAdvMeter()">Validate all vitals</a></i>
								</span>
							</c:when>
						</c:choose>
					</div>
					<br> <input type="hidden" name="advmeterFlag"
						value=${requestScope.advmeterFlag } />

					<script type="text/javascript">
						function showAdvMeter() {
							var x = document.getElementById("advanced");
							var y = document.getElementById("meterSwitch");

							if (x.style.display === "none") {
								x.style.display = "block";
								document.forms[0].advmeterFlag.value = "block";
								y.className = "fa fa-minus-circle";
								y.style.color = "red";
							} else {
								x.style.display = "none";
								document.forms[0].advmeterFlag.value = "none";
								y.className = "fa fa-plus-circle";
								y.style.color = "black";
							}
						}
					</script>
					<div id="advanced" class="carePortalCal"
						style="display: ${requestScope.advmeterFlag};">
						<div style="font-size: 0.7em; color: red; margin-left: 10px">
							<span>If the Vital values are not available, leave it
								blank, and we will bring the expected values for you.</span>
						</div>

						<table style="padding: 3px;">

							<!--  
							<tr>
								<td colspan="2" style="font-size: 0.8em; color: red;"><span
									style="width: 10%; background-color: tran"> Normal
										body temperature taken orally are in the range of 97F to 99F.
								</span></td>
							</tr>
							-->
							<tr>
								<td>Pulse (BPM) at Rest</td>
								<td><input type="text" ${f:text("pulse")} length="3"
									maxlength="3" onchange="javascript:trimSpaces('pulse')"></td>

							</tr>
							<tr>
								<td>BP (High / Systolic) mm Hg</td>
								<td><input type="text" ${f:text("systolicPressure")}
									length="3" maxlength="3"
									onchange="javascript:trimSpaces('systolicPressure')"></td>

							</tr>
							<tr>
								<td>BP (Low / Diastolic) mm Hg</td>
								<td><input type="text" ${f:text("diastolicPressure")}
									length="3" maxlength="3"
									onchange="javascript:trimSpaces('diastolicPressure')"></td>
							</tr>
							<tr>
								<td>Blood Sugar (Normal) mg/dl</td>
								<td><input type="text" ${f:text("bloodSugarNormal")}
									length="3" maxlength="3"
									onchange="javascript:trimSpaces('bloodSugarNormal')"></td>
							</tr>
							<tr>
								<td>Blood Sugar (Fasting) mg/dl</td>
								<td><input type="text" ${f:text("bloodSugarFasting")}
									length="3" maxlength="3"
									onchange="javascript:trimSpaces('bloodSugarFasting')"></td>
							</tr>
							<tr>
								<td>Blood Sugar (hb1ac) %</td>
								<td><input type="text" ${f:text("sugarhb1ac")} length="4"
									maxlength="4" onchange="javascript:trimSpaces('sugarhb1ac')"></td>
							</tr>
							<tr>
								<td>Respiration Rate(resting)</td>
								<td><input type="text" ${f:text("respiration")} length="2"
									maxlength="2" onchange="javascript:trimSpaces('respiration')"></td>
							</tr>
							<tr>
								<td width="50%">Pre-exisiting Conditions</td>
								<td colspan='2'><c:forEach var="existingDisease"
										items="${requestScope.allPreExistingDiseases}">
										<c:choose>
											<c:when test="${existingDisease.key.id == 1000}">
												<div style="display: none">
													<input class="chk" type="checkbox" name="existingIllness"
														value="${f:h(existingDisease.key.id)}" checked="checked" />${f:h(existingDisease.diseaseName)}
												</div>
											</c:when>
											<c:when test="${existingDisease.key.id != 1000}">
												<div style="display: show">
													<input class="chk" type="checkbox" name="existingIllness"
														value="${f:h(existingDisease.key.id)}"
														<c:if test="${requestScope.existingIllness == existingDisease.key.id}">
															checked='checked'
														</c:if> />${f:h(existingDisease.diseaseName)}
												</div>
											</c:when>
										</c:choose>
									</c:forEach></td>
							</tr>
						</table>
					</div>
					<div>
						<table>
							<tr>
								<td>
									<button type="submit" value='Find' name="CREPORTAL"
										onclick="javascript:submitForm('CREPORTAL')"
										style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
										class="fa fa-address-card-o">
										<font style="font-family: Arial;">&nbsp;Find&nbsp;</font>
									</button>
								</td>
								<td>
									<button type="reset" id="reset"
										style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
										class="fa fa-key">
										<font style="font-family: Arial;">Reset</font>
									</button>
								</td>
							</tr>
						</table>
					</div>
					<hr>
					<div id="declr" style="position: relative; font-size: 0.75em;">
						<div>
							Terms of Use:
							<div>
								<div>
									<span> <input type="checkbox" name="EULA"
										${requestScope.eulaStatus}></span> <span
										style="left: 10%; right: 10%"> Health results are
										calculated based on few of the prominent Medical journals
										published in public domain. It is highly recommended to seek
										medical advice from legally certified and authorized medical
										practitioner. 'sevhā' is a technology provider and not a
										certified medical body or practitioner. </span> <span
										style="text-decoration: underline">By clicking this
										checkbox you accept these terms of use.</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>