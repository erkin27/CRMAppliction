package view;

import data.Role;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class RoleViewer {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private SimpleStringProperty comment;
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
	public String getComment() {
		return comment.get();
	}
	public void setComment(String comment) {
		this.comment.set(comment);
	}
	public RoleViewer(int id, String name, String comment) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.comment = new SimpleStringProperty(comment);
	}
	
	public RoleViewer(Role role) {
		super();
		this.id = new SimpleIntegerProperty(count++);
		this.name = new SimpleStringProperty(role.getName());
		this.comment = new SimpleStringProperty(role.getComment());
	}
}
