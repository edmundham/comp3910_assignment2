package ca.bcit.infosys;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeController;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetController;
import ca.bcit.infosys.timesheet.TimesheetRow;
import ca.bcit.infosys.timesheet.TimesheetRowController;

@Named
@SessionScoped
public class TimesheetApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private TimesheetController timesheetController;
    @Inject private TimesheetRowController timesheetRowController;
    @Inject private EmployeeController employeeController;

    private Employee currentEmployee;
    private Date startWeek = getStartWeek();
    private Date endWeek = getEndWeek();

    private List<TimesheetRow> details = new ArrayList<>();

    private Timesheet currentTimesheet;
    private int weekNumber;

    private List<Timesheet> history;

    public TimesheetApplication() {
    }

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
        int leftDays = Calendar.FRIDAY - currentDay - 6;
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

    public Timesheet getCurrentTimesheet() {
        return currentTimesheet;
    }

    public void setCurrentTimesheet(Timesheet currentTimesheet) {
        this.currentTimesheet = currentTimesheet;
    }

    public List<TimesheetRow> getDetails() {
        Timesheet tempTimesheet = timesheetController.getCurrentTimesheet(currentEmployee, startWeek, endWeek);
        if (tempTimesheet.getTimesheetId() != null) {
            details = timesheetRowController.getRowsByCurrentTimesheetId(tempTimesheet.getTimesheetId());
        }
        if (details.size() == 0) {
            for (int i = 0; i < 5; i++) {
                details.add(new TimesheetRow());
            }
        }
        return details;
    }

    public void setDetails(List<TimesheetRow> details) {
        this.details = details;
    }

    private List<Timesheet> refreshHistory(Employee employee) {
        return timesheetController.getTimesheetByEmployee(employee);
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(String username, String password) {
        this.currentEmployee = employeeController.login(username, password);
    }

    public int getSumOfTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getMonday() + detail.getTuesday() + detail.getWednesday() +
                    detail.getThursday() + detail.getFriday() + detail.getSaturday() +
                    detail.getSunday();
        }
        return sum;
    }

    public int getSum(TimesheetRow detail) {
        return detail.getMonday() + detail.getTuesday() + detail.getWednesday() +
                detail.getThursday() + detail.getFriday() + detail.getSaturday() +
                detail.getSunday();
    }

    public int getTotalHours() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getMonday() + detail.getTuesday() + detail.getWednesday() +
                    detail.getThursday() + detail.getFriday() + detail.getSaturday() +
                    detail.getSunday();
        }
        return sum;
    }

    public int getMondaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getMonday();
        }
        return sum;
    }

    public int getTuesdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getTuesday();
        }
        return sum;
    }

    public int getWednesdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getWednesday();
        }
        return sum;
    }

    public int getThursdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getThursday();
        }
        return sum;
    }

    public int getFridaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getFriday();
        }
        return sum;
    }

    public int getSaturdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getSaturday();
        }
        return sum;
    }

    public int getSundaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getSunday();
        }
        return sum;
    }

    public int getMondaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getMonday();
        }
        return sum;
    }

    public int getTuesdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getTuesday();
        }
        return sum;
    }

    public int getWednesdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getWednesday();
        }
        return sum;
    }

    public int getThursdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getThursday();
        }
        return sum;
    }

    public int getFridaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getFriday();
        }
        return sum;
    }

    public int getSaturdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getSaturday();
        }
        return sum;
    }

    public int getSundaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getSunday();
        }
        return sum;
    }

    public String deleteRow(TimesheetRow detail) {
        if (details.size() == 5) {
            details.add(new TimesheetRow());
        }
        details.remove(detail);
        return null;
    }

    public String addRow() {
        details.add(new TimesheetRow());
        return null;
    }

    public String saveTimesheet(Employee employee) {
        currentTimesheet = timesheetController.getCurrentTimesheet(employee, startWeek, endWeek);
        if (currentTimesheet.getTimesheetId() == null) {
            timesheetController.add(currentTimesheet);
            currentTimesheet = timesheetController.getCurrentTimesheet(employee, startWeek, endWeek);
        } else if (timesheetController.findTimesheetById(currentTimesheet.getTimesheetId()) != null) {
            timesheetController.merge(currentTimesheet);
        } else {
            timesheetController.add(currentTimesheet);
            currentTimesheet = timesheetController.getCurrentTimesheet(employee, startWeek, endWeek);
        }
        for (TimesheetRow detail : details) {
            detail.setTimesheetId(currentTimesheet.getTimesheetId());
            timesheetRowController.merge(detail);
        }
        details = timesheetRowController.getRowsByTimesheet(currentTimesheet);
        return null;
    }

    public List<Timesheet> getHistoryTimesheets(Employee employee) {
        return timesheetController.getAllTimesheetsByEmployee(employee);
    }

    public List<TimesheetRow> getHistoryTimesheetRows(Timesheet timesheet) {
        return timesheetRowController.getRowsByTimesheet(timesheet);
    }

}
