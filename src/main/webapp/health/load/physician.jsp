<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='physicianForm' action='physician' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3>
						<i class="icon-i-administration fa-2x" aria-hidden="true"></i>&nbsp;Your
						Physician(s)
					</h3>
				</div>
				<div>
					<!--  
					<input type='button' value="Add Physician"
						onclick="javascript:submitForm('NEWPHY');" />
					-->
					<h4>
						<a class="list-group-item" href="javascript:submitForm('NEWPHY')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add a
							Physician&nbsp;</a>&nbsp;
					</h4>
				</div>
				<div>
					<c:if test="${fn:length(physicians) eq 0}">
						<ul>
							<li>No Physician added</li>
						</ul>
					</c:if>
					<div style="position: relative; background-color: transparent;">
						<table
							style="position: relative; min-width: 50%; border-spacing: 2px; border: 1px; border-style: solid; border-color: #cccccc;">
							<tr>
								<th>Physician</th>
								<th>Speciality</th>
							</tr>
							<c:forEach var="physician" items="${physicians}">
								<tr style="background-color: #FFFFFF;">
									<td style="padding: 5px;"><i class="fa fa-arrow-circle-o-right"
										aria-hidden="true"></i>&nbsp; <a
										href="javascript:submitFormWithKey('LDUPDPHY','${f:h(physician.key.id)}')">${f:h(physician.firstname)}&nbsp;${f:h(physician.lastname)}</a></td>
									<td style="padding: 5px;">${f:h(physician.physicianSpeciality.model.speciality)}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>