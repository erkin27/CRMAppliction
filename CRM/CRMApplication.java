package CRM;

import CRMPages.ContructorPage;
import CRMPages.EmployeePage;
import CRMPages.ProductPage;
import CRMPages.RolePage;
import CRMPages.UserPage;
import GI.ContructorsTab;
import GI.EmployeesTab;
import dao.DaoImpl;
import data.Contructor;
import data.Employee;
import data.Product;
import data.Role;
import data.User;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import util.HibernateUtil;

public class CRMApplication extends Application{
	public static Stage primaryStage = new Stage();
	public static void main(String[] args) {
		launch(args);
		HibernateUtil.getSessionFactory().close();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("CRM SYSTEM");
		primaryStage.setScene(createScene());
		primaryStage.show();
		this.primaryStage = primaryStage;
	}

	private Scene createScene() {
		// TODO Auto-generated method stub
		TabPane tabPane = new TabPane();
		//Вкладки
		Tab tab1 = new Tab("Catalog Products");
		tab1.setContent(createPane1());
		Tab tab2 = new Tab("Catalog Contructors");
		tab2.setContent(createPane2());
		Tab tab3 = new Tab("Catalog Employees");
		tab3.setContent(createPane3());
		Tab tab4 = new Tab("Catalog Users");
		tab4.setContent(createPane4());
		Tab tab5 = new Tab("Catalogs Roles");
		tab5.setContent(createPane5());
		Tab tab6 = new Tab("List of Orders");
		tab6.setContent(createPane6());
		
		tabPane.getTabs().addAll(tab1, tab2, tab3, tab4, tab5);
		Scene scene = new Scene(tabPane, 800, 600);

		return scene;
	}

	private Group createPane6() {
		// TODO Auto-generated method stub
		return null;
	}

	private Group createPane5() {
		// TODO Auto-generated method stub
		DaoImpl<Role> serviceProduct = new DaoImpl<>(Role.class);
		RolePage page = new RolePage(serviceProduct);
		return page.getGroup();
	}

	private Group createPane4() {
		// TODO Auto-generated method stub
		DaoImpl<User> serviceProduct = new DaoImpl<>(User.class);
		UserPage page = new UserPage(serviceProduct);
		return page.getGroup();
	}

	private Group createPane3() {
		// TODO Auto-generated method stub
		DaoImpl<Employee> serviceProduct = new DaoImpl<>(Employee.class);
		EmployeePage page = new EmployeePage(serviceProduct);
		return page.getGroup();
	}

	private Group createPane2() {
		// TODO Auto-generated method stub
		DaoImpl<Contructor> serviceProduct = new DaoImpl<>(Contructor.class);
		ContructorPage page = new ContructorPage(serviceProduct);
		return page.getGroup();
	}

	public Group createPane1 () {
		// TODO Auto-generated method stub
		DaoImpl<Product> serviceProduct = new DaoImpl(Product.class);
		ProductPage page = new ProductPage(serviceProduct);
		return page.getGroup();
	}

}
