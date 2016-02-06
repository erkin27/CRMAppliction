package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="employees")
public class Employee {
	@Id
	@GeneratedValue(generator = "incrementId")
	@GenericGenerator(name = "incrementId", strategy = "increment")
	@Column(name="id")
	private Long id;
	
	@Column(name="name")
	private String name;

	@Column(name="salary")
	private int salary;
	
	@OneToOne (mappedBy = "employee")
//    @JoinColumn(name = "id")
//    @PrimaryKeyJoinColumn
    private User user;
	
	public Employee () {
		super();
	}
	
	public Employee(String name, int salary) {
		this.name = name;
		this.salary = salary;
	}
	
	public Employee(String name, int salary, User user) {
		this.name = name;
		this.salary = salary;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public User getUser() {
		  return user;
		 }

	public void setUser(User user) {
		  this.user = user;
		 }

	@Override
	 public String toString() {
	  String userName = "null";
//	  if(user==null) userName = user.getName();
	  return "Employee [id=" + id + ", name=" + name + ", salary=" + salary + ", user=" + userName + "]";
	 }
	
	
}
