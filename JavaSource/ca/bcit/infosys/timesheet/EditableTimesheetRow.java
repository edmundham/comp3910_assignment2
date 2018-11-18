package ca.bcit.infosys.timesheet;

public class EditableTimesheetRow {

    private boolean editable = false;

    private TimesheetRow timesheetRow;

    public EditableTimesheetRow(TimesheetRow timesheetRow) {
        this.timesheetRow = timesheetRow;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public TimesheetRow getTimesheetRow() {
        return timesheetRow;
    }

    public void setTimesheetRow(TimesheetRow timesheetRow) {
        this.timesheetRow = timesheetRow;
    }
}
