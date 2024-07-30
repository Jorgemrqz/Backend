package ec.edu.ups.pw.ProyectoFinalBackend.model;

import java.io.Serializable;

import ec.edu.ups.pw.ProyectoFinalBackend.enums.Rol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User implements Serializable{
	
	@Id
	@Column(name = "us_email")
	private String email;
	
	@Column(name = "us_password")
	private String password;
	
	@Column(name = "us_rol")
	private Rol rol;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Rol getRol() {
		return rol;
	}
	
	public void setRol(Rol rol) {
		this.rol = rol;
	}
}