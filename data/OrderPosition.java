package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name = "order_positions")
public class OrderPosition {
	@Id
	@GeneratedValue(generator = "increment1")
	@GenericGenerator(name = "increment1", strategy = "increment")
	@Column(name = "id")
	private Long id;

	@Column (name = "kol")
	private int kol;
	
	@Column (name = "pricce")
	private int price;

	@Column (name = "summa")
	private int summa;
	
	public OrderPosition () {
		super();
	}
	
	public OrderPosition(int kol, int summa) {
		super();
		this.kol = kol;
		this.summa = summa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getKol() {
		return kol;
	}

	public void setKol(int kol) {
		this.kol = kol;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSumma() {
		return summa;
	}

	public void setSumma(int summa) {
		this.summa = summa;
	}
	

}
