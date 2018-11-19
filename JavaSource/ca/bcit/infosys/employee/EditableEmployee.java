package ca.bcit.infosys.employee;

/**
 * Employee which has fields indicating it is editable or to be deleted.
 * 
 * @author Cameron
 * @version 1.0
 */
public class EditableEmployee {

    private boolean editable;

    private Employee employee;

    /**
     * Constructor that sets employee member.
     * @param employee to be set
     */
    public EditableEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * Checks if employee is editable.
     * @return true if employee is editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets editable value.
     * @param editable true if editable
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Returns employee object.
     * @return employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Sets the employee.
     * @param employee new employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
