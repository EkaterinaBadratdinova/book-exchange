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
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery("select p from Person p", Person.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> show(String surnameNamePatronymic) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Person> query = session.createQuery(
                "select p from Person p where p.surnameNamePatronymic = :surnameNamePatronymic", Person.class);
        query.setParameter("surnameNamePatronymic", surnameNamePatronymic);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPersonId(int id) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Book> query = session.createQuery(
                "select b from Book b left join fetch b.person p where p.id = :personId", Book.class);
        query.setParameter("personId", id);

        return query.getResultList();
    }

    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);
        personToBeUpdated.setSurnameNamePatronymic(person.getSurnameNamePatronymic());
        personToBeUpdated.setYearOfBirth(person.getYearOfBirth());
    }

    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class, id));
    }
}
