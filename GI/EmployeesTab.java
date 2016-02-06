package GI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import GI.TabPanelJFX.MyAlert;
import dao.ContructorDaoImpl;
import dao.EmployeeDaoImpl;
import data.Contructor;
import data.Employee;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import view.ContructorViewer;
import view.EmployeeViewer;
import view.ProductViewer;

public class EmployeesTab {
	static EmployeeDaoImpl emplImpl = new EmployeeDaoImpl();
	static List<Employee> employeesDB;
	static ObservableList<EmployeeViewer> list;
	
	public static Group createPane () {
		Group group = new Group();
		VBox vbox = new VBox();
		Label label = new Label("List of employees");
		label.setFont(new Font("Arial", 20));
		label.setPadding(new Insets(50, 0, 0, 100));
		//Create TableView
		TableView<EmployeeViewer> table = new TableView<EmployeeViewer>();
		table.setEditable(true);
		table.setMinWidth(800);
		table.setMaxHeight(500);
		TableColumn<EmployeeViewer, Integer> firstNameCol = new TableColumn<EmployeeViewer, Integer>("id");
		TableColumn<EmployeeViewer, String> secondNameCol = new TableColumn<EmployeeViewer, String>("name");
		TableColumn<EmployeeViewer, Integer> thirdNameCol = new TableColumn<EmployeeViewer, Integer>("salary");
		  firstNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, Integer>("id"));
		  firstNameCol.setMinWidth(100);
		  secondNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, String>("name"));
		  secondNameCol.setMinWidth(400);
		  thirdNameCol.setCellValueFactory(new PropertyValueFactory<EmployeeViewer, Integer>("salary"));
		  thirdNameCol.setMinWidth(100);
		  
		  //Добавление колонок в таблицу
		  list = getList();
		  table.setItems(list);
		  table.getColumns().addAll(firstNameCol,secondNameCol,thirdNameCol);	
		  //Панель для дополнительных операций
		  HBox hb = new HBox();
		  hb.setPadding(new Insets(40, 0, 20, 300));
		  hb.setSpacing(10);
		  	TextField nameEmployee = new TextField();
		  	nameEmployee.setPromptText("new name of employee");
			TextField salEmployee = new TextField();
			salEmployee.setPromptText("new salary of employee");
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
			  secondNameCol.setOnEditCommit(new EventHandler<CellEditEvent<EmployeeViewer, String>>() {
				   @Override
				   public void handle(CellEditEvent<EmployeeViewer, String> value) {
				    int activeRow = value.getTablePosition().getRow();
				    if (value.getNewValue().isEmpty()) {
				    	MyAlert er = new MyAlert ("ERROR! Please try again." + "\n" +  "Input name of employee");
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
			  thirdNameCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<EmployeeViewer, Integer>>() {	
				@Override
				public void handle(CellEditEvent<EmployeeViewer, Integer> value) {
					int activeRow = value.getTablePosition().getRow();
						value.getTableView().getItems().get(activeRow).setSalary(value.getNewValue());
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
			if (i==0) hb.getChildren().addAll(nameEmployee, salEmployee, add, close);
			
				add.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						if (!nameEmployee.getText().isEmpty()) {
							if (salEmployee.getText().isEmpty()) {
								MyAlert  er = new MyAlert ("ERROR! Please try again." + "\n" +  "Salary not be null");
								Stage primaryStage = new Stage();
								try {
									er.start(primaryStage);
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
							}else {
								try {
									Integer.parseInt(salEmployee.getText());
								} catch (NumberFormatException e) {
									// TODO: handle exception
									MyAlert  er = new MyAlert ("ERROR! Please try again." + "\n" +  "Salary incorrect");
									Stage primaryStage = new Stage();
									try {
										er.start(primaryStage);
									} catch (Exception e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									e.printStackTrace();
								}
								int salary = Integer.parseInt(salEmployee.getText());
								list.add(new EmployeeViewer(list.size()+1, nameEmployee.getText(), salary));
								nameEmployee.clear();
								salEmployee.clear();
							}
						} else {
							MyAlert er = new MyAlert ("ERROR! Please try again." + "\n" +  "Input name of employee");
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
					 hb.getChildren().removeAll(nameEmployee, salEmployee, add, close);
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
	public static ObservableList<EmployeeViewer> getList () {
		employeesDB = emplImpl.findAll();
		ObservableList<EmployeeViewer> listEmployees = FXCollections.observableArrayList();
		for (int i = 0; i<employeesDB.size(); i++) {
			Employee em = employeesDB.get(i);
			EmployeeViewer viewer = new EmployeeViewer(i+1, em.getName(), em.getSalary());
			listEmployees.add(viewer);
		}
		return listEmployees;
	}
		//Сохранение в базу данных
	public static void setList (ObservableList<EmployeeViewer>list) {
		if (list.size() == employeesDB.size()) {
		for (int i =0; i<list.size(); i++) {
			employeesDB.get(i).setName(list.get(i).getName());
			employeesDB.get(i).setSalary(list.get(i).getSalary());
		}
		employeesDB.forEach(employee->emplImpl.update(employee));
		} else {
			List<Employee> employeesDBNEW = new ArrayList<Employee>();
			list.forEach(new Consumer<EmployeeViewer>() {

				@Override
				public void accept(EmployeeViewer empl) {
					// TODO Auto-generated method stub
					employeesDBNEW.add(new Employee(empl.getName(), empl.getSalary()));
				}
			});
			employeesDB.forEach(em->emplImpl.delete(em));
			employeesDBNEW.forEach(em-> emplImpl.create(em));
		}
	}
	
	public static class MyAlert extends Application {
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
}
