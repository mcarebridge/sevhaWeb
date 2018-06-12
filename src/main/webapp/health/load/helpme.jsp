<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='register' action='authenticate' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
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
						<h3>Help Me</h3>
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
						<div>Welcome to mCareBridge!</div>
						<div>mCareBridge provide a network between a Physician, Care
							needy and Care giver. Following is the quick workflow involved</div>
						<div>
							<ul>
								<li type="square">Step:1-Add a Cared Person, whose care you
									are managing. This person is known to you as your relative or
									as a friend.</li>
								<br>
								<li type="square">Step:2-Add a Care-giver. Care-giver is a
									person who is responsible for providing care to a Cared Person.</li>
								<br>
								<li type="square">Step:3:-Add a professionally qualified
									Doctor who can prescribe you medication.</li>
								<br>
								<li type="square">Step:4:-Add a (Rx)Prescription for a
									diagnose ailment for a Care-needy.</li>
								<br>
								<li type="square">Step:5:Download mCareBridge app from
									Android market place. This app will use the IMEI number of the
									Cared Person's cellphone to synchronize the Rx.</li>
								<br>
							</ul>
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