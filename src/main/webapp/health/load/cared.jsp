<%@include file="../common/header.jsp"%>
<%@page pageEncoding="UTF-8" isELIgnored="false"%>
<body>
	<form name='caredForm' action='cared' method='POST'>
		<input name='actionParam' type='hidden' /> <input type='hidden'
			${f:text("key")} />
		<div id="main">
			<div class='logo'>
				<span>sevhā</span>
			</div>
			<%@include file="../common/mobileMenu.jsp"%>
			<div id="contents">
				<div>
					<span></span>
					<span><h3><i class="icon-i-family-practice fa-2x" aria-hidden="true"></i>&nbsp;Managed Cared Person(s)</h3></span>
				</div>
				<div>
					<h4>
					<a class="list-group-item"
						href="javascript:submitForm('NEWCAD')"><i
						class="fa fa-user-plus fa-x" aria-hidden="true"></i>&nbsp;Add a Cared&nbsp;</a>&nbsp;
					</h4>
				</div>
				<div>
					<c:if test="${fn:length(caredPersons) eq 0}">
						<ul>
							<li>No cared person added</li>
						</ul>
					</c:if>
					<table>
						<c:forEach var="caredOnes" items="${caredPersons}">
							<tr>
								<td><i class="fa fa-arrow-circle-o-right" aria-hidden="true"></i>&nbsp;<a
									href="javascript:submitFormWithKey('LDUPDCAD','${f:h(caredOnes.key.id)}')">${f:h(caredOnes.firstName)}</a>
								</td>
								<!--  
								<td><a href="javascript:submitFormWithKey('UPDVITALPARA','${f:h(caredOnes.key.id)}')">Update Vital Parameters</a></td>
								-->
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
	</form>
	<%@include file="../common/footer.jsp"%>