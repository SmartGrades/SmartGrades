package kz.tech.smartgrades.auth.models;

public class ModelRepresentative {

    private String FirstName;
    private String LastName;
    private String Patronymic;
    private String Phone;
    private String Email;
    private String StateRegistration;

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPatronymic() {
        return Patronymic;
    }

    public void setPatronymic(String patronymic) {
        Patronymic = patronymic;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getStateRegistration() {
        return StateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        StateRegistration = stateRegistration;
    }
}
