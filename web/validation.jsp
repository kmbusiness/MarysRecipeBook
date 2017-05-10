<%-- 
    Document   : validation
    Created on : Feb 21, 2017, 7:44:05 PM
    Author     : johnkmnguyen
--%>

<%@ page import ="java.sql.*" %>
<%
    try {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/yeet?zeroDateTimeBehavior=convertToNull", "root", "1234");
        PreparedStatement pst = conn.prepareStatement("Select userName,password from owner where userName = ? and password = ?");
        pst.setString(1, userName);
        pst.setString(2, password);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            response.sendRedirect("faces/home.xhtml");
        } else {
          //String redirectURL = "http://localhost:8080/RecipeCollection/";
          //response.sendRedirect(redirectURL);
          //out.println("alert('User or password incorrect');");
          %>
                <script> alert("Wrong Username or password. Please try again.");
                 //window.location = 'http://localhost:8080/RecipeCollection/faces/index.xhtml';
                 window.location = 'faces/index.xhtml';
                </script>
          <%
        }
    } catch (Exception e) {
//        String redirectURL = "http://localhost:8080/RecipeCollection/";
//        response.sendRedirect(redirectURL);
        out.println("WTF");
    }
%> 