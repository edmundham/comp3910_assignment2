package ca.bcit.infosys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.EditableEmployee;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeController;

@Named
@SessionScoped
public class EmployeeApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private EmployeeController employeeController;

    @Inject private Employee toBeAddedEmployee;
    private List<EditableEmployee> list;

    public List<EditableEmployee> getList() {
        if (list == null) {
            list = refreshList();
        }
        return list;
    }

    private List<EditableEmployee> refreshList() {
        List<Employee> employees = employeeController.getAll();
        List<EditableEmployee> tempList = new ArrayList<>();
        for (Employee employee : employees) {
            tempList.add(new EditableEmployee(employee));
        }
        return tempList;
    }

    public void setList(List<EditableEmployee> editableEmployee) {
        list = editableEmployee;
    }

    public String deleteRow(EditableEmployee e) {
        employeeController.remove(e.getEmployee());
        list.remove(e);
        return "";
    }

    public String save() {
        for (EditableEmployee e : list) {
            if (e.isEditable()) {
                employeeController.merge(e.getEmployee());
                e.setEditable(false);
            }
        }
        return "";
    }

    public String add() {
        for (EditableEmployee e : list) {
            if (e.getEmployee().getEmployeeId() == toBeAddedEmployee.getEmployeeId()) {
                return null;
            }
        }
        employeeController.add(toBeAddedEmployee);
        list.add(new EditableEmployee(toBeAddedEmployee));
        toBeAddedEmployee = null;
        return "";
    }

}
