package ec.edu.ups.pw.ProyectoFinalBackend.dao;

import java.util.List;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Loan;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Stateless
public class LoanDAO {

    @PersistenceContext
    private EntityManager em;

    public void insert(Loan loan) {
        em.persist(loan);
    }

    public Loan read(int id) {
        return em.find(Loan.class, id);
    }

    public void update(Loan loan) {
        em.merge(loan);
    }

    public void delete(Loan loan) {
        em.remove(em.merge(loan));
    }

    public List<Loan> getAll() {
        return em.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
    }
    
    public List<Loan> getLoansByUserEmail(String email) {
        return em.createQuery("SELECT l FROM Loan l WHERE l.user.email = :email", Loan.class)
                 .setParameter("email", email)
                 .getResultList();
    }
    
    // Método para obtener la persona que más libros ha pedido prestados
    public Object[] getTopUser() {
        Query query = em.createQuery("SELECT l.user.email, COUNT(l) as totalLoans FROM Loan l GROUP BY l.user.email ORDER BY totalLoans DESC")
                        .setMaxResults(1);
        return (Object[]) query.getSingleResult();
    }

    // Método para obtener el libro que ha sido más veces prestado
    public Object[] getTopBook() {
        Query query = em.createQuery("SELECT l.book.name, COUNT(l) as totalLoans FROM Loan l GROUP BY l.book.id ORDER BY totalLoans DESC")
                        .setMaxResults(1);
        return (Object[]) query.getSingleResult();
    }
}