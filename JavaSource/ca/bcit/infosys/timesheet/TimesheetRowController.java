package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Timesheet row controller which connects to
 * database with timesheet row model class.
 * @author Sunguk Ham
 * @version 1.0
 */
@Dependent
@Stateless
public class TimesheetRowController implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Entity manager connects to database.
     */
    @PersistenceContext(unitName = "assignment2") private EntityManager em;

    /**
     * find timesheet row by id.
     * @param id timesheet row id
     * @return timesheet row object
     */
    public TimesheetRow findTimesheetRowById(long id) {
        return em.find(TimesheetRow.class, id);
    }

    /**
     * persist timesheet row.
     * @param timesheetRow object
     */
    public void persist(TimesheetRow timesheetRow) {
        em.persist(timesheetRow);
    }

    /**
     * merge timesheet row.
     * @param timesheetRow object
     */
    public void merge(TimesheetRow timesheetRow) {
        em.merge(timesheetRow);
    }

    /**
     * remove timesheet row.
     * @param timesheetRow object
     */
    public void remove(TimesheetRow timesheetRow) {
        timesheetRow = findTimesheetRowById(timesheetRow.getTimesheetRowId());
        em.remove(timesheetRow);
    }

    /**
     * add timesheet row.
     * @param timesheetRow object
     */
    public void add(TimesheetRow timesheetRow) {
        em.persist(timesheetRow);
    }

    /**
     * get all timesheet rows.
     * @return list of timesheet rows
     */
    @SuppressWarnings("unchecked")
    public List<TimesheetRow> getAll() {
        Query query = em.createNativeQuery(
                "select * from TimesheetRow order by TimesheetRowId",
                TimesheetRow.class);
        return query.getResultList();
    }

    /**
     * get rows by current timesheet id.
     * @param timesheetId current timesheet id
     * @return list of timesheet rows
     */
    @SuppressWarnings("unchecked")
    public List<TimesheetRow> getRowsByCurrentTimesheetId(long timesheetId) {
        Query query = em.createNativeQuery(
                "select * from TimesheetRow where timesheetid=:timesheetid",
                TimesheetRow.class);
        query.setParameter("timesheetid", timesheetId);
        return query.getResultList();
    }

    /**
     * get rows by timesheet.
     * @param timesheet object
     * @return list of timesheet rows
     */
    @SuppressWarnings("unchecked")
    public List<TimesheetRow> getRowsByTimesheet(Timesheet timesheet) {
        Query query = em.createNativeQuery(
                "select * from TimesheetRow where "
                + "timesheetid=:timesheetid order by timesheetrowid asc",
                TimesheetRow.class);
        query.setParameter("timesheetid", timesheet.getTimesheetId());
        return query.getResultList();
    }

    /**
     * delete all rows by timesheet.
     * @param timesheet object
     */
    public void deleteAllRowsByTimesheet(Timesheet timesheet) {
        Query query = em.createNativeQuery(
                "delete from TimesheetRow where timesheetid=:timesheetid",
                TimesheetRow.class);
        query.setParameter("timesheetid", timesheet.getTimesheetId());
        query.executeUpdate();
    }
}
