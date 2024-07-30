package ec.edu.ups.pw.ProyectoFinalBackend.bussines;

import java.util.List;

import ec.edu.ups.pw.ProyectoFinalBackend.dao.UserDAO;
import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class UserManagement {

	@Inject
	UserDAO userD;

	public User getUser(String userName) {
		return this.userD.read(userName);
	}

	public List<User> getUsers() {
		return this.userD.getAll();
	}

	public void insertUSer(User user) {
		this.userD.insert(user);
	}

	public void updateUser(User user) {
		this.userD.update(user);
	}

	public void deleteUser(String email) {
		this.userD.delete(email);
	}
}
