package usa.badratdinova.bookexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findBySurnameNamePatronymic(String surnameNamePatronymic);

    Optional<Person> findByEmail(String email);

    @Query("select b from Book b left join fetch b.person p where p.id = ?1")
    List<Book> findBooksByPersonId(int id);
}
