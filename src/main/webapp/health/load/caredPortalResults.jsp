<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='profileupd' action='register' method='POST'>
		<input name='actionParam' value='UPDPRO' type='hidden' />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>
						<i class="fa fa-user-o fa-2x" aria-hidden="true"></i>&nbsp;Health
						Results
					</h3>
				</div>
				<div>
					<div>
						<span>Age :</span> <span>${age}&nbsp;Years</span> <span>Gender:</span> <span>
							<c:if test="${gender== 1}">Male</c:if> <c:if test="${gender== 2}">Female</c:if>
							<c:if test="${gender== -1}">Others</c:if>
						</span>
					</div>
				</div>
				<div>
					<table style="border-style: dotted; border-width: 1px">
						<tr style="border-style: dotted; border-width: 1px">
							<th>Vitals</th>
							<th>Unit</th>
							<th>IDEAL Value</th>
							<th>Provided Value</th>
							<th>Category</th>
							<th>Status</th>
						</tr>

						<c:forEach var="vsOutPutDTOMap" items="${vitalSignOutPutMap}">
							<c:set var="vsOutPutDTO" value="${vsOutPutDTOMap.value}" />
							<tr>
								<td style="border-style: dotted; border-width: 1px">${f:h(vsOutPutDTO.vitalSignSubType)}</td>
								<td style="border-style: dotted; border-width: 1px">${f:h(vsOutPutDTO.vitalSignCatValIDEAL.vitalSignCategory.model.vitalSignSubType.model.vitalSignSubTypeUnit)}</td>
								<td style="border-style: dotted; border-width: 1px">
									${f:h(vsOutPutDTO.vitalSignCatValIDEAL.minTargetValue)} -
									${f:h(vsOutPutDTO.vitalSignCatValIDEAL.maxTargetValue)}</td>
								<td style="border-style: dotted; border-width: 1px">${f:h(vsOutPutDTO.vitalSignValueProvided)}</td>
								<td style="border-style: dotted; border-width: 1px">${f:h(vsOutPutDTO.vitalSignCatValReported.vitalSignCategory.model.vitalSignCategoryName)}</td>
								<td style="border-style: dotted; border-width: 1px"><c:choose>
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
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>