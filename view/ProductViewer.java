package view;

import java.lang.reflect.Field;

import data.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProductViewer {
	public SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty code;
    private static int count=1; 
   
    public ProductViewer(int id, String name, int code) {
          this.id = new SimpleIntegerProperty(id);
          this.name = new SimpleStringProperty(name);
          this.code = new SimpleIntegerProperty(code);
    }
    
    public ProductViewer (Product prod) {
    	this.id = new SimpleIntegerProperty(count++);
        this.name = new SimpleStringProperty(prod.getName());
        this.code = new SimpleIntegerProperty(prod.getBarcode());
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
    public Integer getCode() {
          return code.get();
    }
    public void setCode(Integer code) {
          this.code.set(code);
    }

	public Product toProduct() {
		// TODO Auto-generated method stub
		return new Product(this.name.get(), this.code.get());
	}

}