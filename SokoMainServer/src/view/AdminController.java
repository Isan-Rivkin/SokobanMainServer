package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import server.Protocol;

public class AdminController extends Observable implements Initializable
{
	// non javafax 
	// config params
	private String serverPort=Protocol.serverPort+"";
	private String maxConnectios="50";
	private SokoViewModel viewModel;
	// javafx 
	@FXML
	private ListView<String> listView;
	@FXML
	private Button startServerButton,stopServerButton,configButton,killSessionButton,connectionDetailsButton;
	@FXML
	private TextArea activityLog;
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		showLoginDialog();
		ArrayList<String> test = new ArrayList<String>();
		//test.add("ronen");
		//updateListView(test);
	}
	public void showConfigDialog()
	{
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Server Configuration");
		//dialog.setHeaderText("Look, a Custom Login Dialog");

		// Set the icon (must be included in the project).
		try 
		{
			dialog.setGraphic(new ImageView(new Image(new FileInputStream("./resources/config.png"))));
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		// Set the button types.
		ButtonType saveConfigButton = new ButtonType("Save Settings", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveConfigButton, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField port = new TextField();
		port.setPromptText(serverPort);
		TextField usersNum = new TextField();
		usersNum.setPromptText(maxConnectios);

		grid.add(new Label("Port Number:"), 0, 0);
		grid.add(port, 1, 0);
		grid.add(new Label("Max Connections Allowed:"), 0, 1);
		grid.add(usersNum, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(saveConfigButton);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		port.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> port.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == saveConfigButton) {
		        return new Pair<>(port.getText(), usersNum.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			this.maxConnectios=usernamePassword.getValue();
			this.serverPort=usernamePassword.getKey();
		    //System.out.println("Port=" + usernamePassword.getKey() + ", MaxConnections=" + usernamePassword.getValue());
		});
	}
	public void errorDialog(String header, String content)
	{
		Platform.runLater(()->
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error!");
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
		});
	}
	public void showLoginDialog()
	{
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Hint (soko 123 will do :))");

		// Set the icon (must be included in the project).
		//dialog.setGraphic(new ImageView(this.getClass().getResource("./resources/login.png").toString()));
		try 
		{
			dialog.setGraphic(new ImageView(new Image(new FileInputStream("./resources/login.png"))));
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("soko");
		PasswordField password = new PasswordField();
		password.setPromptText("123");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
		    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}
	public void killTaskDialog()
	{
		TextInputDialog dialog = new TextInputDialog("Session ID");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Please enter a session id to kill.");
		dialog.setContentText("Session ID: ");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		
		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(name -> 
		{
			updateObservers(Protocol.closeAsession,name,Protocol.failure_task);
		});
	}
	public void onStartServer()
	{
		boolean okToRun=false;
		try
		{
			Integer.parseInt(serverPort);
			Integer.parseInt(maxConnectios);
			okToRun = true;
		}
		catch(NumberFormatException e )
		{
			okToRun=false;
			e.printStackTrace();
		}
		if(okToRun)
		{
			updateObservers(Protocol.startServer,serverPort,maxConnectios);
		}
		else
		{
			errorDialog("Server Configuration Error", "One or more values inserted are not real integers.");
		}
		
	}
	public void onStopServer()
	{
		updateObservers(Protocol.stopServer);
	}
	public void onConfig()
	{
		showConfigDialog();
	}
	public void onKillSession()
	{
		killTaskDialog();
	}
	public void onConnectionDetails()
	{
		
	}
	private void updateObservers(String ...strings)
	{
		List<String> params = new LinkedList<String>();
		int k=0;
		for(String st : strings)
		{
			params.add(st);
			k++;
		}
		setChanged();
		notifyObservers(params);
	}
	public void setViewModel(SokoViewModel vm)
	{
		this.viewModel = vm;;
		activityLog.textProperty().bind(viewModel.log);
	}
	public void updateListView(ArrayList<String> currentTasksAndSessionsList)
	{
		Platform.runLater(()->
		{
			ObservableList<String> items =FXCollections.observableArrayList (currentTasksAndSessionsList);
			listView.setItems(items);
		});
	}

	
}
