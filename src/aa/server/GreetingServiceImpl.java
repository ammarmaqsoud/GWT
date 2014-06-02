package aa.server;

import aa.client.GreetingService;
import aa.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input ,double a_parm,double b_parm,double c_parm) throws IllegalArgumentException {
    // Verify that the input is valid. 
    if (!FieldVerifier.isValidName(input)) {
      // If the input is not valid, throw an IllegalArgumentException back to
      // the client.
      throw new IllegalArgumentException(
          "Name must be at least 4 characters long");
    }
  
    
  //  String serverInfo = getServletContext().getServerInfo();
    String userAgent = getThreadLocalRequest().getHeader("User-Agent");

    // Escape data from the client to avoid cross-site script vulnerabilities.
    input = escapeHtml(input);
    userAgent = escapeHtml(userAgent);
    double x1,x2;
  
    double delta=(b_parm*b_parm)-(4*a_parm*c_parm);
    if (delta>0)
    {
    	  x1=((-1*b_parm)+(Math.sqrt(delta)))/(2*a_parm); 
    	  x2=((-1*b_parm)-(Math.sqrt(delta)))/(2*a_parm);   	
    
    	  
    	  return "x1= "+x1+" , x2= "+x2;
        }
    else  if (delta==0)		
    {
    	x1=((-1*b_parm))/(2*a_parm); //   -b/2a
     
        return "x1= "+x1;
    }
    else
    {
    	
    return "No Solutions for the equation";
    }
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
