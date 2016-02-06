package view;

import data.User;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserViewer {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty login;
	private SimpleStringProperty pass;
	private SimpleStringProperty employee;
	private static int count = 1;
	
	public UserViewer(int id, String name, String login, String pass) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.login = new SimpleStringProperty(login);
		this.pass = new SimpleStringProperty(pass);
	}
	
	public UserViewer(User user) {
		super();
		this.id = new SimpleIntegerProperty(count++);
		this.name = new SimpleStringProperty(user.getName());
		this.login = new SimpleStringProperty(user.getLogin());
		this.pass = new SimpleStringProperty(user.getPass());
		if (user.getEmployee()!= null)this.employee = new SimpleStringProperty(user.getEmployee().getName());
	}

	public int getId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getLogin() {
		return login.get();
	}

	public void setLogin(String login) {
		this.login.set(login);
	}

	public String getPass() {
		return pass.get();
	}

	public void setPass(String pass) {
		this.pass.set(pass);
	}

	public String getEmployee() {
		if (employee != null) {
			return employee.get();
		} else {
			return null;
		}
	}

	
	
}
