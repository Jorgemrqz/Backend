package ec.edu.ups.pw.ProyectoFinalBackend.service;

import java.util.List;

import ec.edu.ups.pw.ProyectoFinalBackend.bussines.BookManagement;
import ec.edu.ups.pw.ProyectoFinalBackend.model.Book;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/book")
public class BookService {
	
	@Inject
	BookManagement bm;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getBooks() {
		List<Book> books = this.bm.getBooks();
		return Response.ok(books).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getBook(@PathParam("id") int id) {
		try {
			Book book = this.bm.getBook(id);
			return Response.ok(book).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciales Invalidas").build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create")
	public Response createBook(Book book) {
		try {
			this.bm.insertBook(book);
			return Response.ok(book).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al crear el libro").build();
		}
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public Response deleteBook(@PathParam("id") int id) {
		try {
			this.bm.delete(id);
			return Response.ok().build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("No se ha podido eliminar el libro solicitado").build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/update")
	public Response updateBook(Book book) {
		try {
			this.bm.updateBook(book);
			return Response.ok(book).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al intentar actualizar el libro").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/author/{author}")
	public Response searchBooksByAuthor(@PathParam("author") String author) {
		try {
			List<Book> books = this.bm.searchBooksByAuthor(author);
			return Response.ok(books).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al buscar libros").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/name/{name}")
	public Response searchBooksByName(@PathParam("name") String name) {
		try {
			List<Book> books = this.bm.searchBooksByName(name);
			return Response.ok(books).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al buscar libros").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/genere/{genere}")
	public Response searchBooksByGenere(@PathParam("genere") String genere) {
		try {
			List<Book> books = this.bm.searchBooksByGenere(genere);
			return Response.ok(books).build();
		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(Response.Status.UNAUTHORIZED).entity("Error al buscar libros").build();
		}
	}
}
