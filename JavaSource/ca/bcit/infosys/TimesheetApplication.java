package ca.bcit.infosys;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetController;

@Named
@SessionScoped
public class TimesheetApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private TimesheetController timesheetController;

    @Inject private Timesheet currentTimesheet;

    private Date startWeek;
    private Date endWeek;
    private int weekNumber;

    private List<Timesheet> history;

    public List<Timesheet> getHistory(Employee employee) throws Exception {
        history = refreshHistory(employee);
        return history;
    }

    public void setHistory(List<Timesheet> history) {
        this.history = history;
    }

    public Date getStartWeek() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.MONDAY - currentDay - 6;
        c.add(Calendar.DATE, leftDays);
        startWeek = new Date(c.getTime().getTime());
        return startWeek;
    }

    public void setStartWeek(Date startWeek) {
        this.startWeek = startWeek;
    }

    public Date getEndWeek() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        endWeek = new Date(c.getTime().getTime());
        return endWeek;
    }

    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }

    public int getWeekNumber() {
        Calendar c= new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        c.setTime(new Date(c.getTime().getTime()));
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        weekNumber = c.get(Calendar.WEEK_OF_YEAR);
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    private List<Timesheet> refreshHistory(Employee employee) {
        return timesheetController.getTimesheetByEmployee(employee);
    }
}
