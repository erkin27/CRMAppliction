package CRM;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class ControlPanel{
	
	private HBox btns = new HBox();
	private String [] commands = new String[]{"HELP", "ADD", "DELETE", "IMPORT", "EXPORT", "EXIT"};
	private Button[] buttons = new Button[commands.length];
	actionsPanel actions;

	public ControlPanel(actionsPanel actions) {
		super();
		// TODO Auto-generated constructor stub
		this.actions = actions;
		for (int i = 0; i<buttons.length; i++) {
			buttons[i] = new Button("F" +Integer.toString(i+1) + "\n" + commands[i]);
			buttons[i].setTooltip(new Tooltip(commands[i]));
			buttons[i].setStyle("-fx-background-color:#C2CCCF");
			buttons[i].setStyle("-fx-text-fill: #8C3535");
			buttons[i].setMaxSize(70, 60);
			buttons[i].setMinSize(70, 60);
		}

		init();
 	}

	public void init () {
		for (Button btn: buttons) btns.getChildren().add(btn);
		buttons[0].setOnAction(event1 -> actions.help());
		buttons[1].setOnAction(event1 -> actions.add());
		buttons[2].setOnAction(event1 -> actions.delete());
		buttons[5].setOnAction(event1 -> actions.exit());
	}


	public HBox getBtns() {
		return btns;
	}

}
