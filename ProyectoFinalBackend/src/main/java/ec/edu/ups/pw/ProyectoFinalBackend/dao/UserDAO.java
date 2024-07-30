package ec.edu.ups.pw.ProyectoFinalBackend.dao;

import java.util.List;

import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class UserDAO {

	@PersistenceContext
	EntityManager em;
	
	public User read(String userName) {
		User user = this.em.find(User.class, userName);
		return user;
	}
	
	public List<User> getAll() {
		String jpql = "SELECT c FROM User c";
		Query query = this.em.createQuery(jpql, User.class);
		return query.getResultList();
	}
	
	public void insert(User user) {
		this.em.persist(user);
	}
	
	public void delete(String userName) {
		User user = this.read(userName);
		this.em.remove(user);
	}
	
	public void update(User user) {
		this.em.merge(user);
	}
}
