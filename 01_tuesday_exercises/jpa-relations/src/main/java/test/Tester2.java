package test;

import entity.Address;
import entity.Customer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ibenk
 */
public class Tester2 {
    
    public static void main(String[] args) {        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Customer cust = new Customer("John","Smith");
        Address add1 = new Address("Lyngbyvej 23","Kongens Lyngby");
        Address add2 = new Address("Lyngbyvej 23","Kongens Lyngby");
        //cust.setAddress(add);
        //add.setCustomer(cust);
        //cust.addAddress(add1);

        
        try{
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        em = emf.createEntityManager();
        Customer found = em.find(Customer.class,cust.getId());
        //System.out.println("Address " + found.getAddress().getCity());
    }
    
}
