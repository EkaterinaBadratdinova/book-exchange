package usa.badratdinova.bookexchange.models;

import jakarta.validation.constraints.*;

public class Person {

    private int id;

    @NotEmpty(message="This field cannot be empty")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+",
            message="The field should have the format: Surname Name Patronymic")
    private String surnameNamePatronymic;

    @Min(value = 1900, message="Year of birth cannot be earlier than 1900")
    @Max(value = 2024, message = "Year of birth cannot be later than 2024")
    private int yearOfBirth;

    public Person() {

    }

    public Person(int id, String surnameNamePatronymic, int yearOfBirth) {
        this.id = id;
        this.surnameNamePatronymic = surnameNamePatronymic;
        this.yearOfBirth = yearOfBirth;
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
}
