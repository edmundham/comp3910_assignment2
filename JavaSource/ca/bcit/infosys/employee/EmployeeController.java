package ca.bcit.infosys.employee;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Dependent
@Stateless
public class EmployeeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName="assignment2") EntityManager em;

    public Employee findEmployeeById(long id) {
        return em.find(Employee.class, id);
    }

    public void persist(Employee employee) {
        em.persist(employee);
    }

    public void merge(Employee employee) {
        em.merge(employee);
    }

    public void remove(Employee employee) {
        employee = findEmployeeById(employee.getEmployeeId());
        em.remove(employee);
    }

    public void add(Employee employee) {
        em.persist(employee);
    }

    @SuppressWarnings("unchecked")
    public List<Employee> getAll() {
        Query query = em.createNativeQuery("select * from Employee order by employeeid", Employee.class);
        return query.getResultList();
    }

    public Employee getAdministrator() {
        Query query = em.createNativeQuery("select * from Employee where isadmin=1 limit 1", Employee.class);
        return (Employee) query.getSingleResult();
    }

    public Employee login(String userName, String password) {
        Query query = em.createNativeQuery("select * from Employee where username=:username and password=:password", Employee.class);
        query.setParameter("username", userName);
        query.setParameter("password", password);
        return (Employee) query.getSingleResult();
    }

}
