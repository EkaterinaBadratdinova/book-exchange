package usa.badratdinova.bookexchange.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?",
                        new Object[] {id},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String title) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE title = ?",
                        new Object[]{title},
                        new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny();
    }

    public Person getPersonByBookId(int id) {
        return jdbcTemplate.query("SELECT surnameNamePatronymic FROM Person LEFT JOIN Book ON Book.personId = Person.id WHERE book.id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void issue(int id, Person person) {
        jdbcTemplate.update("UPDATE Book SET personId = ? WHERE id = ?",
                person.getId(), id);
    }

    public void returnBook(int id) {
        jdbcTemplate.update("UPDATE Book SET personId = null WHERE id = ?", id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(title, author, year, personId) VALUES(?, ?, ?, null)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }
}
