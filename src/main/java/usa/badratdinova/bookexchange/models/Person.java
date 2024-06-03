package usa.badratdinova.bookexchange.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "^[A-Z][a-z]+$",
            message="The field should have the format: Firstname")
    @Size(min = 1, max = 20)
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "^[A-Z][a-z]+$",
            message="The field should have the format: Lastname")
    @Size(min = 1, max = 20)
    @Column(name = "last_name")
    private String lastName;

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

    @NotNull(message = "This field cannot be empty")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Past(message = "The date of birth must be in the past")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "This field cannot be empty")
    @Email
    @Column(name = "email")
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
    private List<Book> books;

    public Person() {

    }

    public Person(String firstName, String lastName, String username, String password, LocalDate dateOfBirth, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
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
        return Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(username, person.username) &&
                Objects.equals(password, person.password) &&
                Objects.equals(dateOfBirth, person.dateOfBirth) &&
                Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, dateOfBirth, email);
    }
}
