<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='profileupd' action='register' method='POST'>
		<input name='actionParam' value='UPDPRO' type='hidden' />
		<div class='logo'>
			<span><a href="\"
				style="color: #FFFFFF; visited: #FFFFFF; text-decoration: none;">sevhā</a></span>
		</div>
		<div id="main">
			<div id="contents" style="width: fit-content;">
				<div>
					<h3>
						<i class="fa fa-user-o fa-2x" aria-hidden="true"></i>&nbsp;Health
						Results
					</h3>
				</div>
				<div>
					<div class='logout'>
						<span> <a href="javascript:submitForm('CAREPORTAL')">Go
								Back</a>
					</div>
				</div>
				<br>
				<div style="font-size: 0.75em;">
					<div style="padding: 2px;">
						<span><i class="fa fa-bolt" aria-hidden="true"
							style="color: RED;">&nbsp;EMERGENCY</i></span>
					</div>
					<div style="padding: 2px;">
						<span><i class="fa fa-star" aria-hidden="true"
							style="color: RED">&nbsp;Need Hospitalization </i> </span>
					</div>
					<div style="padding: 2px;">
						<span> <i class="fa fa-star" aria-hidden="true"
							style="color: ORANGE">&nbsp;</i> <i class="fa fa-star"
							aria-hidden="true" style="color: ORANGE">&nbsp;See Health
								Practitioner</i>
						</span>
					</div>
					<div style="padding: 2px;">
						<span> <i class="fa fa-star" aria-hidden="true"
							style="color: GREEN">&nbsp;</i> <i class="fa fa-star"
							aria-hidden="true" style="color: GREEN">&nbsp;</i> <i
							class="fa fa-star" aria-hidden="true" style="color: GREEN">&nbsp;You
								are doing good.</i>
						</span>
					</div>
				</div>
				<br>
				<div style="font-size: 0.90em;">
					<div
						style="margin-left: 0%; background: #F9F9F9; padding: 2px 5px 2px 2px; min-height: 1.5em; max-height: 2.5em;">
						<span style="font-weight: bold;">Age :</span> <span style="">${age}&nbsp;Years</span>
						<span style="font-weight: bold; margin-left: 5%;">Gender:</span> <span>
							<c:if test="${gender== 1}">Male</c:if> <c:if test="${gender== 2}">Female</c:if>
							<c:if test="${gender== -1}">Others</c:if>
						</span> <span style="font-weight: bold; margin-left: 5%;">Date of
							Results:</span> <span>${currDate}</span>
					</div>
				</div>

				<div style="height: 10px;"></div>
				<c:forEach var="vsOutPutDTOMap" items="${vitalSignOutPutMap}">
					<c:set var="vsOutPutDTO" value="${vsOutPutDTOMap.value}" />
					<div class="careResultsTbl">
						<table style="border-spacing: 0px; margin: 0px; width: 100%;">
							<tr>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; border-right-width: 0px; padding: 5px; width: 50%;">Vitals</td>
								<c:set var="origStr" value="${f:h(vsOutPutDTO.vitalSignSubType)}"/>
								<c:set var="replStr" value="${fn:replace(origStr, '_', ' ')}" />
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; padding: 5px; width: 50%;">${replStr}</td>
							</tr>
							<tr>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; border-right-width: 0px; padding: 5px; width: 50%;">IDEAL
									Value</td>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; padding: 5px; width: 50%;">
									<fmt:parseNumber var="j" type="number"
										value="${f:h(vsOutPutDTO.vitalSignCatValIDEAL.minTargetValue)}" />
									<c:choose>
										<c:when test="${j < 0}">Less than
         							</c:when>
										<c:otherwise>
            							${f:h(vsOutPutDTO.vitalSignCatValIDEAL.minTargetValue)}
         							</c:otherwise>
									</c:choose> - ${f:h(vsOutPutDTO.vitalSignCatValIDEAL.maxTargetValue)}
								</td>
							</tr>
							<tr>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; border-right-width: 0px; padding: 5px; width: 50%;">Provided
									Value</td>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; padding: 5px; width: 50%;">${f:h(vsOutPutDTO.vitalSignValueProvided)}
									${f:h(vsOutPutDTO.vitalSignCatValIDEAL.vitalSignCategory.model.vitalSignSubType.model.vitalSignSubTypeUnit)}
								</td>
							</tr>
							<tr>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; border-right-width: 0px; padding: 5px; width: 50%;">Category</td>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; padding: 5px; width: 50%;">
									${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.vitalSignCategoryName)}
								</td>
							</tr>
							<tr>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; border-right-width: 0px; padding: 5px; width: 50%;">Health
									Status</td>
								<td
									style="border-style: solid; border-color: #cccccc; border-spacing: 0px; padding: 5px; width: 50%;"><c:choose>
										<c:when
											test="${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.klass) == 'R'}">
											<i class="fa fa-star" aria-hidden="true" style="color: RED">&nbsp;</i>
										</c:when>
										<c:when
											test="${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.klass) == 'G'}">
											<i class="fa fa-star" aria-hidden="true" style="color: GREEN">&nbsp;</i>
											<i class="fa fa-star" aria-hidden="true" style="color: GREEN">&nbsp;</i>
											<i class="fa fa-star" aria-hidden="true" style="color: GREEN">&nbsp;</i>
										</c:when>
										<c:when
											test="${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.klass) == 'Y'}">
											<i class="fa fa-star" aria-hidden="true"
												style="color: ORANGE">&nbsp;</i>
											<i class="fa fa-star" aria-hidden="true"
												style="color: ORANGE">&nbsp;</i>
										</c:when>

										<c:when
											test="${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.klass) == 'E'}">
											<i class="fa fa-bolt" aria-hidden="true" style="color: RED;">&nbsp;EMERGENCY</i>
										</c:when>
									</c:choose></td>
							</tr>
						</table>
					</div>
					<hr style="width: 80%;">
				</c:forEach>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>