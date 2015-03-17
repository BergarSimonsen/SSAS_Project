package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.List<java.lang.String> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.List<java.lang.String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("\t\t<title>SSAS Photo Sharing Webapp</title>\n");
      out.write("\t</head>\n");
      out.write("<body>\n");
      out.write("\t<h1>SSAS Photo Sharing Webapp</h1>\n");
      out.write("\t<h2>Existing users:</h2>\n");
      out.write("\t");
 if (request.getParameter("login_failure") != null) { 
      out.write("\n");
      out.write("\t<h3>Login failure. Try again?</h3>\n");
      out.write("\t");
 } 
      out.write("\n");
      out.write("\t<form method=\"get\" action=\"login.jsp\">\n");
      out.write("\t\tUsername: <input type=\"text\" name=\"username\"><br> \n");
      out.write("\t\tPassword: <input type=\"text\" name=\"password\"><br>\t\t\n");
      out.write("\t\t<input type=\"reset\" value=\"Reset\">\n");
      out.write("\t\t<input type=\"submit\" value=\"Login\">\t\t\n");
      out.write("\t</form>\n");
      out.write("\t<h2>New users:</h2>\n");
      out.write("\t");
 if (request.getParameter("create_failure") != null) { 
      out.write("\n");
      out.write("\t<h3>Username already taken. Try again?</h3>\n");
      out.write("\t");
 } 
      out.write("\n");
      out.write("\t<form method=\"get\" action=\"register.jsp\">\n");
      out.write("\t\tUsername: <input type=\"text\" name=\"username\" /><br> \n");
      out.write("\t\tPassword: <input type=\"password\" name=\"password\" /><br>\n");
      out.write("\t\t<input type=\"reset\" value=\"Reset\">\n");
      out.write("\t\t<input type=\"submit\" value=\"Create\">\n");
      out.write("\t</form>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
