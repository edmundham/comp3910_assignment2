package ca.bcit.infosys;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeController;
import ca.bcit.infosys.timesheet.Timesheet;
import ca.bcit.infosys.timesheet.TimesheetController;
import ca.bcit.infosys.timesheet.TimesheetRow;
import ca.bcit.infosys.timesheet.TimesheetRowController;

/**
 * Helper class for all pages containing a timesheet object.
 *
 * @author Cameron
 * @version 1.0
 */
@Named
@SessionScoped
public class TimesheetApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DAYS_LEFT_VARIABLE = 6;

    private static final int MIN_ROWS = 5;

    private static final int DAY_HOURS = 24;

    @Inject
    private TimesheetController timesheetController;
    @Inject
    private TimesheetRowController timesheetRowController;
    @Inject
    private EmployeeController employeeController;

    private Employee currentEmployee;
    private Date startWeek = getStartWeek();
    private Date endWeek = getEndWeek();

    private boolean toBeDeleted;
    private boolean toBeAdded;

    private List<TimesheetRow> details = new ArrayList<>();

    private Timesheet currentTimesheet;
    private int weekNumber;

    private List<Timesheet> history;

    /**
     * No arguments constructor.
     */
    public TimesheetApplication() {
    }

    /**
     * Initializes the history list which contains Timesheet objects.
     *
     * @param employee
     *            whose history you are grabbing
     * @return list containing all historical timesheets for an employee
     * @throws Exception
     *             may throw Persistence exceptions
     */
    public List<Timesheet> getHistory(Employee employee) throws Exception {
        history = refreshHistory(employee);
        return history;
    }

    /**
     * Sets the history timesheet list.
     *
     * @param history
     *            new timesheet list
     */
    public void setHistory(List<Timesheet> history) {
        this.history = history;
    }

    /**
     * Gets the start of the current week.
     *
     * @return start of the current week
     */
    public Date getStartWeek() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay - DAYS_LEFT_VARIABLE;
        c.add(Calendar.DATE, leftDays);
        startWeek = new Date(c.getTime().getTime());
        return startWeek;
    }

    /**
     * Sets the week start date.
     *
     * @param startWeek
     *            new date
     */
    public void setStartWeek(Date startWeek) {
        this.startWeek = startWeek;
    }

    /**
     * Gets the last day in the current week.
     *
     * @return last day of current week.
     */
    public Date getEndWeek() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        endWeek = new Date(c.getTime().getTime());
        return endWeek;
    }

    /**
     * Sets the end date of the week.
     *
     * @param endWeek
     *            new week end date
     */
    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }

    /**
     * Gets the current week in the current year.
     * @return the int value of the current week
     */
    public int getWeekNumber() {
        Calendar c = new GregorianCalendar();
        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int leftDays = Calendar.FRIDAY - currentDay;
        c.add(Calendar.DATE, leftDays);
        c.setTime(new Date(c.getTime().getTime()));
        c.setFirstDayOfWeek(Calendar.SATURDAY);
        weekNumber = c.get(Calendar.WEEK_OF_YEAR);
        return weekNumber;
    }

    /**
     * Sets the current week number.
     * @param weekNumber new week number
     */
    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    /**
     * Gets the current timesheet.
     * @return current timesheet
     */
    public Timesheet getCurrentTimesheet() {
        return currentTimesheet;
    }

    /**
     * Sets the current timesheet.
     * @param currentTimesheet new timesheet.
     */
    public void setCurrentTimesheet(Timesheet currentTimesheet) {
        this.currentTimesheet = currentTimesheet;
    }

    /**
     * Initializes details list which contains timesheet rows. If a timesheet
     * already exists it will return that list of timesheet rows.
     * @return list of timesheet rows.
     */
    public List<TimesheetRow> getDetails() {
        if (!toBeAdded && !toBeDeleted) {
            Timesheet tempTimesheet = timesheetController.getCurrentTimesheet(
                    currentEmployee, startWeek, endWeek);
            if (tempTimesheet.getTimesheetId() != null) {
                details = timesheetRowController.getRowsByCurrentTimesheetId(
                        tempTimesheet.getTimesheetId());
            }
            if (details.size() == 0) {
                for (int i = 0; i < MIN_ROWS; i++) {
                    details.add(new TimesheetRow());
                }
            }
        }
        return details;
    }

    /**
     * Sets a new details list.
     * @param details new list of timesheet rows
     */
    public void setDetails(List<TimesheetRow> details) {
        this.details = details;
    }

    /**
     * Refreshes the history list of timesheets from the database.
     * @param employee history to be found
     * @return list of timesheets
     */
    private List<Timesheet> refreshHistory(Employee employee) {
        return timesheetController.getTimesheetByEmployee(employee);
    }

    /**
     * Returns the current employee.
     * @return the current employee
     */
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * Sets the current employee using username and password.
     * @param username accepts a string
     * @param password accepts a string
     */
    public void setCurrentEmployee(String username, String password) {
        this.currentEmployee = employeeController.login(username, password);
    }

    /**
     * Calculates the sum of the entire timesheet.
     * @param timesheet to be calculated
     * @return the sum of all days
     */
    public int getSumOfTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows = timesheetRowController
                .getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getMonday() + detail.getTuesday()
            + detail.getWednesday() + detail.getThursday()
                    + detail.getFriday() + detail.getSaturday()
                    + detail.getSunday();
        }
        return sum;
    }

    /**
     * Calculates the sum of a single row.
     * @param detail the row to be summed
     * @return sum of entire row
     */
    public int getSum(TimesheetRow detail) {
        return detail.getMonday() + detail.getTuesday()
        + detail.getWednesday() + detail.getThursday()
                + detail.getFriday() + detail.getSaturday()
                + detail.getSunday();
    }

    /**
     * Returns the total hours in the details list.
     * @return sum of the details timesheetrow list
     */
    public int getTotalHours() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getMonday() + detail.getTuesday()
            + detail.getWednesday() + detail.getThursday()
                    + detail.getFriday() + detail.getSaturday()
                    + detail.getSunday();
        }
        return sum;
    }

    /**
     * Gets the sum of the monday col.
     * @return sum of monday
     */
    public int getMondaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getMonday();
        }
        return sum;
    }

    /**
     * Gets the sum of the tuesday col.
     * @return sum of tuesday
     */
    public int getTuesdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getTuesday();
        }
        return sum;
    }

    /**
     * Gets the sum of the wednesday col.
     * @return sum of wednesday
     */
    public int getWednesdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getWednesday();
        }
        return sum;
    }

    /**
     * Gets the sum of the thursday col.
     * @return sum of thursday
     */
    public int getThursdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getThursday();
        }
        return sum;
    }

    /**
     * Gets the sum of the friday col.
     * @return sum of friday
     */
    public int getFridaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getFriday();
        }
        return sum;
    }

    /**
     * Gets the sum of the saturday col.
     * @return sum of saturday
     */
    public int getSaturdaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getSaturday();
        }
        return sum;
    }

    /**
     * Gets the sum of the sunday col.
     * @return sum of sunday
     */
    public int getSundaySum() {
        int sum = 0;
        for (TimesheetRow detail : details) {
            sum += detail.getSunday();
        }
        return sum;
    }

    /**
     * Gets the sum of monday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getMondaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getMonday();
        }
        return sum;
    }

    /**
     * Gets the sum of tuesday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getTuesdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getTuesday();
        }
        return sum;
    }

    /**
     * Gets the sum of wednesday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getWednesdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getWednesday();
        }
        return sum;
    }

    /**
     * Gets the sum of thursday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getThursdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getThursday();
        }
        return sum;
    }

    /**
     * Gets the sum of friday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getFridaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getFriday();
        }
        return sum;
    }

    /**
     * Gets the sum of saturday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getSaturdaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getSaturday();
        }
        return sum;
    }

    /**
     * Gets the sum of sunday from the db.
     * @param timesheet to be queried
     * @return sum of that day
     */
    public int getSundaySumByTimesheet(Timesheet timesheet) {
        List<TimesheetRow> timesheetRows =
                timesheetRowController.getRowsByTimesheet(timesheet);
        int sum = 0;
        for (TimesheetRow detail : timesheetRows) {
            sum += detail.getSunday();
        }
        return sum;
    }

    /**
     * Deletes the current row from the timesheet.
     * @param detail row to be deleted
     * @return string for redirection
     */
    public String deleteRow(TimesheetRow detail) {
        if (details.size() == MIN_ROWS) {
            return null;
        }
        details.remove(detail);
        toBeDeleted = true;
        return null;
    }

    /**
     * Adds a timesheet row to the current sheet.
     * @return string for redirection
     */
    public String addRow() {
        details.add(new TimesheetRow());
        toBeAdded = true;
        return null;
    }

    /**
     * Saves the current timesheet to the current employee.
     * @param employee that the timesheet will be saved to
     * @return string for redirection
     */
    public String saveTimesheet(Employee employee) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle
                .getBundle("ca.bcit.infosys.messages",
                context.getViewRoot().getLocale());

        if (!validateTimesheetRows()) {
            context.addMessage(
                    null, new FacesMessage(bundle.getString("failed"),
                            bundle.getString("error_message_id_wp")));
            return null;
        }

        if (!validateTimesheetRowsHours()) {
            context.addMessage(
                    null, new FacesMessage(bundle.getString("failed"),
                            bundle.getString("error_message_day_hour")));
            return null;
        }

        currentTimesheet = timesheetController
                .getCurrentTimesheet(employee, startWeek, endWeek);
        if (currentTimesheet.getTimesheetId() == null) {
            timesheetController.add(currentTimesheet);
            currentTimesheet = timesheetController
                    .getCurrentTimesheet(employee, startWeek, endWeek);
        } else if (timesheetController.findTimesheetById(
                currentTimesheet.getTimesheetId()) != null) {
            timesheetController.merge(currentTimesheet);
        } else {
            timesheetController.add(currentTimesheet);
            currentTimesheet = timesheetController
                    .getCurrentTimesheet(employee, startWeek, endWeek);
        }
        timesheetRowController.deleteAllRowsByTimesheet(currentTimesheet);
        for (TimesheetRow detail : details) {
            detail.setTimesheetId(currentTimesheet.getTimesheetId());
            timesheetRowController.merge(detail);
        }
        details = timesheetRowController.getRowsByTimesheet(currentTimesheet);

        context.addMessage(
                null, new FacesMessage(bundle.getString("successful"),
                bundle.getString("saved_message_id_wp")));
        return null;
    }

    /**
     * validates timesheet row with work package
     * id and project id.
     * @return true if valid, false otherwise
     */
    private boolean validateTimesheetRows() {
        Map<String, Boolean> tempMap = new HashMap<>();
        for (TimesheetRow detail : details) {
            if (tempMap.get(detail.getWorkPackageId()
                    + detail.getProjectId()) != null) {
                return false;
            }
            if ((detail.getWorkPackageId() + detail.getProjectId())
                    .equals("0")) {
                continue;
            }
            tempMap.put(detail.getWorkPackageId()
                    + detail.getProjectId(), true);
        }
        return true;
    }

    /**
     * validate timesheet row hours.
     * @return true if valid, false otherwise
     */
    private boolean validateTimesheetRowsHours() {
        for (TimesheetRow detail : details) {
            if (detail.getMonday() > DAY_HOURS
                    || detail.getTuesday() > DAY_HOURS
                    || detail.getWednesday() > DAY_HOURS
                    || detail.getThursday() > DAY_HOURS
                    || detail.getFriday() > DAY_HOURS
                    || detail.getSaturday() > DAY_HOURS
                    || detail.getSunday() > DAY_HOURS) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a list of timesheets from the db for history list.
     * @param employee to be queried
     * @return list of timesheets
     */
    public List<Timesheet> getHistoryTimesheets(Employee employee) {
        return timesheetController.getAllTimesheetsByEmployee(employee);
    }

    /**
     * Gets a list of timesheet rows from the db for history.
     * @param timesheet to be queried
     * @return list of timesheet rows
     */
    public List<TimesheetRow> getHistoryTimesheetRows(Timesheet timesheet) {
        return timesheetRowController.getRowsByTimesheet(timesheet);
    }

}
