package data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "roles_users")
public class RoleUser {
	
	@Id
	@GeneratedValue(generator = "incr")
	@GenericGenerator (name = "incr", strategy = "increment")
	@Column (name = "id")
	private Long id;
	
	
}
