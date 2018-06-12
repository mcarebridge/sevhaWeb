<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-118289204-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());

	gtag('config', 'UA-118289204-1');
</script>
<script src="scripts/sevhacommon.js"></script>
<script src="scripts/jquery.min.js"></script>
<script src="scripts/modernizr.min.js"></script>
<script src="scripts/jquery.slicknav.js"></script>
<script src="scripts/rxScheduleBuilder.js"></script>
<script src="scripts/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
<script src="scripts/jquery-ui-1.11.1.custom/jquery-ui.min.js"></script>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-store,no-cache">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
			<link href="css/mobile.css" media="(max-width:1040px)"
				rel="stylesheet" type="text/css" />
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
									var s = document
											.getElementsByTagName('script')[0];
									s.parentNode.insertBefore(po, s);
								})();
							</script>
							<!-- JavaScript specific to this application that is not related to API calls -->
							<script
								src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>

							<script>
								function timezone() {
									var d = new Date();
									// calcs offset in mins from GMT
									var n = d.getTimezoneOffset();
									document.forms[0].timezoneoffset.value = n;
								}

								function changePassword(_actionParam) {
									document.forms[0].actionParam.value = "CHGPASSWORD";
									document.forms[0].submit();
									var chgPasswordButton = eval("document.forms[0]."
											+ _actionParam);
									chgPasswordButton.disabled = true;
									chgPasswordButton.value = "Please wait...";
									return true;
								}
							</script>
							
			<!--  script to handle spinner -->
			<script>
				
					$(window).load(function()
						{$('button').click(function() 
							{
								var _id = this.id;
								if(_id != 'reset')
									$('<div class=loadingDiv><i class="fa fa-gear fa-spin" style="font-size:3em;margin-left:48%;margin-top:20%;"></i></div>').prependTo(document.getElementById('loginBox'));
							});

						});
				 
			</script>							
</head>
<body onload="timezone()">
	<form name="auth" action="authenticate" method="post">
		<input name='actionParam' type='hidden' value='AUTH' /> <input
			name='timezoneoffset' type='hidden' value='0' />
		<div z-index="001" id="loginBox" class="loginBox">
			<div class='logo'>
				<span><a href="\"
					style="color: #FFFFFF; visited: #FFFFFF; text-decoration: none;">sevhā</a></span>
			</div>
			<div class="loginBoxInternal">
				<div style="position: relative; text-align: right; right: 3%;">
					<h3>
						<i class="fa fa-user-circle-o" aria-hidden="true"></i>&nbsp;Authentication
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
					style="position: relative; left: 10%; right: 0%; width: 100%; height: 100%; background-color: transparent;">
					<table>
						<tr>
							<td colspan='2' style='text-align: left'>Not a member ? <a
								href='/health/register?actionParam=NEWREG'>Register Today</a></td>
						</tr>
						<tr>
							<td>User name</td>
							<td>
								<!--  <input name="username" type='text' /> -->
								<div class="input-group margin-bottom-sm">
									<span class="input-group-addon"><i
										class="fa fa-envelope-o fa-fw"></i></span> <input
										class="form-control" name="username" type="text" onchange="javascript:makeLowerCase('username')"
										placeholder="Email address">
								</div>
							</td>
						</tr>
						<tr>
							<td>Password</td>
							<td>
								<!--  <input name="password" type='password' /> -->
								<div class="input-group">
									<span class="input-group-addon"><i
										class="fa fa-key fa-fw"></i></span> <input class="form-control"
										type="password" name="password" length="8" , maxlength="12" onchange="javascript:trimSpaces('password')"
										placeholder="Password">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan='2' style="background-color: transparent">
								<div style="align: center;">
									<span style="position: relative;">
										<button type="submit"
											style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
											class="fa fa-lock">
											<font style="font-family: Arial;">&nbsp;Login&nbsp;</font>
										</button>
									</span> <span>
										<button type="button" name="chgPassword"
											onclick="changePassword('chgPassword');"
											style="width: 120px; background-color: #8F7C2F; color: #FFFFFF; border: none; height: 2em; padding: 3px; border-radius: 4px;"
											class="fa fa-key">
											<font style="font-family: Arial;">Password?</font>
										</button>
									</span>
								</div>
							</td>
						</tr>
						<%-- 
						<tr>
							<td colspan="2"><a
								href='/health/register?actionParam=CAREPORTAL'>Care Portal</a></td>
						</tr>
						--%>
					</table>
				</div>
				<!--  
				<div id="gConnect">
					<button class="g-signin"
						data-scope="https://www.googleapis.com/auth/plus.login"
						data-requestvisibleactions="http://schemas.google.com/AddActivity"
						data-clientId="872880999297-h0rj3uas6laauuraj7po03eaa5ao6b3l.apps.googleusercontent.com"
						data-callback="onSignInCallback" data-theme="dark"
						data-cookiepolicy="single_host_origin"></button>
				</div>
				-->
				<!--  
				<div>
					<a
						href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile&state=%2Fprofile&redirect_uri=http://localhost/oauth2CallBack&response_type=code&client_id=872880999297-h0rj3uas6laauuraj7po03eaa5ao6b3l.apps.googleusercontent.com&approval_prompt=force">Test</a>
				</div>
				-->
			</div>
			<div id="footer"
				style="background-color: transparent; color: #bbbbbb;">
				<span
					style="position: relative; top: 42%; left: 15%; right: 15%; width: 70%; text-align: center;">&copy;
					sevhā LLC USA</span>
			</div>
		</div>
	</form>

</body>
</html>