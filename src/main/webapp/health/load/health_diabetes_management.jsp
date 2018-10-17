<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<html>
<head>
<link href="css/mobile.css" media="(max-width:1040px)" rel="stylesheet"
	type="text/css" />
<link href="css/desktop.css" media="(min-width:1041px)" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="css/webfont-medical-icons/css/wfmi-style.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-store,no-cache">
			<meta name="viewport"
				content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
				<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
				<meta name="Keywords"
					content="Healthcare,Technology,Health Insurance,Longterm care
				Apple Android integration, Digial services, Primarycare Physician, Hospitals 
				" />
				<meta name="Description" content="" />
				<title>sevhā Health Management</title> <script language=javascript
					type='text/javascript'>
					var uagent = navigator.userAgent.toLowerCase();
					//alert("Dimensions of document: " + $(document).width() + "x" + $(document).height());
					//alert("Dimensions of window: " + $(window).width() + "x"
					//		+ $(window).height());
				</script>

				<script src="scripts/jquery.min.js"></script>
				<script src="scripts/modernizr.min.js"></script>
				<script src="scripts/jquery.slicknav.js"></script>
				<script src="scripts/rxScheduleBuilder.js"></script>
				<script src="scripts/jquery-ui-1.11.1.custom/jquery-ui.js"></script>
				<script src="scripts/jquery-ui-1.11.1.custom/jquery-ui.min.js"></script>

				<!--  CSS for Menu -->
				<link rel="stylesheet" href="css/style.css" />
				<link rel="stylesheet" href="css/slicknav.css" />
				<link rel="stylesheet"
					href="scripts/jquery-ui-1.11.1.custom/jquery-ui.css" />
				<link rel="stylesheet"
					href="scripts/jquery-ui-1.11.1.custom/jquery-ui.min.css" />
				<link rel="stylesheet"
					href="scripts/jquery-ui-1.11.1.custom/jquery-ui.structure.css" />

				<script language=javascript type='text/javascript'>
					function submitForm(_actionParam, _action) {
						//alert(_actionParam);
						document.forms[0].action = _action;
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

				<!--  script to handle spinner -->
				<script>
					$(window)
							.load(
									function() {
										$('button')
												.click(
														function() {
															var _id = this.id;
															//Note : change the div tag as per the page. e.g : 'content'
															if (_id != 'reset')
																$(
																		'<div class=loadingDiv><i class="fa fa-gear fa-spin" style="font-size:3em;margin-left:48%;margin-top:20%;"></i></div>')
																		.prependTo(
																				document
																						.getElementById('content'));
														});

									});
				</script>
</head>
<body
	style="position: absolute; border-style: none; width: 100%; left: 0%; padding-left: 0px; margin-left: 0%; background-color: #FFFFFF;">
	<form name="auth" action="authenticate" method="post">
		<input name='actionParam' type='hidden' value='' /> <input
			name='timezoneoffset' type='hidden' value='0' />
		<div id="headerSection" style="z-index: 999">
			<div>
				<span class='newlogo'>sevhā</span>
			</div>
			<div id="banner">
				<span style="padding-right: 2%"><i class="fa fa-home"
					aria-hidden="true"></i>&nbsp;<a href="/">Home</a></span> <span
					style="padding-right: 2%"><i class="fa fa-tachometer"
					aria-hidden="true"></i>&nbsp;Diabetes Management</span>
			</div>
		</div>
		<br></br>
		<div id="content" class="content" style="z-index: -1">
			<div id="block-3">
				<div class="imgbkgrd-5" style="position: relative;">
					<br></br>
					<div class="bnnr__hdln_3_a" style="margin-left: 3%">
						<span>Diabetes is a major cause of blindness, kidney
							failure, heart attacks, stroke and lower limb amputation</span>
					</div>
					<br></br>
					<div class="bnnr__hdln_3_a" style="margin-left: 3%">
						<span> The global prevalence of diabetes among adults over
							18 years of age has risen from 4.7% in 1980 to 8.5%. </span>
					</div>
				</div>
			</div>
			<br></br>
			<div id="block-2" style="position: relative">
				<div style="position: relative;">
					<ul
						style="padding-left: 40px; list-style-position: outside; list-style-type: square;">
						<li>Diabetes self-care is a critical aspect of disease
							management for adults with diabetes. Since family members can
							play a vital role in a patient's disease management, involving
							them in self-care interventions may positively influence
							patient's diabetes outcomes.</li>
						<li>Diabetes self-management education (DSME) is a critical
							component of care for all individuals with diabetes. For adults
							with type 2 diabetes, engaging in diabetes self-care activities
							is associated with improved glycemic control and can prevent
							diabetes-related complications. Much of a patient's
							diabetes management takes place within their family and social
							environment.</li>
						<li>Family members can actively support and care for patients
							with diabetes. Most individuals live within a household that has
							a great influence on diabetes-management behaviors.</li>
					</ul>
					<ul
						style="padding-left: 40px; list-style-position: outside; list-style-type: square;">
						<li>Providing diabetes education to just the individual with
							type 2 diabetes could limit its impact on patients, since family
							may play such a large role in disease management. Family-based
							approaches to chronic disease management emphasize the context in
							which the disease occurs, including the family's physical
							environment, as well as the educational, relational, and personal
							needs of patients and family members</li>
					</ul>
				</div>
				<br></br>
				<div class="bnnr__hdln_3_a" style="margin-left: 5%; width: 80%">
					<span>sevhā provides a simple platform for managing
						diabetes for children and adult by connecting your family or
						professional CareGiver.</span>
				</div>

			</div>
			<div
				style="position: relative; top: -2px; height: 10px; background: #f9f9f9; margin-top: 0px; padding-top: 0px;">
			</div>
			<div id="block-5">
				<div style="margin-left: 10%">sevhā is now available on
					Google Play.</div>
				<div style="margin-left: 10%">
					<a href="https://play.google.com/store/apps/details?id=com.phr.ade">
						<img src="images/google-play-badge.png" alt="" width="226"
						height="88">
					</a>
				</div>
			</div>
		</div>
		<!--  Web text contents end -->
		<div
			style="position: relative; top: 0px; background-color: #525456; min-height: 100px;">
			<div
				style="position: relative; top: 40px; text-align: center; font-size: 0.8em; background: transparent; color: #cccccc; border-color: #dddddd; border-width: 1px 1px 1px 1px; border-style: none;">
				<div style="border-width: 0px 0px 0px 0px; border-style: none">
					<span>&copy;</span> <span style="">sevhā LLC</span>
				</div>
				</br>
				<div class="terms">
					<a href="/health/legal">Terms of Use</a>
				</div>
			</div>
		</div>
	</form>

	<!--  
	<script>
		window.onscroll = function() {
			myFunction()
		};

		var header = document.getElementById("myHeader");
		var sticky = header.offsetTop;

		function myFunction() {
			if (window.pageYOffset >= sticky) {
				header.classList.add("sticky");
			} else {
				header.classList.remove("sticky");
			}
		}
	</script>
	-->
</body>
</html>