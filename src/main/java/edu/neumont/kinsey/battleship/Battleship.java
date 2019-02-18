package edu.neumont.kinsey.battleship;

import java.net.URL;

import edu.neumont.kinsey.battleship.view.MyBattleshipView;
import edu.neumont.kinsey.battleshipapi.controller.BattleshipController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Battleship extends Application {

	public static void main(String[] args) {
		Application.launch(Battleship.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL location = this.getClass().getClassLoader().getResource("BattleshipView.fxml");
		FXMLLoader loader = new FXMLLoader(location);
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		MyBattleshipView viewController = loader.getController();
		viewController.setStage(stage);
		BattleshipController controller = new BattleshipController(viewController);
		controller.run();
	}
}
