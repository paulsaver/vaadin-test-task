package main;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String patronic;
    private String phone;

    public Patient() {
    }

    public Patient(String firstName, String lastName, String patronic, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronic = patronic;
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

    public String getPatronic() {
        return patronic;
    }

    public void setPatronic(String patronic) {
        this.patronic = patronic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
