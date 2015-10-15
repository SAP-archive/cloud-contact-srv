<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title><tiles:getAsString name="title" /></title>
	
	<tiles:insertAttribute name="meta" />
	<tiles:insertAttribute name="stylesheets" />
	<tiles:insertAttribute name="js" />
	
</head>

<body>
<div class="container">
	<tiles:insertAttribute name="navbar"/>
	<div class="content" id="pjax-container">
		<tiles:insertAttribute name="content" />
	</div>
    <tiles:insertAttribute name="footer" />

</div><!--/container-->
</body>
</html>
