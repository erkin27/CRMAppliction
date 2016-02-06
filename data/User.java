package data;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.criteria.Fetch;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {
 
 @Id
 @GeneratedValue(generator = "increment")
 @GenericGenerator(name = "increment", strategy = "increment")
 @Column(name = "id")
 private Long id;

 @Column(name = "name")
 private String name;

 @Column(name = "login")
 private String login;

 @Column(name = "pass")
 private String pass;
 
// 
 @OneToOne 
@JoinColumn(name = "employee_id")
// @PrimaryKeyJoinColumn
// @Column(name = "employee_id")
 private Employee employee;
 
// @OneToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
// @JoinTable(name="roles_users",
//   joinColumns = @JoinColumn(name = "user_id"),
//   inverseJoinColumns = @JoinColumn(name = "role_id")
// )
// private Set<Role> roles;


 public User() {
 }

 
 public User(String name, String login, String pass, Employee employee) {
  this.name = name;
  this.login = login;
  this.pass = pass;
  this.employee = employee;
 }

 public String getLogin() {
  return login;
 }

 public void setLogin(String login) {
  this.login = login;
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

 public String getPass() {
  return pass;
 }

 public void setPass(String pass) {
  this.pass = pass;
 }

 public Employee getEmployee() {
  return employee;
 }

 public void setEmployee(Employee employee) {
  this.employee = employee;
 }
//
// public Set<Role> getRoles() {
//	return roles;
//}
//
//public void setRoles(Set<Role> roles) {
//	this.roles = roles;
//}

@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", login=" + login + ", pass=" + pass + "]";
}

}