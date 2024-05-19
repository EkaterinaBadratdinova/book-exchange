package usa.badratdinova.bookexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.repositories.BooksRepository;
import usa.badratdinova.bookexchange.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    private final BooksRepository booksRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Person findOne(String surnameNamePatronymic) {
        Optional<Person> foundPerson = peopleRepository.findBySurnameNamePatronymic(surnameNamePatronymic);
        return foundPerson.orElse(null);
    }

    public List<Book> findBooksByPersonId(int id) {
        return peopleRepository.findBooksByPersonId(id);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        Optional<Person> personToDelete = peopleRepository.findById(id);
        if (personToDelete.isPresent() && personToDelete.get().getBooks() != null) {
            for (Book book : personToDelete.get().getBooks()) {
                book.setPerson(null);
                booksRepository.save(book);
            }
        }
        peopleRepository.deleteById(id);
    }
}
