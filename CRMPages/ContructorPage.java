package CRMPages;

import java.util.List;

import CRM.ControlPanel;
import CRM.actionsPanel;
import CRMPages.ProductPage.addControl;
import dao.DaoImpl;
import data.Contructor;
import data.Product;
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
import view.ContructorViewer;
import view.ProductViewer;

public class ContructorPage implements actionsPanel {
	private ObservableList<ContructorViewer> viewers = FXCollections.observableArrayList();
	private TableView<ContructorViewer> table = new TableView<ContructorViewer>();
	private DaoImpl<Contructor> servisOf = null;
	private Group group = new Group();
	
	public Group getGroup() {
		return group;
	}

	public ContructorPage (DaoImpl<Contructor> service) {
		this.servisOf = service;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		List<Contructor> list = servisOf.findAll();
		
		for (Contructor pr: list) viewers.add(new ContructorViewer(pr)); 
		
		Label point = new Label("");
		
		Label label = new Label("Catalog Contructors");
		label.setLayoutX(160);
		label.setLayoutY(20);
		label.setStyle("-fx-font-size:20px");
		
		initTable();

		table.setStyle("-fx-font-size:16px");
		
		HBox controlPanel = new ControlPanel(this).getBtns();

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
		
		TableColumn<ContructorViewer, Integer> firstNameCol = new TableColumn<ContructorViewer, Integer>("id");
		TableColumn<ContructorViewer, String> secondNameCol = new TableColumn<ContructorViewer, String>("name");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<ContructorViewer, Integer>("id"));
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<ContructorViewer, String>("name"));
		  	firstNameCol.setMinWidth(50);
			secondNameCol.setMinWidth(200);
		  
		table.setItems(viewers);
		table.getColumns().addAll(firstNameCol, secondNameCol);	
		
		 secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 	secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<ContructorViewer, String>>() {
			   @Override
			   public void handle(CellEditEvent<ContructorViewer, String> value) {
			    int activeRow = value.getTablePosition().getRow();
			    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
			    	Contructor posUp = servisOf.findAll().get(activeRow);
			    	posUp.setName(value.getNewValue());
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
			  	TextField nameProduct = new TextField();
			  	nameProduct.setMinHeight(40);
			  	nameProduct.setFont(new Font(15));
			  	nameProduct.setPromptText("new name of contruct");
				Button add = new Button("Add");
				add.setFont(new Font(15));
				add.setMinHeight(40);
				add.setMinWidth(50);
				add.setStyle("-fx-text-fill: #74B352");
		
				add.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if (!nameProduct.getText().isEmpty()) {
									viewers.add(new ContructorViewer(viewers.size()+1, nameProduct.getText()));
									Contructor product = new Contructor(nameProduct.getText());
									servisOf.create(product);
									message.setText("Position added successfully" + "\n" + nameProduct.getText());
									message.setVisible(true);
									nameProduct.clear();
						} else {
							message.setText("NO NAME");
							message.setVisible(true);
						}
					}
				});
				
			hb.getChildren().setAll(nameProduct, add);
			gr.getChildren().addAll(hb, message);	
			
			return new Scene(gr, 500, 200);
		}
	}
}
