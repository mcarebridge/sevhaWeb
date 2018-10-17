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
				<title>Welcome to sevhā</title> <script language=javascript
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
					aria-hidden="true"></i>&nbsp;<a href="/">Home</a></span>
				<!--   
				<span
					style="padding-right: 2%"><i class="fa fa-tachometer"
					aria-hidden="true"></i>&nbsp;<a
					href="/health/register?actionParam=CAREPORTAL">Healthmeter</a>
				</span>
				-->
				<span style="padding-right: 2%"><i class="fa fa-tachometer"
					aria-hidden="true"></i>&nbsp;
					<button id="portalHdrBtn"
						onclick="submitForm('CAREPORTAL','register');">Healthmeter</button>
				</span> <span style="padding-right: 2%"><i class="fa fa-rocket"
					aria-hidden="true"></i>&nbsp;<a
					href="javascript:submitForm('APPLN','authenticate')">Launch</a></span>
				<!--  
					<span
					style="padding-right: 2%"><i class="fa fa-rocket"
					aria-hidden="true"></i>&nbsp;<a
					href="javascript:submitForm('APPLN','authenticate')">Launch</a></span>
					-->
				<span style="padding-right: 2%"><i class="fa fa-android"
					aria-hidden="true"></i>&nbsp;<a href="#block-5">playstore</a></span> <span
					style="padding-right: 2%"><i class="fa fa-address-card"
					aria-hidden="true"></i>&nbsp;<a href="#block-3">about us</a></span>
			</div>
		</div>

		<div id="content" class="content" style="z-index: -1">
			<div id="block-1"
				style="border-radius: 0px 0px 10px 10px; border-top: none;">
				<div class="imgbkgrd-1">
					<div class="bnnr__hdln">Life is Busy. Families are spread
						across cities. Managing family health cost is a challenge.</div>
				</div>
			</div>
			<br></br>
			<div id="block-1A"
				style="border-radius: 0px 0px 10px 10px; border-top: none;">
				<div class="imgbkgrd-1A">
					<div class="bnnr__hdln_1A" style="margin-top: 0%">November 14
						is Diabetes Day</div>
					<div class="bnnr__hdln_1A_sub">
						<ul
							style="padding-left: 10px; list-style-position: outside; list-style-type: square;">
							<li style="padding: 2px">415 million adults have diabetes (1
								in 11 adults)</li>
							<li style="padding: 2px">46.5% of those with diabetes have
								not been diagnosed</li>
							<li style="padding: 2px">12% of global health expenditure is
								spent on diabetes ($673 billion)</li>
							<li
								style="background-color: yellow; padding: 2px; border-radius: 5px; text-align: center;">
								<a href="/health/register?actionParam=HEALTHDATA">How sevha manages adult and juvelie diabetes?</a>
							</li>
						</ul>
					</div>

				</div>
			</div>
			<br></br>
			<div
				style="position: relative; height: 10px; background: transparent;"></div>
			<div id="block-2" style="position: relative">
				<div class="imgbkgrd-2">
					<div class="bnnr__hdln_1" style="position: relative;">
						<span> Every family have elderly. Managing health in old
							age is difficult and costly. </span> <br></br> <span> Few have
							suffered brain stroke, and old age disorder like Parkinson's,
							Dementia and Alzheimer's disease. Many of us are having long term
							disorders like Hypertension, Diabetes, Cardiovascular diseases
							and depression and that too at young. </span> <br></br> <span>
							Most of these disorders lead to complex situation for not able to
							comply with medication routine. Do not follow body symptoms and
							seek primary care in time. This leads to a medical emergency. </span>
					</div>
				</div>
			</div>
			<br></br>
			<div
				style="position: relative; height: 10px; background: transparent;"></div>
			<div id="block-3">
				<div class="imgbkgrd-3" style="position: relative;">
					<div class="bnnr__hdln_3">
						<span
							style="font-family: 'Prata', serif; color: #FFFFFF; font-size: 1.1em">sevhā</span>
						<span> provides a digital platform to seamlessly connect
							'you', 'your family' and 'your physician'. </span>
					</div>
					<br></br>
					<div
						style="position: relative; margin-left: 2%; width: 96%; margin-right: 2%; top: 0%;">
						<div style="position: relative;">
							<span class="bnnr__hdln_4">
								<button id="portalHdrBtn" style="color: #FFFFFF"
									; onclick="submitForm('CAREPORTAL','register');">Healthmeter</button>
							</span>
						</div>
						<div style="position: relative; margin-top: 3%; width: 100%;">
							<span
								style="background: transparent; margin-left: 0%; margin-top: 4%; font-size: 0.75em;">Click
								on Healthemeter to check your Vitals. </span>
						</div>
					</div>
				</div>
			</div>
			<div
				style="position: relative; height: 10px; background: transparent;"></div>
			<div
				style="height: 20px; background: transparent; position: relative;"></div>
			<div id="block-4"
				style="border-radius: 10px 10px 0px 0px; margin-bottom: 0px;">
				<div class="imgbkgrd-4">
					<div class="bnnr__hdln_2">
						<span
							style="font-family: 'Prata', serif; font-size: 4em; color: #FFFFFF;">sevhā</span>
					</div>
					<div class="bnnr__hdln_2"
						style="width: 80%; margin-left: 10%; margin-right: 10%">Track
						Symptoms. RxCompliance. Health Alerts.</div>
				</div>
			</div>
			<div
				style="position: relative; top: -2px; height: 10px; background: #f9f9f9; margin-top: 0px; padding-top: 0px;">
			</div>
			<div id="block-5">
				<div style="margin-left: 10%">sevhā is now available on Google
					Play.</div>
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