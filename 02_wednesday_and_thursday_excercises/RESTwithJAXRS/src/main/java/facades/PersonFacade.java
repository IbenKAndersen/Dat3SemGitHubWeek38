package facades;

import entities.Person;
import exceptions.PersonNotFoundException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public Person addPerson(String fName, String lName, String phone) {
        if (fName != null && !fName.isEmpty() && lName != null && !lName.isEmpty() && phone != null && !phone.isEmpty()) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                Person person = new Person(fName, lName, phone);
                em.persist(person);
                em.getTransaction().commit();
                return person;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting Person: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

    @Override
    public Person deletePerson(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.remove(person);
            em.getTransaction().commit();
            return person;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Could not delete, provided id does not exist");
        } finally {
            em.close();
        }

    }

    @Override
    public Person getPerson(int id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, id);
            em.getTransaction().commit();
            if (person != null) {
                return person;
            } else {
                throw new PersonNotFoundException("No person with provided id found");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public List<Person> getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Person> people = em.createNamedQuery("Person.getAll").getResultList();
            em.getTransaction().commit();
            return people;
        } finally {
            em.close();
        }
    }

    @Override
    public Person editPerson(Person p) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return p;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("Something went wrong when editing Person: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
