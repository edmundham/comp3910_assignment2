package ca.bcit.infosys.timesheet;

public class EditableTimesheet {

    private boolean editable = false;

    private Timesheet timesheet;

    public EditableTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }
}
