<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<link href="css/mobile.css" media="(max-width:1040px)" rel="stylesheet"
		type="text/css" />
	<link href="css/desktop.css" media="(min-width:1041px)"
		rel="stylesheet" type="text/css" />
	<link rel="stylesheet"
		href="css/webfont-medical-icons/css/wfmi-style.css" />
	<link rel="stylesheet"
		href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="Keywords" content="">
			<meta name="Description" content="">
				<title>Welcome to sevhā</title>
				<link href='https://fonts.googleapis.com/css?family=Nova+Round'
					rel='stylesheet' type='text/css'>
					<script src="scripts/jquery.min.js"></script>
					<script language=javascript type='text/javascript'>
						var uagent = navigator.userAgent.toLowerCase();
						//alert("Dimensions of document: " + $(document).width()
						//		+ "x" + $(document).height());
						//alert("Dimensions of window: " + $(window).width()
						//+"x" + $(window).height());
						//alert(uagent);
					</script>
					<!--  oAuth JS -->
					<script type="text/javascript">
						(function() {
							var po = document.createElement('script');
							po.type = 'text/javascript';
							po.async = true;
							po.src = 'https://plus.google.com/js/client:plusone.js';
							var s = document.getElementsByTagName('script')[0];
							s.parentNode.insertBefore(po, s);
						})();
					</script>
					<!-- JavaScript specific to this application that is not related to API calls -->
					<script
						src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
</head>
<body>
	<form name='auth' action='authenticate' method="post">
		<input name='actionParam' type='hidden' value='UPDATEPASSWORD' /> <input
			name='timezoneoffset' type='hidden' value='0' />
		<div z-index="_001" class="loginBox">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div class="loginBoxInternal">
				<div style="position: relative; text-align: right; right: 3%;">
					<h3>
						<i class="fa fa-user-circle-o" aria-hidden="true"></i>&nbsp;Update
						Password
					</h3>
				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<div id='errbox'>
						<div>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
				</c:if>
				<div
					style="position: relative; left: 0%; right: 0%; width: 100%; height: 100%; background-color: transparent;">
					<table>
						<tr>
							<td colspan='2' style='text-align: right'><a href='/health'>Home</a></td>
						</tr>
						<c:if test="${requestScope.validUserId == true}">
							<tr>
								<td colspan='2'>The password reset instructions have been sent to your registered email ${requestScope.maskedEmail}.</td>
							</tr>
						</c:if>

						<c:if test="${requestScope.validUserId == false}">
							<tr>
								<td colspan='2'>Invalid Userid. Please re-check registered
									email.</td>
							</tr>
						</c:if>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>