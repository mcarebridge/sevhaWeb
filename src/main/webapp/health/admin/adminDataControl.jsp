<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='adminForm' action='adminData' method='POST'>
		<input name='actionParam' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div id="contents">
				<div>
					<h3>Create Admin DATA</h3>
				</div>
				<div>
					<li><a href="javascript:submitForm('SPECIALITY')">Add
							Speciality Data</a></li>
				</div>
				<div>
					<li><a href="javascript:submitForm('MASTER')">Add
							Master Data</a></li>
				</div>
				<div>
					<li><a href="javascript:submitForm('LDMASTERDATA')">Load
							Master Data</a></li>
				</div>
				<div>
					<li><a href="javascript:submitForm('VITALDATA')">Load
							Vital Data Test Link</a></li>
				</div>
			</div>
		</div>
	</form>
</body>
<%@include file="../common/footer.jsp"%>