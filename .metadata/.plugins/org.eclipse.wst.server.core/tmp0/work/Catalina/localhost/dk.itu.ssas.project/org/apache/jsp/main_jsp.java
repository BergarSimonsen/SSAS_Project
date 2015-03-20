package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import dk.itu.ssas.project.Utils;
import java.sql.*;
import dk.itu.ssas.project.DB;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write('\n');

	String user = "";
	String username = "";
	if(Utils.isSessionValid(session)) {
		user = session.getAttribute("user").toString();
		username = session.getAttribute("username").toString();
	

      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("<title>SSAS Photo Sharing Project</title>\n");
      out.write("<style>\n");
      out.write("* {\n");
      out.write("\tfont-family: helvetica-neue, helvetica, arial;\n");
      out.write("\tfont-size: 10pt;\n");
      out.write("}\n");
      out.write("ul {\n");
      out.write("    list-style-type: none;\n");
      out.write("}\n");
      out.write("</style>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<p>Hello, ");
      out.print( username );
      out.write("!\n");
      out.write("<p><form method=\"post\" enctype=\"multipart/form-data\" action=\"Uploader\">\n");
      out.write("\tAdd a picture: \n");
      out.write("\t<input type=\"file\" name=\"pic\" accept=\"jpeg\">\n");
      out.write("\t<input type=\"submit\" value=\"Upload!\">\n");
      out.write("</form>\n");
      out.write("<a href=\"logout.jsp\">Log out</a>\n");
      out.write("<ul>\n");


	final String SELECT_IMAGE = "SELECT DISTINCT image_id FROM perms WHERE perms.user_id = ?";
	final String SELECT_OTHER = "SELECT username FROM users INNER JOIN images WHERE users.id = images.owner AND  images.id = ?";
	final String SELECT_VIEWERS = "SELECT users.username FROM users INNER JOIN perms WHERE users.id = perms.user_id AND perms.image_id = ?";
	final String SELECT_COMMENTS = "SELECT comments.comment, users.username FROM comments INNER JOIN users WHERE users.id = comments.user_id AND comments.image_id = ?";
	
// 	ResultSet comments = st2.executeQuery(
//         	"SELECT comments.comment, users.username " + 
//             "FROM comments INNER JOIN users " + 
//             "WHERE users.id = comments.user_id " +
//             "AND comments.image_id = " + image_id
//         );	

	Connection con = DB.getConnection();
	PreparedStatement st = con.prepareStatement(SELECT_IMAGE);
	PreparedStatement st2 = con.prepareStatement(SELECT_OTHER);
	st.setString(1, user);
	ResultSet image_ids = st.executeQuery();
//     Statement st = con.createStatement();
//     Statement st2 = con.createStatement();
//     ResultSet image_ids = st.executeQuery("SELECT DISTINCT image_id FROM perms WHERE perms.user_id = " + user);
    while (image_ids.next()) {
    	  String image_id = image_ids.getString(1);
    	  st2.setString(1, image_id);
    	  ResultSet other = st2.executeQuery();
//     	  ResultSet other = st2.executeQuery(
//               "SELECT username " +
//               "FROM users INNER JOIN images " + 
//               "WHERE users.id = images.owner " +
//               "AND   images.id = " + image_id
//     	  );
    	  other.next();
    	  String other_name = other.getString(1);
  
      out.write("\n");
      out.write("\t<li> Posted by ");
      out.print( other_name);
      out.write(":<br><br>\n");
      out.write("\t   <img src=\"Downloader?image_id=");
      out.print( image_id );
      out.write("\" width=\"60%\"><br>\n");
      out.write("\t    Shared with: \n");
      
		st2 = con.prepareStatement(SELECT_VIEWERS);
	   	st2.setString(1, image_id);
	   	ResultSet viewers = st2.executeQuery();
// 		ResultSet viewers = st2.executeQuery(
// 			"SELECT users.username " +
// 		    "FROM users INNER JOIN perms " +
// 			"WHERE users.id = perms.user_id " +
// 		    "AND perms.image_id = " + image_id
// 		);
		while (viewers.next()) {
			String sharee = viewers.getString(1);
			if (sharee.equals (username)) {
				continue;
			}

      out.write('\n');
      out.write('	');
      out.write('	');
      out.print( sharee );
      out.write('\n');
 
		}			

      out.write("\n");
      out.write("\t\t<br><br>\n");

		st2 = con.prepareStatement(SELECT_COMMENTS);
		st2.setString(1, image_id);
		ResultSet comments = st2.executeQuery();
		
//         ResultSet comments = st2.executeQuery(
//         	"SELECT comments.comment, users.username " + 
//             "FROM comments INNER JOIN users " + 
//             "WHERE users.id = comments.user_id " +
//             "AND comments.image_id = " + image_id
//         );	
        while (comments.next()) {

      out.write("\n");
      out.write("\t\tFrom ");
      out.print( comments.getString(2) );
      out.write(':');
      out.write(' ');
      out.write('"');
      out.print( comments.getString(1) );
      out.write("\"<br>\n");

        }
 
      out.write("\n");
      out.write(" <br>\n");
      out.write("   <form action=\"Comment\" method=\"post\">\n");
      out.write("        \t<input type='text' name='comment'>\n");
      out.write("            <input type=\"submit\" value=\"Post comment!\">\n");
      out.write("            <input type=\"hidden\" name=\"user_id\" value='");
      out.print( user );
      out.write("'>\n");
      out.write("            <input type=\"hidden\" name=\"image_id\" value='");
      out.print( image_id );
      out.write("'>\n");
      out.write("   \t\t </form>\t\n");
      out.write("   \t\t<br>\n");
      out.write("   \t\t<form action=\"Invite\" method=\"post\">\n");
      out.write("   \t\t\t<input type='text' name='other'>\n");
      out.write("            <input type=\"submit\" value=\"Share image!\">\n");
      out.write("            <input type=\"hidden\" name=\"image_id\" value=\"");
      out.print( image_id );
      out.write("\">\n");
      out.write("   \t\t</form>\n");
      out.write("   \t\t<br>\n");
      out.write("\t </li>       \n");

	} } else {
		response.sendRedirect("index.jsp?login_failure=1");
	}

      out.write("     \n");
      out.write("</ul>   \n");
      out.write("\n");
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
