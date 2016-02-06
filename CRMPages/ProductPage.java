package CRMPages;


import java.util.List;

import javax.crypto.CipherInputStream;

import CRM.CRMApplication;
import CRM.ControlPanel;
import CRM.actionsPanel;
import GI.TabPanelJFX.MyAlert;
import dao.DaoImpl;
import data.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import view.ProductViewer;

public class ProductPage implements actionsPanel{
	private ObservableList<ProductViewer> viewers = FXCollections.observableArrayList();
	private TableView<ProductViewer> table = new TableView<ProductViewer>();
	private DaoImpl<Product> servisOf = null;
	private Group group = new Group();
	
	public Group getGroup() {
		return group;
	}

	public ProductPage (DaoImpl<Product> service) {
		this.servisOf = service;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		List<Product> list = servisOf.findAll();
		
		for (Product pr: list) viewers.add(new ProductViewer(pr)); 
		
		Label point = new Label("");
		
		Label label = new Label("Catalog Products");
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
		
		TableColumn<ProductViewer, Integer> firstNameCol = new TableColumn<ProductViewer, Integer>("id");
		TableColumn<ProductViewer, String> secondNameCol = new TableColumn<ProductViewer, String>("name");
		TableColumn<ProductViewer, Integer> thirdNameCol = new TableColumn<ProductViewer, Integer>("code");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, Integer>("id"));
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, String>("name"));
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, Integer>("code"));
		  	firstNameCol.setMinWidth(50);
			secondNameCol.setMinWidth(150);
			thirdNameCol.setMinWidth(50);
		  
		table.setItems(viewers);
		table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol);	
		
		 secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 	secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<ProductViewer, String>>() {
			   @Override
			   public void handle(CellEditEvent<ProductViewer, String> value) {
			    int activeRow = value.getTablePosition().getRow();
			    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
			    	Product posUp = servisOf.findAll().get(activeRow);
			    	posUp.setName(value.getNewValue());
			    	servisOf.update(posUp);
			   }
		  });
		  	//Открытие на редактирование колонка CODE
		  thirdNameCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		  //Cобытие на изменение ячейки
		  thirdNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ProductViewer, Integer>>() {	
			@Override
			public void handle(CellEditEvent<ProductViewer, Integer> value) {
				int activeRow = value.getTablePosition().getRow();
					value.getTableView().getItems().get(activeRow).setCode(value.getNewValue());
					Product posUp = servisOf.findAll().get(activeRow);
			    	posUp.setBarcode(value.getNewValue());
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
			  	nameProduct.setPromptText("new name of product");
				TextField codeProduct = new TextField();
				codeProduct.setMinHeight(40);
				codeProduct.setFont(new Font(15));
				codeProduct.setPromptText("new code of product");
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
							if (codeProduct.getText().isEmpty()) {
								message.setText("NO CODE");
								message.setVisible(true);
							}else {
								try{
									int cod = Integer.parseInt(codeProduct.getText());
									viewers.add(new ProductViewer(viewers.size()+1, nameProduct.getText(), cod));
									Product product = new Product(nameProduct.getText(), cod);
									servisOf.create(product);
									message.setText("Position added successfully" + "\n" + nameProduct.getText() + " - " + codeProduct.getText());
									message.setVisible(true);
									nameProduct.clear();
									codeProduct.clear();
								} catch (NumberFormatException e) {
									message.setText("INVALID CODE");
									message.setVisible(true);
									e.printStackTrace();
								}
							}
						} else {
							message.setText("NO NAME");
							message.setVisible(true);
						}
					}
				});
				
			hb.getChildren().setAll(nameProduct, codeProduct,add);
			gr.getChildren().addAll(hb, message);	
			
			return new Scene(gr, 500, 200);
		}
	}
}
