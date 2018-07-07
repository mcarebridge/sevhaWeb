<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body onload="timezone()">
	<form name='register' action='authenticate' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
			<input name='timezoneoffset' type='hidden' value='0' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id='contents'>
				<div class='loggedInAs'>Welcome&nbsp;
					${sessionScope.profile.firstname}&nbsp;${sessionScope.profile.lastname}!</div>

				<div>
					<div>
						<h3>Dashboard</h3>
					</div>
					<c:if test="${requestScope.showErrBox == true}">
						<div class='errbox'>
							<div style='color: #003300'>* Missing information</div>
							<c:forEach var="e" items="${f:errors()}">
								<li>${f:h(e)}</li>
							</c:forEach>
						</div>
					</c:if>
					<!--  First Time User -->
					<div>
						<div>Welcome to sevhā!</div>
						<div>mCareBridge provide a network between a Physician, Care
							needy and Care giver. Following is the quick workflow involved</div>
						<div>
							<ul>
								<li type="square"><label>Step:1-Add a person, whose
										care you are managing. This person is known to you as your
										relative or as a friend.</label></li>
								<br>

								<li type="square"><label>Step:2-Add a Care-giver.
										Care-giver is a person who is responsible for providing care
										to a care needy.</label></li>
								<br>

								<li type="square"><label>Step:3:-Add a
										professionally qualified Doctor who can prescribe you
										medication.</label></li>
								<br>
								<li type="square"><label>Step:4:-Add a Prescription
										for a diagnose ailment for a Cared Person</label></li>
								<br>
								<li type="square"><label>Step:5:Download
										sevhā app from Android market place. This app will use
										the IMEI number of the Cared Person's cellphone to synchronize
										the Rx.</label></li>
								<br>
							</ul>
							<div>Well ! Thanks I have understood the process and would
								like to continue to use the application.</div>
							<div>
								<!--  
								<input type="button" value="Proceed"
									onClick="javascript:submitForm('1STTIMECHK')" />
								-->

								<button type="submit" value='Proceed' name="PROCEED"
									onclick="javascript:submitForm('1STTIMECHK')"
									style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
									class="fa fa-forward">
									<font style="font-family: Arial;">&nbsp;Proceed&nbsp;</font>
								</button>
							</div>
						</div>
					</div>
					<div>
						<!--  <h4>Page Footer</h4> -->
					</div>
				</div>
			</div>

		</div>

		</div>
	</form>
	<%@include file="../common/footer.jsp"%>