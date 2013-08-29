<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Spring MVC PDF iText</title>
    </head>
    <body>
        <h3>PDF generation using Spring MVC and iText</h3>

        <p><a href="<c:url value="/students/html"/>">HTML View</a></p>
        <p><a href="<c:url value="/students/beanToPdfSimple"/>">Bean to PDF View - Simple</a></p>
        <p><a href="<c:url value="/students/beanToPdfRich"/>">Bean to PDF View - Rich</a></p>

    </body>
</html>
