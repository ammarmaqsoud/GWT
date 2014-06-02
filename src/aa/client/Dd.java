package aa.client;

import aa.shared.FieldVerifier;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Dd implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
    final Button sendButton = new Button("Send");
    final TextBox nameField = new TextBox();
    nameField.setText("GWT User");
    final Label errorLabel = new Label();

    final TextBox a_Field = new TextBox();
    final TextBox b_Field = new TextBox();
    final TextBox c_Field = new TextBox();
    
    a_Field.setText("");
    b_Field.setText("");
    c_Field.setText("");
    
 
    
    // We can add style names to widgets
    sendButton.addStyleName("sendButton");

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel rootPanel = RootPanel.get("nameFieldContainer");
    rootPanel.add(nameField);
 
    rootPanel.add(a_Field,606,210);
    rootPanel.add(b_Field,606,250);
    rootPanel.add(c_Field,606,290);
    
    
    
    RootPanel.get("sendButtonContainer").add(sendButton, 606, 330);
    RootPanel.get("errorLabelContainer").add(errorLabel,573,375);

    // Focus the cursor on the name field when the app loads
    a_Field.setFocus(true);
 
    
    Label lblA = new Label("What's (a) value?");
    rootPanel.add(lblA,  480, 218);
    lblA.setSize("120px", "18px");
    
    Label lblB = new Label("What's (b) value?");
    rootPanel.add(lblB, 480, 258);
    lblB.setSize("120px", "18px");
    
    Label lblC = new Label("What's (c) value?	");
    rootPanel.add(lblC, 480, 298);
    lblC.setSize("120px", "18px");
    a_Field.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending a  , b  ,c   to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements ClickHandler, KeyUpHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();
        
        String txt_a_Field = a_Field.getText();
        String txt_b_Field = b_Field.getText();
        String txt_c_Field = c_Field.getText();
        
        double a_parm =Double.parseDouble(a_Field.getText());
        double b_parm = Double.parseDouble(b_Field.getText());
        double c_parm = Double.parseDouble(c_Field.getText());
        
       
        
        if (FieldVerifier.isEmptyField(txt_a_Field) || FieldVerifier.isEmptyField(txt_b_Field) ||FieldVerifier.isEmptyField(txt_c_Field)) {
            errorLabel.setText("Please enter the all parameters");
            return;
          }
        
        
        if (!FieldVerifier.isValidName(textToServer)) {
          errorLabel.setText("Please enter at least four characters");
          return;
        }
        
        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        
     /*   textToServerLabel.setText(textToServer);
        textToServerLabel.setText(textToServer);
        textToServerLabel.setText(textToServer);*/
        
        serverResponseLabel.setText("");
        greetingService.greetServer(textToServer,a_parm,b_parm,c_parm, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            // Show the RPC error message to the user
            dialogBox.setText("Squared Equation Solving - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String result) {
            dialogBox.setText("Squared Equation Solving");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    sendButton.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
  }
}
