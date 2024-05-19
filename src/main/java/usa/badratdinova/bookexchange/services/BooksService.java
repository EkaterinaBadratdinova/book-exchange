package usa.badratdinova.bookexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }

    public Book findOne(String title) {
        Optional<Book> foundBook = booksRepository.findByTitle(title);
        return foundBook.orElse(null);
    }

    public Person findPersonByBookId(int id) {
        Optional<Person> foundPerson = booksRepository.findPersonByBookId(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void issue(int id, Person person) {
        Optional<Book> issuedBook = booksRepository.findById(id);
        issuedBook.get().setPerson(person);
        booksRepository.save(issuedBook.get());
    }

    @Transactional
    public void returnBook(int id) {
        Optional<Book> returnedBook = booksRepository.findById(id);
        returnedBook.get().setPerson(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
