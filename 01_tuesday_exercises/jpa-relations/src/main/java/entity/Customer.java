package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ibenk
 */
@Entity
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    
    @ManyToMany //bidirectional
    private List<Address> addresses = new ArrayList();

    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void addAddress(Address address) {
        addresses.add(address);
    }
    
    
//    @OneToMany(mappedBy = "customer") //bidirectional
//    private List<Address> addresses = new ArrayList();
    
    
//    @OneToMany(cascade = {CascadeType.PERSIST}) //unidirectional
//    @JoinColumn //så der ikke kommer join table ved unidirectional
//    List<Address> addresses = new ArrayList();
//    
//    public void addAddress(Address address) {
//        addresses.add(address);
//    }
//
//    public List<Address> getAddresses() {
//        return addresses;
//    }
    

//    @OneToOne(cascade = {CascadeType.PERSIST})//when you persist two objects at a time
//    private Address address;
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//        this.address.setCustomer(this);
//    }

    public Customer() {
    }

    public Customer(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + id + " ]";
    }
    
}
