package usa.badratdinova.bookexchange.dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select b from Book b", Book.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Book show(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Book.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Book> show(String title) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Book> query = session.createQuery(
                "select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public Person getPersonByBookId(int id) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Person> query = session.createQuery(
                "select p from Person p left join fetch p.books b where b.id = :id", Person.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void issue(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book bookFromDatabase = session.get(Book.class, id);
        bookFromDatabase.setPerson(person);
    }

    @Transactional
    public void returnBook(int id) {
        Session session = sessionFactory.getCurrentSession();
        Book bookFromDatabase = session.get(Book.class, id);
        bookFromDatabase.setPerson(null);
    }

    @Transactional
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Session session = sessionFactory.getCurrentSession();
        Book bookToBeUpdated = session.get(Book.class, id);
        bookToBeUpdated.setTitle(book.getTitle());
        bookToBeUpdated.setAuthor(book.getAuthor());
        bookToBeUpdated.setYear(book.getYear());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class, id));
    }
}
