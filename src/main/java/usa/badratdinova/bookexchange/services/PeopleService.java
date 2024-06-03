package usa.badratdinova.bookexchange.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.repositories.BooksRepository;
import usa.badratdinova.bookexchange.repositories.PeopleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
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

    public Page<Person> findAll(Pageable pageable) {
        return peopleRepository.findAll(pageable);
    }

    public List<Person> findAllPeopleAndTheirBooks() {
        return peopleRepository.findAllPeopleAndTheirBooks();
    }

    public List<Person> findPeopleByName(String name) {
        if (name == null || name.isEmpty()) {
            return Collections.emptyList();
        }
        return peopleRepository.findByFirstNameStartingWithOrLastNameStartingWith(name);
    }

    public Person findOne(Long id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public Person findByUsername(String username) {
        Optional<Person> foundPerson = peopleRepository.findByUsername(username);
        return foundPerson.orElse(null);
    }

    public Person findByEmail(String email) {
        Optional<Person> foundPerson = peopleRepository.findByEmail(email);
        return foundPerson.orElse(null);
    }

    public List<Book> findBooksByPersonId(Long id) {
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()) {
            person.get().getBooks().forEach(book -> {
                long daysBetween = ChronoUnit.DAYS.between(book.getIssuedAt(), LocalDate.now());
                if (daysBetween > 10) {
                    book.setIsOverdue(true);
                }
            });
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional
    public void save(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Long id, Person updatedPerson) {
        Optional<Person> optionalPersonFromDatabase = peopleRepository.findById(id);
        if (optionalPersonFromDatabase.isPresent()) {
            Person personToBeUpdated = optionalPersonFromDatabase.get();
            personToBeUpdated.setFirstName(updatedPerson.getFirstName());
            personToBeUpdated.setLastName(updatedPerson.getLastName());
            personToBeUpdated.setUsername(updatedPerson.getUsername());
            personToBeUpdated.setPassword(updatedPerson.getPassword());
            personToBeUpdated.setDateOfBirth(updatedPerson.getDateOfBirth());
            personToBeUpdated.setEmail(updatedPerson.getEmail());
            peopleRepository.save(personToBeUpdated);
        }
    }
    @Transactional
    public void delete(Long id) {
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
