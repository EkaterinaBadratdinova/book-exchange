package usa.badratdinova.bookexchange.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",
            message="The field should have the format: Surname Name Patronymic")
    @Column(name = "surnamenamepatronymic")
    private String surnameNamePatronymic;

    @Min(value = 1900, message="Year of birth cannot be earlier than 1900")
    @Max(value = 2024, message = "Year of birth cannot be later than 2024")
    @Column(name = "yearofbirth")
    private int yearOfBirth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    public Person() {

    }

    public Person(String surnameNamePatronymic, int yearOfBirth, List<Book> books) {
        this.surnameNamePatronymic = surnameNamePatronymic;
        this.yearOfBirth = yearOfBirth;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurnameNamePatronymic() {
        return surnameNamePatronymic;
    }

    public void setSurnameNamePatronymic(String surnameNamePatronymic) {
        this.surnameNamePatronymic = surnameNamePatronymic;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && yearOfBirth == person.yearOfBirth && Objects.equals(surnameNamePatronymic, person.surnameNamePatronymic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surnameNamePatronymic, yearOfBirth);
    }
}
