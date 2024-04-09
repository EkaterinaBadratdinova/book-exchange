package usa.badratdinova.bookexchange.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id = ?",
                new Object[] {id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public Optional<Person> show(String surnameNamePatronymic) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE surnameNamePatronymic = ?",
                new Object[]{surnameNamePatronymic},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT title, author, year FROM Book LEFT JOIN Person ON Book.personId = Person.id WHERE personId = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class))
                .stream().collect(Collectors.toList());
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(surnameNamePatronymic, yearOfBirth) VALUES(?, ?)",
                person.getSurnameNamePatronymic(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET surnameNamePatronymic = ?, yearOfBirth = ? WHERE id = ?",
                person.getSurnameNamePatronymic(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id = ?", id);
    }
}
