package usa.badratdinova.bookexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);

    @Query("select p from Person p left join fetch p.books b where b.id = ?1")
    Optional<Person> findPersonByBookId(Long id);

    @Query("select b from Book b left join fetch b.person where b.title like :title%")
    List<Book> findBooksByTitle(@Param("title") String title);
}
