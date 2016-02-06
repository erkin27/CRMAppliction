package CRMPages;

import java.util.List;

import CRM.CRMApplication;
import CRM.ControlPanel;
import CRM.actionsPanel;
import CRMPages.ProductPage.addControl;
import dao.DaoImpl;
import data.Employee;
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
import view.EmployeeViewer;


public class EmployeePage implements actionsPanel {
	private ObservableList<EmployeeViewer> viewers = FXCollections.observableArrayList();
	private TableView<EmployeeViewer> table = new TableView<EmployeeViewer>();
	private DaoImpl<Employee> servisOf = null;
	private Group group = new Group();
	
	public Group getGroup() {
		return group;
	}

	public EmployeePage (DaoImpl<Employee> service) {
		this.servisOf  = service;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		List<Employee> list = servisOf.findAll();
		
		for (Employee pr: list) viewers.add(new EmployeeViewer(pr)); 
		
		Label point = new Label("");
		
		Label label = new Label("Catalog Employees");
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
		
		TableColumn<EmployeeViewer, Integer> firstNameCol = new TableColumn<EmployeeViewer, Integer>("id");
		TableColumn<EmployeeViewer, String> secondNameCol = new TableColumn<EmployeeViewer, String>("name");
		TableColumn<EmployeeViewer, Integer> thirdNameCol = new TableColumn<EmployeeViewer, Integer>("salary");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, Integer>("id"));
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, String>("name"));
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, Integer>("salary"));
		  	firstNameCol.setMinWidth(50);
			secondNameCol.setMinWidth(150);
			thirdNameCol.setMinWidth(50);
		  
		table.setItems(viewers);
		table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol);	
		
		 secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		 	secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<EmployeeViewer, String>>() {
			   @Override
			   public void handle(CellEditEvent<EmployeeViewer, String> value) {
			    int activeRow = value.getTablePosition().getRow();
			    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
			    	Employee posUp = servisOf.findAll().get(activeRow);
			    	posUp.setName(value.getNewValue());
			    	servisOf.update(posUp);
			   }
		  });
		  	//Открытие на редактирование колонка CODE
		  thirdNameCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		  //Cобытие на изменение ячейки
		  thirdNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<EmployeeViewer, Integer>>() {	
			@Override
			public void handle(CellEditEvent<EmployeeViewer, Integer> value) {
				int activeRow = value.getTablePosition().getRow();
					value.getTableView().getItems().get(activeRow).setSalary(value.getNewValue());
					Employee posUp = servisOf.findAll().get(activeRow);
			    	posUp.setSalary(value.getNewValue());
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
			  	nameProduct.setPromptText("new name of employee");
				TextField codeProduct = new TextField();
				codeProduct.setMinHeight(40);
				codeProduct.setFont(new Font(15));
				codeProduct.setPromptText("new salary of employee");
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
								message.setText("NO SALARY");
								message.setVisible(true);
							}else {
								try{
									int cod = Integer.parseInt(codeProduct.getText());
									viewers.add(new EmployeeViewer(viewers.size()+1, nameProduct.getText(), cod));
									Employee product = new Employee(nameProduct.getText(), cod);
									servisOf.create(product);
									message.setText("Position added successfully" + "\n" + nameProduct.getText() + " - " + codeProduct.getText());
									message.setVisible(true);
									nameProduct.clear();
									codeProduct.clear();
								} catch (NumberFormatException e) {
									message.setText("INVALID SALARY");
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
