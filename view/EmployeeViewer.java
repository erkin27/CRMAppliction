package view;

import data.Employee;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeViewer {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleIntegerProperty salary;
	private static int count = 1;
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
	public int getSalary() {
		return salary.get();
	}
	public void setSalary(Integer salary) {
		this.salary.set(salary);
	}
	
	public EmployeeViewer (int id, String name, int salary) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.salary = new SimpleIntegerProperty(salary);
	}
	public EmployeeViewer(Employee pr) {
		// TODO Auto-generated constructor stub
		this.id = new SimpleIntegerProperty(count++);
		this.name = new SimpleStringProperty(pr.getName());
		this.salary = new SimpleIntegerProperty(pr.getSalary());
		
	}
	public Employee toEmployee() {
		// TODO Auto-generated method stub
		return new Employee(this.name.get(), this.salary.get());
	}
}
