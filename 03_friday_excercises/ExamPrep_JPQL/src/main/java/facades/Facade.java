package facades;

import entities.Semester;
import entities.Student;
import entities.Teacher;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author ibenk
 */
public class Facade {

    private static Facade instance;
    private static EntityManagerFactory emf;

    private Facade() {
    }

    public static Facade getFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //Exercise C1
    public List<Student> findAllStudents() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            List<Student> students = em.createNamedQuery("Student.findAll").getResultList();
            em.getTransaction().commit();
            return students;
        } finally {
            em.close();
        }
    }

    //Exercise C2
    public List<Student> findAllStudentsNamedAnders() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Student> Anders = em.createQuery("SELECT s FROM Student s WHERE s.firstname = 'Anders'").getResultList();
            return Anders;
        } finally {
            em.close();
        }
    }

    //Exercise C3
    public Student newStudent(Student student) {
        if (student != null) {
            EntityManager em = getEntityManager();
            try {
                em.getTransaction().begin();
                em.persist(student);
                em.getTransaction().commit();
                return student;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Something went wrong when persisting Student: " + e.getMessage());
            } finally {
                em.close();
            }
        } else {
            throw new IllegalArgumentException("Wrong input. Try again.");
        }
    }

    //Exercise C4
    public void assignStudent() {

    }

    //Exercise C6
    public List<Student> findAllStudentsWithLastNameAnd() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Student> studentsWithAnd = em.createQuery("SELECT s FROM Student s WHERE s.lastname LIKE 'And%'").getResultList();
            return studentsWithAnd;
        } finally {
            em.close();
        }
    }

    //Exercise C7
    public int countAllStudents() {
        EntityManager em = emf.createEntityManager();
        try {
            long count = (int) em.createQuery("SELECT COUNT(s) FROM Student s").getSingleResult();
            return (int) count;
        } finally {
            em.close();
        }
    }

    //Exercise C8
    public int countStudentsInSemester(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            long count = (int) em.createQuery("SELECT COUNT(s) FROM Student s WHERE s.semester = :name").setParameter("name", name).getSingleResult();
            return (int) count;
        } finally {
            em.close();
        }
    }

    //Exercise C9
    public void countStudentsInEachSemester() {

    }

    //Exercise C10
    public List<Student> teachersWhoTeachMost() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Student> teachers = em.createQuery("SELECT t FROM Teacher t WHERE t.id = (SELECT max(s.id) FROM Teacher t JOIN t.semesterCollection s)").getResultList();
            return teachers;
        } finally {
            em.close();
        }
    }


}
