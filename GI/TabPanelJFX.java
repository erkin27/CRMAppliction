package GI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.function.Consumer;

import org.omg.CORBA.CharSeqHolder;

import dao.ProductDaoImpl;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import util.HibernateUtil;
import view.ProductViewer;

public class TabPanelJFX extends Application implements EventHandler<MouseEvent>{
	ProductDaoImpl prodImpl = new ProductDaoImpl();
	List<Product> productsDB;
	ObservableList<ProductViewer> list;
	public static Stage primaryStage = new Stage();

	public static void main(String[] args) {
		launch(args);
		HibernateUtil.getSessionFactory().close();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Table");
		primaryStage.setScene(createScene());
		primaryStage.show();
		this.primaryStage = primaryStage;
	}
	
	public Scene createScene () {
		TabPane tabPane = new TabPane();
		//Вкладки
		Tab tab1 = new Tab("Product");
		tab1.setContent(createPane());
		Tab tab2 = new Tab("Contructors");
		tab2.setContent(ContructorsTab.createPane());
		Tab tab3 = new Tab("Employees");
		tab3.setContent(EmployeesTab.createPane());
		
		tabPane.getTabs().addAll(tab1, tab2, tab3);
		Scene scene = new Scene(tabPane, 1040, 600);
		
		return scene;
	}
	
	public Group createPane () {
		Group group = new Group();
		VBox vbox = new VBox();
		Label label = new Label("List of products");
		label.setFont(new Font("Arial", 20));
		label.setPadding(new Insets(50, 0, 0, 100));
		//Create TableView
		TableView<ProductViewer> table = new TableView<ProductViewer>();
		table.setEditable(true);
		table.setMinWidth(800);
		table.setMaxHeight(500);
		TableColumn<ProductViewer, Integer> firstNameCol = new TableColumn<ProductViewer, Integer>("id");
		TableColumn<ProductViewer, String> secondNameCol = new TableColumn<ProductViewer, String>("name");
		TableColumn<ProductViewer, Integer> thirdNameCol = new TableColumn<ProductViewer, Integer>("code");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, Integer>("id"));
		  firstNameCol.setMinWidth(100);
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, String>("name"));
		  secondNameCol.setMinWidth(400);
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<ProductViewer, Integer>("code"));
		  thirdNameCol.setMinWidth(100);
		  
		  //Добавление колонок в таблицу
		  list = getList();
		  table.setItems(list);
		  table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol);	
		  //Панель для дополнительных операций
		  HBox hb = new HBox();
		  hb.setPadding(new Insets(40, 0, 20, 300));
		  hb.setSpacing(10);
		  	TextField nameProduct = new TextField();
		  	nameProduct.setPromptText("new name of product");
			TextField codeProduct = new TextField();
			codeProduct.setPromptText("new code of product");
			Button add = new Button("Add");
			Button close = new Button("Close");
			Text textEdit = new Text("Edit operation is open");
			textEdit.setFont(new Font(15));
		
		//Создание кнопок 
		HBox btns = new HBox();
		btns.setStyle("-fx-background-color:#E6EFF5"); 
		btns.setMinHeight(60);
		Button[] buttons = new Button[12];
		String [] commands = new String[]{"HELP", "SAVE", "", "EDIT", "", "", "ADD", "DELETE", "REFRESH", "EXIT", "IMPORT", "EXPORT"};
		for (int i = 0; i<buttons.length; i++) {
			buttons[i] = new Button("F" +Integer.toString(i+1) + "\n" + commands[i]);
			buttons[i].setTooltip(new Tooltip(commands[i]));
			buttons[i].setStyle("-fx-background-color:#C2CCCF");
			buttons[i].setMaxSize(70, 60);
			buttons[i].setMinSize(70, 60);
		}
		btns.getChildren().addAll(buttons);
		btns.setSpacing(15);
		btns.setPadding(new Insets(10, 0, 0, 0));
		btns.setAlignment(Pos.CENTER);
		
		//		Добавления таблицы и кнопок на панель
		vbox.getChildren().setAll(table, btns);
		vbox.setMinWidth(800);
		vbox.setMaxHeight(500);
//		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(100, 20, 20, 20));
		
		//==========EDIT============================
		buttons[3].setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent event) {
				if (i== 0) vbox.getChildren().add(textEdit);
				// TODO Auto-generated method stub
				//Редактирование колонок
				 //Открытие на редактирование колонка NAME
			  secondNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
			  	//Cобытие на изменение ячейки
			  secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<ProductViewer, String>>() {
				   @Override
				   public void handle(CellEditEvent<ProductViewer, String> value) {
				    int activeRow = value.getTablePosition().getRow();
				    if (value.getNewValue().isEmpty()) {
				    	MyAlert er = new MyAlert ("ERROR! Please try again." + "\n" +  "Input name of product");
						Stage primaryStage = new Stage();
						try {
							er.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    } else {
				    	value.getTableView().getItems().get(activeRow).setName(value.getNewValue());
				    }
				    	
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
				}
			  }); 
			  i++;
			}
		});
		//==========ADD POSITION============================
		buttons[6].setOnAction(new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
			if (i==0) hb.getChildren().addAll(nameProduct, codeProduct, add, close);
			
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if (!nameProduct.getText().isEmpty()) {
							if (!codeProduct.getText().matches("\\d" +"\\d" + "\\d" + "\\d")) {
								MyAlert  er = new MyAlert ("ERROR! Please try again." + "\n" +  "Сode must have 4 digits");
								Stage primaryStage = new Stage();
								try {
									er.start(primaryStage);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
							}else {
								int cod = Integer.parseInt(codeProduct.getText());
								list.add(new ProductViewer(list.size()+1, nameProduct.getText(), cod));
								nameProduct.clear();
								codeProduct.clear();
							}
						} else {
							MyAlert er = new MyAlert ("ERROR! Please try again." + "\n" +  "Input name of product");
							Stage primaryStage = new Stage();
							try {
								er.start(primaryStage);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}	
				});
				i++;
				close.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
					 hb.getChildren().removeAll(nameProduct, codeProduct, add, close);
					 i=0;
					}
				});
			}
		});
		//==========DELETE POSITION============================
		buttons[7].setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int selectedInd = table.getSelectionModel().getSelectedIndex(); 
				if (selectedInd >= 0) {
					table.getItems().remove(selectedInd);
				} else {
					//Nothing selected
					Stage st = new Stage();
					MyAlert  er = new MyAlert ("Please select row for delete");
					try {
						er.start(st);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});	
		//==========REFRESH============================
		buttons[8].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				list = getList();
				table.setItems(list);
			}
		});
		//==========SAVE=============================================	
		list = table.getItems();
		buttons[1].setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				setList(list);
				Stage st = new Stage();
				MyAlert  er = new MyAlert ("Executed store successfully");
				try {
					er.start(st);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		//==========EXIT=====================================================
		buttons[9].setOnAction(eventExit-> TabPanelJFX.primaryStage.close());
			
		//		Добавление панели в группу
		group.getChildren().addAll(label, vbox, hb);
		
		return group;
	}
	
		// Список данных с базы
	public ObservableList<ProductViewer> getList () {
		productsDB = prodImpl.findAll();
		ObservableList<ProductViewer> listProducts = FXCollections.observableArrayList();
		for (int i = 0; i<productsDB.size(); i++) {
			Product pr = productsDB.get(i);
			ProductViewer viewer = new ProductViewer(i+1, pr.getName(), pr.getBarcode());
			listProducts.add(viewer);
		}
		return listProducts;
	}
		//Сохранение в базу данных
	public void setList (ObservableList<ProductViewer>list) {
		if (list.size() == productsDB.size()) {
		for (int i =0; i<list.size(); i++) {
			productsDB.get(i).setName(list.get(i).getName());
			productsDB.get(i).setBarcode(list.get(i).getCode());
		}
		productsDB.forEach(product->prodImpl.update(product));
		} else {
			List<Product> productsDBNEW = new ArrayList<Product>();
			list.forEach(new Consumer<ProductViewer>() {

				@Override
				public void accept(ProductViewer prod) {
					// TODO Auto-generated method stub
					productsDBNEW.add(new Product(prod.getName(), prod.getCode()));
				}
			});
			productsDB.forEach(product->prodImpl.delete(product));
			productsDBNEW.forEach(product-> prodImpl.create(product));
		}
	}
	
	public class MyAlert extends Application {
		String message;
		Stage stage;
		public MyAlert (String message) {
			// TODO Auto-generated constructor stub
			this.message = message;
		}
		@Override
		public void start(Stage primaryStage) throws Exception {
			// TODO Auto-generated method stub
			stage = primaryStage;
			stage.setTitle("Alert");
			stage.setScene(sc(message));
			stage.show();
		}
		public Scene sc (String message) {
			GridPane gp = new GridPane();
			gp.setMaxSize(150, 150);
			Button text = new Button(message);
			text.setOnAction(eventClose->stage.close());
			text.setFont(new Font("Arial", 20));
			text.setAlignment(Pos.CENTER);
			gp.setAlignment(Pos.CENTER);
			gp.getChildren().add(text);
			Scene sc = new Scene(gp, 300, 150);
			
			return sc;
			
		}
	}
	
	@Override
	public void handle(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}
}
