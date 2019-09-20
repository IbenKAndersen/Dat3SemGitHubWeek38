package test;

import entity.Customer2;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ibenk
 */
public class Tester {
    
    public static void main(String[] args) {
        Customer2 cust = new Customer2("John","Smith");
        cust.addHobby("Tennis");
        cust.addHobby("Beer");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
        em = emf.createEntityManager();
        Customer2 found = em.find(Customer2.class, cust.getId());
        System.out.println("Hobbies --->" + found.getHobbies());
    }
    
}
