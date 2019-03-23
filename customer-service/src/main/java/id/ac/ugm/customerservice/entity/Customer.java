package id.ac.ugm.customerservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private  Long id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String address;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp dateofbirth;

    public Customer(final String username, final String firstname, final String lastname, final String email, final String address,
                    final Timestamp dateofbirth
    ) {
        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
        this.setEmail(email);
        this.setAddress(address);
        this.setDateofbirth(dateofbirth);
    }

    @SuppressWarnings("unused")
    private Customer() { } // JPA only

    public Customer(final String username) {
        this.setUsername(username);
    }

    public Customer(final String username, final String firstname, final String lastname) {
        this.setUsername(username);
        this.setFirstname(firstname);
        this.setLastname(lastname);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Timestamp dateofbirth) {
        this.dateofbirth = dateofbirth;
    }
}
