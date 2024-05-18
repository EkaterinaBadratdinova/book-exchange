package usa.badratdinova.bookexchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import usa.badratdinova.bookexchange.dao.PersonDAO;
import usa.badratdinova.bookexchange.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Person.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Optional<Person> optionalPersonFromDatabase = personDAO.show(person.getSurnameNamePatronymic());
        if (optionalPersonFromDatabase.isPresent() && optionalPersonFromDatabase.get().getId() != person.getId()) {
            errors.rejectValue("surnameNamePatronymic", "", "Person with this name already exists");
        }
        if (optionalPersonFromDatabase.isEmpty()) {
        }
    }
}
