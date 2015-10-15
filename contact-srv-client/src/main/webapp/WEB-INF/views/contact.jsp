<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-4">
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">Contacts</h3>
          </div>
          <div class="panel-body">
            <ol class="list-group">
                <c:forEach items="${contactList}" var="contact" varStatus="contactStatus">
                    <li class="list-group-item"><a href="<c:url value="/contacts/${contact.id}#basic_data"/>" data-pjax>${contact.firstName} ${contact.lastName}</a></li>
                </c:forEach>
            </ol>
          </div>
          <!--
          <div class="panel-footer">Pagination</div>
          -->
        </div>
    </div>
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-8">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<form:form id="contact" name="contact" servletRelativeAction="/contacts/${contact.version eq 0 ? '' : contact.id}" method="POST" enctype="application/x-www-form-urlencoded" autocomplete="on" modelAttribute="contact" class="form-horizontal" role="form" data-pjax="data-pjax">
					<fieldset>
						<a id="basic_data"></a><section>
                        <form:hidden path="id" />
						<form:hidden path="createdAt" />
						<form:hidden path="createdBy" />
						<form:hidden path="version" />

						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Basic data</h3>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="salutation" class="col-xs-3 col-sm-3 col-md-3 col-lg-2 control-label">Salutation</label>
									<div class="col-xs-8 col-sm-7 col-md-7 col-lg-5">
										<form:select path="salutation" class="form-control">
											<form:option value="" label="" />
											<form:options />
										</form:select>
									</div>
								</div>
								<div class="form-group">
									<label for="title" class="col-xs-3 col-sm-3 col-md-3 col-lg-2 control-label">Title</label>
									<div class="col-xs-8 col-sm-7 col-md-7 col-lg-5">
										<form:select path="title" class="form-control">
											<form:option value="" label="" />
											<form:options />
										</form:select>
									</div>
								</div>
								<t:input path="firstName" label="Firstname" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
								<t:input path="lastName" label="Lastname" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
							</div>
						</div>
                        </section>
						<!-- addresses -->
                        <a id="address_data"></a><section>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Address data<button class="btn btn-default btn-xs pull-right" type="submit" id="addAddress" name="addAddress" value="addAddress"><i class="icon-plus"></i> Add</button></h3>
							</div>
							<div class="panel-body">
								<c:forEach items="${contact.addresses}" var="address" varStatus="addressStatus">
									<form:hidden path="addresses[${addressStatus.index}].id" />
									<form:hidden path="addresses[${addressStatus.index}].createdAt" />
									<form:hidden path="addresses[${addressStatus.index}].createdBy" />
									<form:hidden path="addresses[${addressStatus.index}].version" />
		
									<t:input path="addresses[${addressStatus.index}].street" label="Street" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
									<t:input path="addresses[${addressStatus.index}].street2" label="Street2" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
									<t:input path="addresses[${addressStatus.index}].city" label="City" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
									<t:input path="addresses[${addressStatus.index}].zipCode" label="ZIP Code" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
									<t:input path="addresses[${addressStatus.index}].state" label="State" controlClass="col-xs-8 col-sm-7 col-md-7 col-lg-5" labelClass="col-xs-3 col-sm-3 col-md-3 col-lg-2" />
		
									<div class="form-group ">
										<label class="col-xs-3 col-sm-3 col-md-3 col-lg-2 control-label" for="country">Country</label>
										<div class="col-xs-8 col-sm-7 col-md-7 col-lg-5">
											<form:select path="addresses[${addressStatus.index}].country" class="form-control">
												<form:option value="" label="" />
												<form:options items="${countryList}" />
											</form:select>
										</div>
									</div>
								</c:forEach>
							</div>
						</div>
                        </section>
						<!-- phone numbers -->
                        <a id="phone_data"></a><section>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Phone numbers<button class="btn btn-default btn-xs pull-right" type="submit" id="addPhoneNumber" name="addPhoneNumber" value="addPhoneNumber"><i class="icon-plus"></i> Add</button></h3>
							</div>
							<div class="panel-body">
								<c:forEach items="${contact.phoneNumbers}" var="phoneNumber" varStatus="phoneNumberStatus">
									<form:hidden path="phoneNumbers[${phoneNumberStatus.index}].id" />
									<form:hidden path="phoneNumbers[${phoneNumberStatus.index}].createdAt" />
									<form:hidden path="phoneNumbers[${phoneNumberStatus.index}].createdBy" />
									<form:hidden path="phoneNumbers[${phoneNumberStatus.index}].version" />
									<div class="form-group">
										<label class="col-xs-3 col-sm-3 col-md-3 col-lg-2 control-label" for="phoneNumbers[${phoneNumberStatus.index}].number">Number</label>
										<div class="col-xs-7 col-sm-6 col-md-6 col-lg-4">
											<form:input path="phoneNumbers[${phoneNumberStatus.index}].number" type="tel" cssClass="form-control" />
										</div>
                                        <div class="col-xs-2 col-sm-2 col-md-2 col-lg-1">
                                            <c:if test="${phoneNumberStatus.index > 0}">
												<button class="btn btn-sm btn-danger" type="submit" id="deletePhoneNumber" name="deletePhoneNumber" value="${phoneNumberStatus.index}"><i class="glyphicon glyphicon-minus-sign"></i></button>
											</c:if>
                                        </div>
									</div>
								</c:forEach>
							</div>
						</div>
                        </section>
						<!-- emails -->
                        <a id="email_data"></a><section>
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">Email addresses<button class="btn btn-default btn-xs pull-right" type="submit" id="addEmail" name="addEmail" value="addEmail"><i class="icon-plus"></i> Add</button></h3>
							</div>
							<div class="panel-body">
								<c:forEach items="${contact.emailAddresses}" var="emailAddress" varStatus="emailAddressStatus">
									<form:hidden path="emailAddresses[${emailAddressStatus.index}].id" />
									<form:hidden path="emailAddresses[${emailAddressStatus.index}].createdAt" />
									<form:hidden path="emailAddresses[${emailAddressStatus.index}].createdBy" />
									<form:hidden path="emailAddresses[${emailAddressStatus.index}].version" />
									<div class="form-group">
										<label class="col-xs-3 col-sm-3 col-md-3 col-lg-2 control-label" for="pemailAddresses[${emailAddressStatus.index}].email">Email</label>
										<div class="col-xs-7 col-sm-6 col-md-6 col-lg-4">
											<form:input path="emailAddresses[${emailAddressStatus.index}].email" type="email" cssClass="form-control" />
										</div>
                                         <div class="col-xs-2 col-sm-2 col-md-2 col-lg-1">
                                            <c:if test="${emailAddressStatus.index > 0}">
												<button class="btn-sm btn-danger" type="submit" id="deleteEmail" name="deleteEmail" value="${emailAddressStatus.index}"> <i class="glyphicon glyphicon-minus-sign"></i></button>
											</c:if>
                                        </div>
									</div>
								</c:forEach>
							</div>
						</div>
                        </section>
						<hr />
						<button type="submit" id="saveButton" name="save" value="Save" class="btn btn-primary">Save</button>
						<button type="submit" id="deleteButton" name="delete" value="Delete" class="btn btn-danger">Delete</button>
					</fieldset>
				</form:form>
			</div>	
		</div>
	</div>
	<!--/col-->
</div>
<!--/row-->