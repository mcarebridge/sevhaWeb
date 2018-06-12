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


<link href="css/mobile.css" media="(max-width:1040px)" rel="stylesheet"
	type="text/css" />
<link href="css/desktop.css" media="(min-width:1041px)" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="css/webfont-medical-icons/css/wfmi-style.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />

<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-store,no-cache">
		<meta name="viewport"
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<meta name="Keywords" content="" />
			<meta name="Description" content="" />
			<title><fmt:message key="${requestScope.pageTitle}" /></title> <script
				src="scripts/jquery.min.js"></script>
			<script language=javascript type='text/javascript'>
				var uagent = navigator.userAgent.toLowerCase();
				//alert("Dimensions of document: " + $(document).width() + "x" + $(document).height());
				//alert("Dimensions of window: " + $(window).width() + "x"
				//		+ $(window).height());
			</script>

			<style>
<!--
code for slider -->#custom-handle {
	width: 3em;
	height: 1.6em;
	top: 50%;
	margin-top: -.8em;
	text-align: center;
	line-height: 1.6em;
}
</style>

			<script>
				$(function() {
					$("#slider").slider({
						value : 100,
						min : 6,
						max : 21,
						step : 1,
						slide : function(event, ui) {
							$("#amount").val(ui.value + " Hrs");
						}
					});
					//$("#amount").val($("#slider") + "Hrs".slider("value"));
				});
			</script>
			<!-- code for slider -->

			<script language=javascript type='text/javascript'>
				function submitForm(_actionParam) {
					//alert(_actionParam);
					document.forms[0].actionParam.value = _actionParam;
					document.forms[0].submit();
					var submitButton = eval("document.forms[0]." + _actionParam);
					submitButton.disabled = true;
					submitButton.value = "Please wait...";
					return true;

				}

				function submitRegisterForm(_actionParam) {
					//alert(_actionParam);
					getValueUsingClass();
					submitForm(_actionParam);
				}

				function submitFormWithKey(_actionParam, _key) {
					//alert(_actionParam);
					document.forms[0].actionParam.value = _actionParam;
					document.forms[0].key.value = _key;
					document.forms[0].submit();
					var submitButton = eval("document.forms[0]." + _actionParam);
					submitButton.disabled = true;
					submitButton.value = "Please wait...";
					return true;
				}
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
			<script>
				$(function() {
					$("#datepicker").datepicker({
						showOn : "button",
						buttonImage : "images/calendar.gif",
						minDate : "-1",
						buttonImageOnly : true,
						buttonText : "Select date",
						changeMonth : true,
						changeYear : true,
						autoSize : true,
						beforeShow : function() {
							$(".ui-datepicker").css('font-size', 14)
						}
					});
				});

				$(function() {
					$("#dobdatepicker").datepicker({
						showOn : "button",
						buttonImage : "images/calendar.gif",
						minDate : "-75y",
						maxDate : "-21y",
						buttonImageOnly : true,
						buttonText : "Select Date of Birth",
						changeMonth : true,
						changeYear : true,
						autoSize : true,
						yearRange : "-100:+0",
						beforeShow : function() {
							$(".ui-datepicker").css('font-size', 14)
						}
					});
				});

				$(function() {
					$("#dobdatepickercared").datepicker({
						showOn : "button",
						buttonImage : "images/calendar.gif",
						minDate : "-100y",
						maxDate : "-0y",
						buttonImageOnly : true,
						buttonText : "Select Date of Birth",
						changeMonth : true,
						changeYear : true,
						autoSize : true,
						yearRange : "-100:+0",
						beforeShow : function() {
							$(".ui-datepicker").css('font-size', 14)
						}
					});
				});
			</script>

			<script>
				function submitCaredPersonForm(_actionParam) {
					//alert(_actionParam);
					document.forms[0].actionParam.value = _actionParam;
					getValueUsingClass();
					document.forms[0].submit();
					var submitButton = eval("document.forms[0]." + _actionParam);
					submitButton.disabled = true;
					submitButton.value = "Please wait...";
					return true;
				}

				function submitEmergencyContactForm(_actionParam) {
					//alert(_actionParam);
					document.forms[0].actionParam.value = _actionParam;
					document.getElementById("emerDetails").style.display = "block";
					document.forms[0].submit();
					var submitButton = eval("document.forms[0]." + _actionParam);
					submitButton.disabled = true;
					submitButton.value = "Please wait...";
					return true;
				}

				function getValueUsingClass() {
					/* declare an checkbox array */
					var chkArray = [];

					/* look for all checkboes that have a class 'chk' attached to it and check if it was checked */
					$(".chk:checked").each(function() {
						chkArray.push($(this).val());
					});

					/* we join the array separated by the comma */
					var _selectedPreCondition;
					_selectedPreCondition = chkArray.join(',') + ",";
					document.forms[0].selectedPreCondition.value = _selectedPreCondition;

					/* check if there is selected checkboxes, by default the length is 1 as it contains one single comma */
					/**
					if (selected.length > 1) {
						alert("You have selected " + selected);
					} else {
						alert("Please at least one of the checkbox");
					}
					 **/
				}

				function checkERStatus() {
					var e = document.forms[0].careneedy;
					var str = e.options[e.selectedIndex].text;

					var pos = str.indexOf("Added");

					if (pos == -1) {
						//alert(document.getElementById("emerDetails").id);
						document.getElementById("emerDetails").style.display = "block";
						document.getElementById("cremr").disabled = false;
						document.getElementById("cremr").style.color = "#FFFFFF";

					} else {
						document.getElementById("emerDetails").style.display = "none";
						document.getElementById("cremr").disabled = true;
						document.getElementById("cremr").style.color = "#DDDDDD";
					}

				}

				function setCPtoSession() {
					var e = document.forms[0].careneedy;
					var _selectedCaredId = e.options[e.selectedIndex].value;
					document.forms[0].selectedCaredId.value = _selectedCaredId;
					document.forms[0].actionParam.value = "SLTNEEDYCP";
					document.forms[0].submit();
					return true;
				}

				function resetForm($form) {
					$form
							.find(
									'input:text, input:password, input:file, select, textarea')
							.val('');
					$form.find('input:radio, input:checkbox').removeAttr(
							'checked').removeAttr('selected');
				}
			</script>

			<!--  Portal Scripts -->

			<script>
				function toFeet() {
					var n = document.forms[0].height.value;

					var realFeet = (n / 30);

					if (isNaN(realFeet)) {
						realFeet = 0;
					}
					var feet = Math.floor(realFeet);
					var inches = Math.round((realFeet - feet) * 12);
					document.getElementById("caredHeight").innerHTML = feet
							+ "&nbsp;Ft&nbsp;" + inches + '&nbsp;inch';
				}

				function toLBS() {
					var n = document.forms[0].weight.value;

					var realLBS = (n / 0.453592);

					if (isNaN(realLBS)) {
						realFeet = 0;
					}
					var lbs = Math.floor(realLBS);
					document.getElementById("caredWeight").innerHTML = lbs
							+ "&nbsp;lbs";
				}

				function setTemptoForm() {
					var e = document.forms[0].temperature;
					var _temperatureId = e.options[e.selectedIndex].value;
					document.forms[0].selectedtemperatureId.value = _temperatureId;
					return true;
				}

				function makeLowerCase(elName) {
					var _str = "document.forms[0]." + elName + ".value";
					var _fieldValue = eval(_str);
					_fieldValue = _fieldValue.toLowerCase().trim();
					var _str1 = "document.forms[0]." + elName
							+ ".value = _fieldValue";
					eval(_str1);
				}

				function trimSpaces(elName) {
					var _str = "document.forms[0]." + elName + ".value";
					var _fieldValue = eval(_str);
					_fieldValue = _fieldValue.trim();
					var _str1 = "document.forms[0]." + elName
							+ ".value = _fieldValue";
					eval(_str1);
				}
			</script>

			<!--  script to handle spinner -->
			<script>
				
					$(window).load(function()
						{$('button').click(function() 
							{
								var _id = this.id;
								if(_id != 'reset')
									$('<div class=loadingDiv><i class="fa fa-gear fa-spin" style="font-size:3em;margin-left:48%;margin-top:20%;"></i></div>').prependTo(document.getElementById('main'));
							});

						});
					
					$(window).load(function()
							{$('a').click(function() 
								{
								var _id = this.getAttribute("href");
								//alert(_id);
								if(_id != '#' | _id.conatins('showAdvMeter'))
									$('<div class=loadingDiv><i class="fa fa-gear fa-spin" style="font-size:3em;margin-left:48%;margin-top:20%;"></i></div>').prependTo(document.getElementById('main'));
								});

							});
					
				 
			</script>
</head>