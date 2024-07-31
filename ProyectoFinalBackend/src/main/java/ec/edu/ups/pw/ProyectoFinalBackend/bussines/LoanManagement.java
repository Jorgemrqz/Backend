package ec.edu.ups.pw.ProyectoFinalBackend.bussines;

import java.util.Date;
import java.util.List;
import ec.edu.ups.pw.ProyectoFinalBackend.dao.BookDAO;
import ec.edu.ups.pw.ProyectoFinalBackend.dao.LoanDAO;
import ec.edu.ups.pw.ProyectoFinalBackend.dao.UserDAO;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Book;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Loan;
import ec.edu.ups.pw.ProyectoFinalBackend.model.User;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class LoanManagement {

    @Inject
    LoanDAO loanDAO;

    @Inject
    BookDAO bookDAO;

    @Inject
    UserDAO userDAO;

    public Loan getLoan(int id) {
        return this.loanDAO.read(id);
    }

    public List<Loan> getLoans() {
        return this.loanDAO.getAll();
    }

    public void insertLoan(Loan loan) {
        // Obtener el libro y el usuario desde la base de datos
        Book book = this.bookDAO.read(loan.getBook().getId());
        User user = this.userDAO.read(loan.getUser().getEmail());

        if (book == null || user == null) {
            throw new RuntimeException("El libro o el usuario no existen.");
        }

        // Verificar si el libro está disponible
        if (!book.getAvailability().equals("available")) {
            throw new RuntimeException("El libro no está disponible para préstamo.");
        }

        // Actualizar disponibilidad del libro
        book.setAvailability("loaned");
        this.bookDAO.update(book);

        // Asignar libro y usuario al préstamo
        loan.setBook(book);
        loan.setUser(user);
        loan.setStatus("loaned");
        loan.setLoanDate(new Date());

        this.loanDAO.insert(loan);
    }

    public void returnLoan(int loanId) {
        Loan loan = this.loanDAO.read(loanId);
        loan.setStatus("returned"); // Cambiado a "returned"
        loan.setReturnDate(new Date());

        // Update book availability
        Book book = loan.getBook();
        book.setAvailability("available"); // Cambiado a "available"
        this.bookDAO.update(book);

        this.loanDAO.update(loan);
    }
    
    public List<Loan> getLoansByUserEmail(String email) {
        User user = userDAO.read(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado.");
        }
        return loanDAO.getLoansByUserEmail(email);
    }
    
    public Object[] getTopUser() {
        return this.loanDAO.getTopUser();
    }

    public Object[] getTopBook() {
        return this.loanDAO.getTopBook();
    }
}