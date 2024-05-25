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

    @Query("select p from Person p left join fetch p.books")
    List<Person> findAllPeopleAndTheirBooks();

    @Query("select p from Person p left join fetch p.books where p.surnameNamePatronymic like :name%")
    List<Person> findPeopleByName(String name);
}
