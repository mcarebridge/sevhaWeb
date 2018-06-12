<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caregiverForm' action='caregiver' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<h3><i class="icon-i-physical-therapy  fa-2x" aria-hidden="true"></i>&nbsp;Manage Care Givers</h3>
				</div>
				<div>
					<h4>
						<a class="list-group-item" href="javascript:submitForm('NEWCAG')"><i
							class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add
							Care Giver&nbsp;</a>&nbsp;
					</h4>
				</div>
				<div>
					<c:if test="${fn:length(careGiverList) eq 0}">
						<ul>
							<li>No Care Giver added</li>
						</ul>
					</c:if>
					<table>
						<c:forEach var="careGiver" items="${careGiverList}">
							<tr>
								<td><i class="fa fa-arrow-circle-o-right"
									aria-hidden="true"></i>&nbsp;<a
									href="javascript:submitFormWithKey('LDCAGDTLS','${f:h(careGiver.key.id)}')">${f:h(careGiver.firstName)}&nbsp;${f:h(careGiver.lastName)}</a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>