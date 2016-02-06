package CRMPages;

import java.util.List;

import CRM.CRMApplication;
import CRM.ControlPanel;
import CRM.actionsPanel;
import dao.DaoImpl;
import data.Role;
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
import view.RoleViewer;

public class RolePage implements actionsPanel {
	private ObservableList<RoleViewer> viewers = FXCollections.observableArrayList();
	private TableView<RoleViewer> table = new TableView<RoleViewer>();
	private DaoImpl<Role> servisOf = null;
	private Group group = new Group();
	
	public Group getGroup() {
		return group;
	}

	public RolePage (DaoImpl<Role> service) {
		this.servisOf = service;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		List<Role> list = servisOf.findAll();
		
		for (Role pr: list) viewers.add(new RoleViewer(pr)); 
		
		Label point = new Label("");
		
		Label label = new Label("Catalog Roles");
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
		
		TableColumn<RoleViewer, Integer> firstNameCol = new TableColumn<RoleViewer, Integer>("id");
		TableColumn<RoleViewer, String> secondNameCol = new TableColumn<RoleViewer, String>("name");
		TableColumn<RoleViewer, String> thirdNameCol = new TableColumn<RoleViewer, String>("comment");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<RoleViewer, Integer>("id"));
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<RoleViewer, String>("name"));
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<RoleViewer, String>("comment"));
		  	firstNameCol.setMinWidth(50);
			secondNameCol.setMinWidth(150);
			thirdNameCol.setMinWidth(150);
		  
		table.setItems(viewers);
		table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol);	
		
		 secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 	secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<RoleViewer, String>>() {
			   @Override
			   public void handle(CellEditEvent<RoleViewer, String> value) {
			    int activeRow = value.getTablePosition().getRow();
			    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
			    	Role posUp = servisOf.findAll().get(activeRow);
			    	posUp.setName(value.getNewValue());
			    	servisOf.update(posUp);
			   }
		  });
		  	
		  thirdNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		  thirdNameCol.setOnEditCommit(new EventHandler<CellEditEvent<RoleViewer, String>>() {	
			@Override
			public void handle(CellEditEvent<RoleViewer, String> value) {
				int activeRow = value.getTablePosition().getRow();
					value.getTableView().getItems().get(activeRow).setComment(value.getNewValue());
					Role posUp = servisOf.findAll().get(activeRow);
			    	posUp.setComment(value.getNewValue());
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
			  	name1.setPromptText("new role");
				TextField name2 = new TextField();
				name2.setMinHeight(40);
				name2.setFont(new Font(15));
				name2.setPromptText("new comment");
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
								message.setText("NO COMMENT");
								message.setVisible(true);
							}else {
									viewers.add(new RoleViewer(viewers.size()+1, name1.getText(), name2.getText()));
									Role product = new Role(name1.getText(), name2.getText());
									servisOf.create(product);
									message.setText("Position added successfully" + "\n" + name1.getText() + " - " + name2.getText());
									message.setVisible(true);
									name1.clear();
									name2.clear();
							}
						} else {
							message.setText("NO ROLE");
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
