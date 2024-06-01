package usa.badratdinova.bookexchange.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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
    @Column(name = "surname_name_patronymic")
    private String surnameNamePatronymic;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "^[A-Za-z0-9]{6,}$",
            message="This field must contain at least 6 characters, including Latin letters and digits")
    @Size(min = 6, max = 25)
    @Column(name = "username")
    private String username;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[/\\*\\!\\?@%$&:;])[A-Za-z\\d/\\*\\!\\?@%$&:;]{6,}$",
            message="The field must contain at least 6 characters, including 1 uppercase letter, 1 lowercase letter, 1 digit, and 1 symbol from the set /*!?@%$&:;")
    @Size(min = 6, max = 25)
    @Column(name = "password")
    private String password;

    @Column(name = "date_of_birth")
    @NotNull(message = "This field cannot be empty")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirth;

    @Column(name = "email")
    @NotEmpty(message = "This field cannot be empty")
    @Email
    private String email;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person() {

    }

    public Person(String surnameNamePatronymic, String username, String password, Date dateOfBirth, String email) {
        this.surnameNamePatronymic = surnameNamePatronymic;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        return id == person.id &&
                Objects.equals(surnameNamePatronymic, person.surnameNamePatronymic) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surnameNamePatronymic, dateOfBirth, email);
    }
}
