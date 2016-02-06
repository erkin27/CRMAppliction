package view;

import data.Contructor;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ContructorViewer {
	private SimpleIntegerProperty id;
	private SimpleStringProperty name;
	private static int count=1;
	
	public ContructorViewer(int id, String name) {
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
	}
	
	public ContructorViewer(Contructor contr) {
		this.id = new SimpleIntegerProperty(count++);
		this.name = new SimpleStringProperty(contr.getName());
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

	public Contructor toContruct() {
		// TODO Auto-generated method stub
		return new Contructor(this.name.get());
	}
}
