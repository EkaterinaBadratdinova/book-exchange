package usa.badratdinova.bookexchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.services.BooksService;

@Component
public class BookValidator implements Validator {

    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> oClass) {
        return Book.class.equals(oClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        Book bookFromDatabase = booksService.findOne(book.getTitle());
        if (bookFromDatabase != null && bookFromDatabase.getId() != book.getId()) {
            errors.rejectValue("title", "", "Book with this title already exists");
        }
        if (bookFromDatabase == null) {
        }
    }
}
