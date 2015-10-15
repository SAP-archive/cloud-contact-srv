<%@tag description="Extended input tag to allow for sophisticated errors" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="labelClass" required="false" type="java.lang.String"%>
<%@attribute name="controlClass" required="false" type="java.lang.String"%>
<%@attribute name="label" required="false" type="java.lang.String"%>
<%@attribute name="required" required="false" type="java.lang.Boolean"%>
<%@attribute name="placeholder" required="false" type="java.lang.String"%>
<%@attribute name="type" required="false" type="java.lang.String"%>


<c:if test="${empty label}">
    <c:set var="label" value="${fn:toUpperCase(fn:substring(path, 0, 1))}${fn:toLowerCase(fn:substring(path, 1,fn:length(path)))}" />
</c:if>
<spring:bind path="${path}">
    <div class="form-group ${status.error ? 'has-error' : '' }">
        <label class="control-label ${labelClass}" for="${path}">${label}<c:if test="${required}"><span class="required">*</span></c:if></label>
        <div class="${empty controlClass ? '' : controlClass}">
            <form:input path="${path}" cssClass="form-control" placeholder="${placeholder}" type="${empty type ? 'text' : type}"/>
            <c:if test="${status.error}">
                <span class="help-block">${status.errorMessage}</span>
            </c:if>
        </div>
    </div>
</spring:bind>