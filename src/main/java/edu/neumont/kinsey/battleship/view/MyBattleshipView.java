package edu.neumont.kinsey.battleship.view;

import java.util.HashMap;
import java.util.Map;

import edu.neumont.kinsey.battleshipapi.controller.BattleshipController;
import edu.neumont.kinsey.battleshipapi.model.Board;
import edu.neumont.kinsey.battleshipapi.view.BattleshipView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MyBattleshipView implements BattleshipView {

	private Stage stage;

	private BattleshipController controller;

	@FXML
	private HBox namePrompt;

	@FXML
	private Label playerName;

	@FXML
	private HBox playerBox;
	@FXML
	private Label playerLabel;
	@FXML
	private TextField playerText;
	@FXML
	private Button playerButton;
	
	@FXML
	private GridPane boardGridPaneLeft = new GridPane();
	@FXML
	private GridPane boardGridPaneRight = new GridPane();
	
	private Map<String, Button> boardButtonsLeft = new HashMap<>();
	
	private Map<String, Button> boardButtonsRight = new HashMap<>();

	@Override
	public void registerController(BattleshipController battleshipController) {
		this.controller = battleshipController;
	}

	@Override
	public void init() {
		this.stage.setTitle("Battleship");
		this.stage.setMinWidth(1920);
		this.stage.setMinHeight(1080);
		this.stage.setMaximized(true);
		this.stage.show();
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void onPlayerClicked(ActionEvent e) {
		if (playerText.getText().trim().matches("^[a-zA-Z]+$")) {
			controller.setPlayer(0, playerText.getText());
			updatePlayerTurnLabel(playerText.getText());
			playerLabel.setText("Player 2's name: ");
			playerText.clear();
			playerButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (playerText.getText().trim().matches("^[a-zA-Z]+$")) {
						controller.setPlayer(1, playerText.getText());
						playerBox.setVisible(false);
						drawButtons();
						updateButtonEventHandlers();
					} else {
						new Alert(AlertType.ERROR, "Player name must be only letters.", ButtonType.OK).show();
					}
				}
			});
		} else {
			new Alert(AlertType.ERROR, "Player name must be only letters.", ButtonType.OK).show();
		}
	}

	public void onRestartAction(ActionEvent e) {

	}

	public void onSaveAction(ActionEvent e) {

	}

	public void onLoadAction(ActionEvent e) {

	}

	public void onExitAction(ActionEvent e) {

	}

	public void onAboutAction(ActionEvent e) {

	}
	
	private EventHandler<ActionEvent> attackButtonEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Button button = (Button) event.getSource();
			String buttonId = button.getId();
			String[] pieces = buttonId.split("_");
			controller.attack(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]));
		}
	};
	
	private EventHandler<ActionEvent> placeShipButtonEvent = new EventHandler<ActionEvent>() {
		public void handle(ActionEvent event) {
			Button button = (Button) event.getSource();
			String buttonId = button.getId();
			String[] pieces = buttonId.split("_");
			controller.placeShip(Integer.parseInt(pieces[0]), Integer.parseInt(pieces[1]));
		}
	};

	private void drawButtons() {
		for (int c = 0; c < Board.MAX_COLUMNS; c++) {
			for (int r = 0; r < Board.MAX_ROWS; r++) {
				Button button1 = new Button();
				Button button2 = new Button();
				button1.setMinWidth(50);
				button1.setMinHeight(50);
				button1.setId("" + r + "_" + c);
				button1.setText("W");
				button2.setMinWidth(50);
				button2.setMinHeight(50);
				button2.setId("" + r + "_" + c);
				button2.setText("W");
				button2.setDisable(true);
				this.boardButtonsLeft.put(button1.getId(), button1);
				this.boardButtonsRight.put(button2.getId(), button2);
				this.boardGridPaneLeft.add(button1, c, r);
				this.boardGridPaneRight.add(button2, c, r);
			}
		}
	}
	
	private void updateButtonEventHandlers() {
		for (Button button : boardButtonsLeft.values()) {
			if (controller.placingShips == true) {
				button.setOnAction(placeShipButtonEvent);				
			} else {
				button.setOnAction(attackButtonEvent);
			}
		}
		
		for (Button button : boardButtonsRight.values()) {
			if (controller.placingShips == true) {
				button.setOnAction(placeShipButtonEvent);				
			} else {
				button.setOnAction(attackButtonEvent);
			}
		}
	}
	
	public void updateBoards(Board ownBoard, Board hiddenBoard) {
		for (int c = 0; c < Board.MAX_COLUMNS; c++) {
			for (int r = 0; r < Board.MAX_ROWS; r++) {
				Button button1 = boardButtonsLeft.get("" + r + "_" + c);
				Button button2 = boardButtonsRight.get("" + r + "_" + c);
				button1.setText(ownBoard.getTileValue(c, r).name());
				button2.setText(hiddenBoard.getTileValue(c, r).name());
			}
		}
	}
	
	private void updatePlayerTurnLabel(String name) {
		playerName.setText(name);
	}
}
