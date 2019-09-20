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
        return null;
    }
    
    //Exercise C2
    public List<Student> findAllStudentsNamedAnders() {
        return null;
    }
    
    //Exercise C3
    public Student newStudent() {
        return null;
    }
    
    //Exercise C4
    public void assignStudent() {
        
    }
    
    //Exercise C6
    public List<Student> findAllStudentsWithLastNameAnd() {
        return null; 
    }
    
    //Exercise C7
    public int countAllStudents() {
        return 0;
    }
    
    //Exercise C8
    public int countStudentsInSemester(String semester) {
        return 0;
    }
    
    //Exercise C9
    public void countStudentsInEachSemester() {
        
    }
    
    //Exercise C10
    public List<Teacher> teachersWhoTeachMost() {
        return null;
    }
    
}
