package usa.badratdinova.bookexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.books")
    List<Person> findAllPeopleAndTheirBooks();

    @Query("SELECT p FROM Person p WHERE p.firstName LIKE CONCAT(:name, '%') OR p.lastName LIKE CONCAT(:name, '%')")
    List<Person> findByFirstNameStartingWithOrLastNameStartingWith(@Param("name") String name);
}
