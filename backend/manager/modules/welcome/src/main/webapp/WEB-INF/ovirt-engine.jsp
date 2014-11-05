<%@ page pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="obrand" uri="obrand" %>
<fmt:setBundle basename="languages" var="lang" />
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8" />
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="description" content="EayunOS Homepage">
    <meta name="author" content="EayunOS Team">
    <obrand:favicon />
    <title><fmt:message key="obrand.welcome.title" /></title>
    <obrand:stylesheets />
    <obrand:javascripts />
    <script src="splash.js" type="text/javascript"></script>
</head>
<body onload="pageLoaded()">
    ${requestScope['sections'].toString()}

    <!-- Footer -->
    <div class="footer">
      <div class="container-footer">
        <div class="row">

          <div class="col-sm-9 logo-descr-container">
            <div class="media">
              <a class="pull-left media-logo" href="{obrand.common.vendor_url}">
                <img src="theme-resource/eayun-logo.png" alt="Eayun Logo"></img>
              </a>
              <div class="media-body hidden-xs product-description">
                <p class="description">开放式虛拟化管理者</p>
                <p class="description">open virtualization manager</p>
              </div>
            </div>
          </div>

          <div class="col-sm-3 language-menu-container">
            <div class="btn-group dropup">
              <select class="gwt-ListBox obrand_locale_list_box" onchange="localeSelected(this)" id="localeBox">
                <c:forEach items="${requestScope['localeKeys']}" var="localeKey">
                  <c:choose>
                    <c:when test="${requestScope['locale'].toString() == localeKey}">
                      <c:set var="selectedLocale" value="${localeKey}"/>
                      <option value="${localeKey}" selected="selected"><fmt:message key="${localeKey}" bundle="${lang}"/></option>
                    </c:when>
                    <c:otherwise>
                      <option value="${localeKey}"><fmt:message key="${localeKey}" bundle="${lang}"/></option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>

</body>
</html>
