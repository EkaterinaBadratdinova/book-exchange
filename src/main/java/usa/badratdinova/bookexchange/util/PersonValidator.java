package usa.badratdinova.bookexchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.services.PeopleService;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Person.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        Person personFromDatabase = peopleService.findOne(person.getSurnameNamePatronymic());
        if (personFromDatabase != null && personFromDatabase.getId() != person.getId()) {
            errors.rejectValue("surnameNamePatronymic", "", "Person with this name already exists");
        }
        if (personFromDatabase == null) {
        }
    }
}
