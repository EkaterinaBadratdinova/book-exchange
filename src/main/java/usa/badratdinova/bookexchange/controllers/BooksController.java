package usa.badratdinova.bookexchange.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import usa.badratdinova.bookexchange.models.Book;
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.services.BooksService;
import usa.badratdinova.bookexchange.services.PeopleService;
import usa.badratdinova.bookexchange.util.BookValidator;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;
    private BookValidator bookValidator;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService, BookValidator bookValidator) {
        this.booksService = booksService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    //    With pagination and sorting
    @GetMapping("/pages")
    public String indexPage(@RequestParam int page,
                            @RequestParam int booksPerPage,
                            @RequestParam(required = false) Boolean sortByTitle,
                            Model model) {
        Pageable pageable;
        if (Boolean.TRUE.equals(sortByTitle)) {
            pageable = PageRequest.of(page, booksPerPage, Sort.by("title"));
        } else {
            pageable = PageRequest.of(page, booksPerPage);
        }
        Page<Book> booksPage = booksService.findAll(pageable);
        model.addAttribute("booksPage", booksPage);
        model.addAttribute("currentPage", page);
        return "books/indexPage";
    }

    //    Without pagination
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksService.findAll());
        return "books/index";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String title,
                         @RequestParam(required = false) String searched,
                         Model model) {
        List<Book> books = Collections.emptyList();
        if (title != null && !title.isEmpty()) {
            books = booksService.findBooksByTitle(title);
        }
        model.addAttribute("books", books);
        return "books/search";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model,
                       @ModelAttribute("personToBeChosen") Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("person", booksService.findPersonByBookId(id));
        model.addAttribute("people", peopleService.findAll());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("{id}/issue")
    public String issue(@PathVariable("id") int id, @ModelAttribute("personToBeChosen") Person person) {
        booksService.issue(id, person);
        return "redirect:/books";
    }

    @PatchMapping("{id}/return")
    public String returnBook(@PathVariable("id") int id) {
        booksService.returnBook(id);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
}
