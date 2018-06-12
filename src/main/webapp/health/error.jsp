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
		type="text/css">
		<link href="css/desktop.css" media="(min-width:1041px)"
			rel="stylesheet" type="text/css">
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
				<meta name="Keywords" content="">
					<meta name="Description" content="">
						<title>Welcome to mCareBridge</title>
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
									var s = document
											.getElementsByTagName('script')[0];
									s.parentNode.insertBefore(po, s);
								})();
							</script>
							<!-- JavaScript specific to this application that is not related to API
     calls -->
							<script
								src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

							<script language=javascript type='text/javascript'>
								function submitForm(_actionParam) {
									//alert(_actionParam);
									document.forms[0].actionParam.value = _actionParam;
									document.forms[0].submit();
									var submitButton = eval("document.forms[0]."
											+ _actionParam);
									submitButton.disabled = true;
									submitButton.value = "Please wait...";
									return true;

								}

								function submitFormWithKey(_actionParam, _key) {
									//alert(_actionParam);
									document.forms[0].actionParam.value = _actionParam;
									document.forms[0].key.value = _key;
									document.forms[0].submit();
									var submitButton = eval("document.forms[0]."
											+ _actionParam);
									submitButton.disabled = true;
									submitButton.value = "Please wait...";
									return true;
								}
							</script>
</head>
<body>
	<form name='auth' action='authenticate' methoud='POST'>
		<input name='actionParam' type='hidden' value='AUTH' />
		<div z-index="_001" class="loginBox">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<div class="loginBoxInternal">
				<div>
					<h3>Error</h3>
				</div>
				<c:if test="${requestScope.showErrBox == true}">
					<div class='errbox'>
						<div style='color: #003300'>* Missing information</div>
						<c:forEach var="e" items="${f:errors()}">
							<li>${f:h(e)}</li>
						</c:forEach>
					</div>
				</c:if>
				<div style='text-align: center'>
					<label>Opps something went wrong ! For security reasons we
						need to restart your session.</label> <label><br><a
							href="/">Please click here to log
								again</a></label>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
