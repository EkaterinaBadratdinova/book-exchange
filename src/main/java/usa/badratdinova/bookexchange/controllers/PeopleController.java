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
import usa.badratdinova.bookexchange.models.Person;
import usa.badratdinova.bookexchange.services.PeopleService;
import usa.badratdinova.bookexchange.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    //    With pagination and sorting
    @GetMapping("/pages")
    public String indexPage(@RequestParam int page,
                            @RequestParam int peoplePerPage,
                            @RequestParam(required = false) Boolean sortBySurname,
                            Model model) {
        Pageable pageable;
        if (Boolean.TRUE.equals(sortBySurname)) {
            pageable = PageRequest.of(page, peoplePerPage, Sort.by("surnameNamePatronymic"));
        } else {
            pageable = PageRequest.of(page,peoplePerPage);
        }
        Page<Person> peoplePage = peopleService.findAll(pageable);
        model.addAttribute("peoplePage", peoplePage);
        model.addAttribute("currentPage", page);
        return "people/indexPage";
    }

    //    Without pagination
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/info")
    public String allData(Model model) {
        model.addAttribute("people", peopleService.findAllPeopleAndTheirBooks());
        return "people/info";
    }

    @GetMapping("{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("books", peopleService.findBooksByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        peopleService.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }
}
