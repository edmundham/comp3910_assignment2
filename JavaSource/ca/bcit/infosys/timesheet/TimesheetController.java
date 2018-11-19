package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ca.bcit.infosys.employee.Employee;

/**
 * TimesheetController connects timesheet model with database.
 * @author Sunguk Ham
 * @version 1.0
 */
@Dependent
@Stateless
public class TimesheetController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * entity manager that connects to database.
     */
    @PersistenceContext(unitName = "assignment2") private EntityManager em;

    /**
     * find timesheet by id.
     * @param id timesheet id
     * @return timesheet by timesheet id
     */
    public Timesheet findTimesheetById(long id) {
        return em.find(Timesheet.class, id);
    }

    /**
     * persist timesheet.
     * @param timesheet to be persisted
     */
    public void persist(Timesheet timesheet) {
        em.persist(timesheet);
    }

    /**
     * merge timesheet.
     * @param timesheet to be merged
     */
    public void merge(Timesheet timesheet) {
        em.merge(timesheet);
    }

    /**
     * remove timesheet.
     * @param timesheet to be removed
     */
    public void remove(Timesheet timesheet) {
        timesheet = findTimesheetById(timesheet.getTimesheetId());
        em.remove(timesheet);
    }

    /**
     * add timesheet.
     * @param timesheet to be added
     */
    public void add(Timesheet timesheet) {
        em.persist(timesheet);
    }

    /**
     * get all timesheets.
     * @return all timesheets
     */
    @SuppressWarnings("unchecked")
    public List<Timesheet> getAll() {
        Query query = em.createNativeQuery(
                "select * from Timesheet order by TimesheetId",
                Timesheet.class);
        return query.getResultList();
    }

    /**
     * get timesheet by employee.
     * @param employee one of the employees
     * @return list of timesheets of the employee
     */
    @SuppressWarnings("unchecked")
    public List<Timesheet> getTimesheetByEmployee(Employee employee) {
        Query query = em.createNativeQuery(
                "select * from Timesheet where employeeid=:employeeid"
                + "order by startweek asc", Timesheet.class);
        query.setParameter(":employeeid", employee.getEmployeeId());
        return query.getResultList();
    }

    /**
     * get current timesheet.
     * @param employee current employee
     * @param startWeek start of the week
     * @param endWeek end of the week
     * @return current timesheet
     */
    public Timesheet getCurrentTimesheet(
            Employee employee, Date startWeek, Date endWeek) {
        Query query = em.createNativeQuery(
                "select * from Timesheet where employeeid=:employeeid"
                + " and startWeek=:startweek limit 1", Timesheet.class);
        query.setParameter("employeeid", employee.getEmployeeId());
        query.setParameter("startweek", startWeek);
        Timesheet timesheet = null;
        try {
            timesheet = (Timesheet) query.getSingleResult();
        } catch (NoResultException e) {

        }
        if (timesheet == null) {
            timesheet = new Timesheet();
            timesheet.setEmployeeId(employee.getEmployeeId());
            timesheet.setStartWeek(startWeek);
            timesheet.setEndWeek(endWeek);
        }
        return timesheet;
    }

    /**
     * get all timesheets by employee.
     * @param employee one of the employees
     * @return list of timesheets
     */
    @SuppressWarnings("unchecked")
    public List<Timesheet> getAllTimesheetsByEmployee(Employee employee) {
        Query query = em.createNativeQuery(
                "select * from Timesheet where employeeid=:employeeid"
                + " order by startweek desc",
                Timesheet.class);
        query.setParameter("employeeid", employee.getEmployeeId());
        return query.getResultList();
    }


}
