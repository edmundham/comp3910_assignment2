package ca.bcit.infosys;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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

    public Employee currentEmployee;
    private List<EditableEmployee> list;
    private Employee employeeToBeChanged = null;

    public List<EditableEmployee> getList() throws Exception {
        if (currentEmployee.isAdmin()) {
            list = refreshList();
        } else {
            throw new Exception("Current employee is not admin");
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

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public Employee getEmployeeToBeChanged() {
        return employeeToBeChanged;
    }

    public void setEmployeeToBeChanged(Employee employeeToBeChanged) {
        this.employeeToBeChanged = employeeToBeChanged;
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

    public String addNewUser(String name, String username, String password) {
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        Employee addedEmployee = new Employee();
        addedEmployee.setName(name);
        addedEmployee.setUserName(username);
        addedEmployee.setPassword(password);
        addedEmployee.setCreatedDate(new Date(Calendar.getInstance().getTime().getTime()));
        employeeController.add(addedEmployee);
        list.add(new EditableEmployee(addedEmployee));
        return "";
    }

    public String login(String userName, String password) {
        currentEmployee = employeeController.login(userName, password);
        System.out.println(currentEmployee.isAdmin());
        return currentEmployee.isAdmin() ? "admin" : "success";
    }

    public String logout() {
        currentEmployee = null;
        return "logoutSuccess";
    }

    public String editPasswordButton(Employee employee) {
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        employeeToBeChanged = employee;
        return "edit";
    }

    public String editOneUserPassword() {
        employeeController.merge(employeeToBeChanged);
        return "adminSuccess";
    }

    public String deleteEmployee(EditableEmployee employee) {
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        employeeController.remove(employee.getEmployee());
        list.remove(employee);
        return null;
    }

    public String updateEmployee(String oldPassword, String password) {
        if (!currentEmployee.getPassword().equals(oldPassword)) {
            return null;
        }
        employeeToBeChanged = currentEmployee;
        employeeToBeChanged.setPassword(password);
        employeeController.merge(employeeToBeChanged);
        return "landing";
    }

}
