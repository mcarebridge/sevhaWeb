<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='' action='POST'>
		<input name='actionParam' type='hidden' />
		<div
			style="width: auto; height: auto; margin: 0 auto; align: center; background-color: transparent; border-style: none; border-width: 1px 1px 1px 1px;">
			<div id='logo' >
				<span class='logo'>mCareBridge</span>
				<span style='align:right;'> <a href="javascript:submitForm('LOGOUT')">Logout</a></span>
			</div>
			<div id='body'>
				<div>
					<h1>Page One</h1>
				</div>
				<div>
					<ul>
						<li><a href="#page2">Page Two</a></li>
						<li><a href="#page3">Page Three</a></li>
						<li><a href="#page4">Page Four</a></li>
					</ul>
				</div>
				<div>
					<h4>Page Footer</h4>
				</div>
			</div>
		</div>
	</form>
</body>
<%@include file="../common/footer.jsp"%>