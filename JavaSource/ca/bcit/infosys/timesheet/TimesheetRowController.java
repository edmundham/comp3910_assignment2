package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class TimesheetRowController implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName="assignment2") EntityManager em;

    public TimesheetRow findTimesheetRowById(long id) {
        return em.find(TimesheetRow.class, id);
    }

    public void persist(TimesheetRow timesheetRow) {
        em.persist(timesheetRow);
    }

    public void merge(TimesheetRow timesheetRow) {
        em.merge(timesheetRow);
    }

    public void remove(TimesheetRow timesheetRow) {
        timesheetRow = findTimesheetRowById(timesheetRow.getTimesheetRowId());
        em.remove(timesheetRow);
    }

    public void add(TimesheetRow timesheetRow) {
        em.persist(timesheetRow);
    }

    public List<TimesheetRow> getAll() {
        Query query = em.createNativeQuery("select * from TimesheetRow order by TimesheetRowId", TimesheetRow.class);
        return query.getResultList();
    }

}
