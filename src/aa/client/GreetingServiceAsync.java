package aa.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
  void greetServer(String input,double a_parm,double b_parm,double c_parm, AsyncCallback<String> callback)
      throws IllegalArgumentException;
}
