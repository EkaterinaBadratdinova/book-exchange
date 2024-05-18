package usa.badratdinova.bookexchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import usa.badratdinova.bookexchange.dao.BookDAO;
import usa.badratdinova.bookexchange.models.Book;

import java.util.Optional;

@Component
public class BookValidator implements Validator {
    private final BookDAO bookDAO;

    @Autowired
    public BookValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Book.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        Optional<Book> optionalBookFromDatabase = bookDAO.show(book.getTitle());
        if (optionalBookFromDatabase.isPresent() && optionalBookFromDatabase.get().getId() != book.getId()) {
            errors.rejectValue("title", "", "Book with this title already exists");
        }
        if (optionalBookFromDatabase.isEmpty()) {
        }
    }
}
