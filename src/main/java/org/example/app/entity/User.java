package org.example.app.entity;


public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public User(Long id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public User(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder user = new StringBuilder("User{");
        boolean isFirst = true; // Flag to manage comma placement

        if (this.id != null && this.id != -1) {
            user.append("id=").append(this.id);
            isFirst = false;
        }
        if (this.firstName != null && !this.firstName.isEmpty()) {
            if (!isFirst) user.append(", ");
            user.append("firstName='").append(this.firstName).append("'");
            isFirst = false;
        }
        if (this.lastName != null && !this.lastName.isEmpty()) {
            if (!isFirst) user.append(", ");
            user.append("lastName='").append(this.lastName).append("'");
            isFirst = false;
        }
        if (this.email != null && !this.email.isEmpty()) {
            if (!isFirst) user.append(", ");
            user.append("email='").append(this.email).append("'");
            isFirst = false;
        }
        if (this.phone != null && !this.phone.isEmpty()) {
            if (!isFirst) user.append(", ");
            user.append("phone='").append(this.phone).append("'");
        }

        user.append("}");
        return user.toString();
    }
}
