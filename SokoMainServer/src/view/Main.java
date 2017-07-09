package view;
	
import controller.MainController;
import controller.SokobanController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.MyModel;
import model.SSModel;
import model.policy.Iinterpeter;
import model.policy.IinterpeterCreator;
import model.policy.InterpeterXML;
import model.policy.Policy;
import model.policy.SokobanPolicy;
import model.tasks.TaskManager;
import server.SessionBuilder;
import server.SessionManager;
import server.SokoSessionBuilder;
import server.SokoSessionManager;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			FXMLLoader fxml=new FXMLLoader();			
			BorderPane root = fxml.load(getClass().getResource("adminView.fxml").openStream());
			//soko stuff
		
			SokoViewModel vm = new SokoViewModel();
			
			 AdminController gui = fxml.getController();
			 vm.addView(gui);
			 gui.addObserver(vm);
			 SessionBuilder sb = new SokoSessionBuilder(System.in, System.out);
			 SokoSessionManager sm = new SokoSessionManager(sb);
			 TaskManager tm = new TaskManager();
			SSModel model = new SSModel(sm, tm);
			MainController controller = new MainController(model, vm);
			vm.addObserver(controller);
			model.addObserver(controller);
			sm.addObserver(controller);
			tm.addObserver(controller);
			gui.setViewModel(vm);
			controller.start();
			model.initializeModelComponents();
			Scene scene = new Scene(root,700,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
