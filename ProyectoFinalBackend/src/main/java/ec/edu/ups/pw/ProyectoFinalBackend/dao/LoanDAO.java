package ec.edu.ups.pw.ProyectoFinalBackend.dao;

import java.util.List;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Loan;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

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
}