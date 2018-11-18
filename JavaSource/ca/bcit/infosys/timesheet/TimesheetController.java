package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.infosys.employee.Employee;

@Dependent
@Stateless
public class TimesheetController implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName="assignment2") EntityManager em;

    public Timesheet findTimesheetById(long id) {
        return em.find(Timesheet.class, id);
    }

    public void persist(Timesheet timesheet) {
        em.persist(timesheet);
    }

    public void merge(Timesheet timesheet) {
        em.merge(timesheet);
    }

    public void remove(Timesheet timesheet) {
        timesheet = findTimesheetById(timesheet.getTimesheetId());
        em.remove(timesheet);
    }

    public void add(Timesheet timesheet) {
        em.persist(timesheet);
    }

    public List<Timesheet> getAll() {
        Query query = em.createNativeQuery("select * from Timesheet order by TimesheetId", Timesheet.class);
        return query.getResultList();
    }

    public List<Timesheet> getTimesheetByEmployee(Employee employee) {
        Query query = em.createNativeQuery("select * from Timesheet where employeeid=:employeeid"
                + "order by startweek asc", Timesheet.class);
        query.setParameter(":employeeid", employee.getEmployeeId());
        return query.getResultList();
    }

}
