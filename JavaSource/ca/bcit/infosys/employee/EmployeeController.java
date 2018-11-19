package ca.bcit.infosys.employee;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Handle CRUD operations for Employee Class.
 * @author Cameron
 * @version 1.0
 */
@Dependent
@Stateless
public class EmployeeController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Setting context for the persistence xml.
     */
    @PersistenceContext(unitName = "assignment2")
    private EntityManager em;

    /**
     * Finds employee via ID int he db.
     * @param id accepts a long
     * @return employee found
     */
    public Employee findEmployeeById(long id) {
        return em.find(Employee.class, id);
    }

    /**
     * Adds employee to the db.
     * @param employee accepts employee object
     */
    public void persist(Employee employee) {
        em.persist(employee);
    }

    /**
     * Updates employee in the db.
     * @param employee accepts employee object
     */
    public void merge(Employee employee) {
        em.merge(employee);
    }

    /**
     * Removes employee from the db.
     * @param employee accepts employee object
     */
    public void remove(Employee employee) {
        employee = findEmployeeById(employee.getEmployeeId());
        em.remove(employee);
    }

    /**
     * Adds employee to the db.
     * @param employee accepts employee object
     */
    public void add(Employee employee) {
        em.persist(employee);
    }

    /**
     * Gets all employees in a list.
     * @return list of all employees
     */
    @SuppressWarnings("unchecked")
    public List<Employee> getAll() {
        Query query = em.createNativeQuery(
                "select * from Employee order by employeeid", Employee.class);
        return query.getResultList();
    }

    /**
     * Returns the administrator.
     * @return administrator employee object
     */
    public Employee getAdministrator() {
        Query query = em.createNativeQuery("select * from Employee "
                + "where isadmin=1 limit 1", Employee.class);
        return (Employee) query.getSingleResult();
    }

    /**
     * Authenticates the user against all users.
     * @param userName accepts a string
     * @param password accepts a string
     * @return employee object.
     */
    public Employee login(String userName, String password) {
        Query query = em.createNativeQuery("select * from Employee "
                + "where username=:username and password=:password",
                Employee.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);
        return (Employee) query.getSingleResult();
    }

}
