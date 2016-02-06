package CRMPages;

import java.util.List;

import CRM.ControlPanel;
import CRM.actionsPanel;
import dao.DaoImpl;
import data.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import view.UserViewer;

public class UserPage implements actionsPanel{
	private ObservableList<UserViewer> viewers = FXCollections.observableArrayList();
	private TableView<UserViewer> table = new TableView<UserViewer>();
	private DaoImpl<User> servisOf = null;
	private Group group = new Group();
	
	public Group getGroup() {
		return group;
	}

	public UserPage (DaoImpl<User> service) {
		this.servisOf = service;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		List<User> list = servisOf.findAll();
		
		for (User pr: list) viewers.add(new UserViewer(pr)); 
		
		Label point = new Label("");
		
		Label label = new Label("Catalog Users");
		label.setLayoutX(160);
		label.setLayoutY(20);
		label.setStyle("-fx-font-size:20px");
		
		initTable();

		table.setStyle("-fx-font-size:16px");
		
		HBox controlPanel = new ControlPanel(this).getBtns(); //Добавление ControlPanel

		VBox vbox = new VBox();
		vbox.getChildren().setAll(table, controlPanel);
		vbox.setLayoutX(120);
		vbox.setPadding(new Insets(60, 20, 20, 20));
		
		group.getChildren().add(point);
		group.getChildren().add(label);
		group.getChildren().add(vbox);
	}

	private void initTable() {
		// TODO Auto-generated method stub
		table.setEditable(true);
		
		TableColumn<UserViewer, Integer> firstNameCol = new TableColumn<UserViewer, Integer>("id");
		TableColumn<UserViewer, String> secondNameCol = new TableColumn<UserViewer, String>("name");
		TableColumn<UserViewer, String> thirdNameCol = new TableColumn<UserViewer, String>("login");
		TableColumn<UserViewer, String> fourthNameCol = new TableColumn<UserViewer, String>("pass");
		TableColumn<UserViewer, String> fifthNameCol = new TableColumn<UserViewer, String>("employee");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<UserViewer, Integer>("id"));
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<UserViewer, String>("name"));
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<UserViewer, String>("login"));
		  fourthNameCol.setCellValueFactory(new PropertyValueFactory<UserViewer, String>("pass"));
		 fifthNameCol.setCellValueFactory(new PropertyValueFactory<UserViewer, String>("employee"));
		  	firstNameCol.setMinWidth(50);
			secondNameCol.setMinWidth(150);
			thirdNameCol.setMinWidth(50);
			fourthNameCol.setMinWidth(50);
		  
		table.setItems(viewers);
		table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol, fourthNameCol, fifthNameCol);	
		
		 secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 	secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<UserViewer, String>>() {
			   @Override
			   public void handle(CellEditEvent<UserViewer, String> value) {
			    int activeRow = value.getTablePosition().getRow();
			    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
			    	User posUp = servisOf.findAll().get(activeRow);
			    	posUp.setName(value.getNewValue());
			    	servisOf.update(posUp);
			   }
		  });
		  	
		  thirdNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		  thirdNameCol.setOnEditCommit(new EventHandler<CellEditEvent<UserViewer, String>>() {	
			@Override
			public void handle(CellEditEvent<UserViewer, String> value) {
				int activeRow = value.getTablePosition().getRow();
					value.getTableView().getItems().get(activeRow).setLogin(value.getNewValue());
					User posUp = servisOf.findAll().get(activeRow);
			    	posUp.setLogin(value.getNewValue());
			    	servisOf.update(posUp);
			}
		  }); 
		  
		  fourthNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		  fourthNameCol.setOnEditCommit(new EventHandler<CellEditEvent<UserViewer,String>>() {
			@Override
			public void handle(CellEditEvent<UserViewer, String> value) {
				// TODO Auto-generated method stub
				int activeRow = value.getTablePosition().getRow();
				value.getTableView().getItems().get(activeRow).setPass(value.getNewValue());
				User posUp = servisOf.findAll().get(activeRow);
		    	posUp.setPass(value.getNewValue());
		    	servisOf.update(posUp);
			}
		});
		  
	}

	
	//=====================actionsPanel=======================================================
	@Override
	public void help() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add() {
		// TODO Auto-generated method stub
		addControl myAdd = new addControl();
		try {
			myAdd.start(new Stage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		int selectedInd = table.getSelectionModel().getSelectedIndex(); 
		if (selectedInd >= 0) {
			table.getItems().remove(selectedInd);
			servisOf.delete(servisOf.findAll().get(selectedInd));
		} else {
			//Nothing selected
		}
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		CRM.CRMApplication.primaryStage.close();
	}
	
	
//	=========addControl ================================================
	public class addControl extends Application {
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			primaryStage.setScene(myScene());
			primaryStage.show();
		}

		private Scene myScene() {
			// TODO Auto-generated method stub
			Group gr = new Group();
			Text message = new Text();
			 message.setLayoutX(30);
			 message.setLayoutY(100);
			 message.setVisible(false);
			 message.setFont(new Font(15));
			 HBox hb = new HBox();
			 hb.setPadding(new Insets(20));
			 hb.setSpacing(10);
			  	TextField name1 = new TextField();
			  	name1.setMinHeight(40);
			  	name1.setFont(new Font(15));
			  	name1.setPromptText("new name of user");
				TextField name2 = new TextField();
				name2.setMinHeight(40);
				name2.setFont(new Font(15));
				name2.setPromptText("new login of user");
				Button add = new Button("Add");
				add.setFont(new Font(15));
				add.setMinHeight(40);
				add.setMinWidth(50);
				add.setStyle("-fx-text-fill: #74B352");
		
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if (!name1.getText().isEmpty()) {
							if (name2.getText().isEmpty()) {
								message.setText("NO LOGIN");
								message.setVisible(true);
							}else {
									viewers.add(new UserViewer(viewers.size()+1, name1.getText(), name2.getText(), ""));
									User product = new User(name1.getText(), name2.getText(), "", null);
									servisOf.create(product);
									message.setText("Position added successfully" + "\n" + name1.getText() + " - " + name2.getText());
									message.setVisible(true);
									name1.clear();
									name2.clear();
							}
						} else {
							message.setText("NO NAME");
							message.setVisible(true);
						}
					}
				});
				
			hb.getChildren().setAll(name1, name2,add);
			gr.getChildren().addAll(hb, message);	
			
			return new Scene(gr, 500, 200);
		}
	}
}
