package data;

import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(generator = "increment1")
	@GenericGenerator(name = "increment1", strategy = "increment")
	@Column (name = "id")
	private Long id;
	
	@Column (name = "number")
	private String number;
	
	@Column (name = "summa")
	private String summa;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	
	
	//============Getters & Setters============================
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSumma() {
		return summa;
	}

	public void setSumma(String summa) {
		this.summa = summa;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	//=============Constructors==============================
	public Order(String number, String summa, Date date) {
		super();
		this.number = number;
		this.summa = summa;
		this.date = date;
	}
	
	public Order () {
		super();
	}

	@Override
	public String toString() {
		return "Order [number=" + number + ", summa=" + summa + ", date=" + date + "]";
	}

//	public Contructor getContructor() {
//		return contructor;
//	}
//
//	public void setContructor(Contructor contructor) {
//		this.contructor = contructor;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
}
