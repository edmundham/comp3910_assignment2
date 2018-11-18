package ca.bcit.infosys.employee;

public class EditableEmployee {

    private boolean editable = false;

    private Employee employee;

    public EditableEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
