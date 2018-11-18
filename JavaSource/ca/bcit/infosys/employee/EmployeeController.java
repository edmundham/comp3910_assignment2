package ca.bcit.infosys.employee;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public List<Employee> getAll() {
        Query query = em.createNativeQuery("select * from Employee order by employeeid", Employee.class);
        return query.getResultList();
    }
}
