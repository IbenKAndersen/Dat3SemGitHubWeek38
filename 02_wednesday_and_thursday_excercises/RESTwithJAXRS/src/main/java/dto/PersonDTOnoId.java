/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;

/**
 *
 * @author Malte
 */
public class PersonDTOnoId {

    private String fName;
    private String lName;
    private String phone;

    public PersonDTOnoId(String fName, String lName, String phone) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }

    public PersonDTOnoId(Person person) {
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.phone = person.getPhone();
    }

    public PersonDTOnoId() {
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
