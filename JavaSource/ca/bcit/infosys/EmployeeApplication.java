package ca.bcit.infosys;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.employee.EditableEmployee;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeController;

/**
 * Helper class for all pages containing an Employee object.
 * 
 * @author Cameron
 * @version 1.0
 */
@Named
@SessionScoped
public class EmployeeApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private EmployeeController employeeController;

    private Employee currentEmployee;
    private List<EditableEmployee> list;
    private Employee employeeToBeChanged;

    /**
     * If the user is an admin it will initialize the list which
     * contains all employees.
     * @return list of editable employees
     * @throws Exception is user is not an admin
     */
    public List<EditableEmployee> getList() throws Exception {
        if (currentEmployee.isAdmin()) {
            list = refreshList();
        } else {
            throw new Exception("Current employee is not admin");
        }
        return list;
    }

    /**
     * Updates the editable employee list from the database.
     * @return list of all employees
     */
    private List<EditableEmployee> refreshList() {
        List<Employee> employees = employeeController.getAll();
        List<EditableEmployee> tempList = new ArrayList<>();
        for (Employee employee : employees) {
            tempList.add(new EditableEmployee(employee));
        }
        return tempList;
    }

    /**
     * Sets list of editable employees.
     * @param editableEmployee new editable employee list
     */
    public void setList(List<EditableEmployee> editableEmployee) {
        list = editableEmployee;
    }

    /**
     * Getter for currentEmployee.
     * @return currentEmployee
     */
    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    /**
     * Setter for current employee.
     * @param currentEmployee new current employee
     */
    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    /**
     * Getter for employeeToBeChanged.
     * @return employeeToBeChanged
     */
    public Employee getEmployeeToBeChanged() {
        return employeeToBeChanged;
    }

    /**
     * Setter for employeeToBeChanged.
     * @param employeeToBeChanged new employeeToBeChanged
     */
    public void setEmployeeToBeChanged(Employee employeeToBeChanged) {
        this.employeeToBeChanged = employeeToBeChanged;
    }

    /**
     * Deletes an employee from the list.
     * @param e employee to be deleted
     * @return string for redirection
     */
    public String deleteRow(EditableEmployee e) {
        employeeController.remove(e.getEmployee());
        list.remove(e);
        return "";
    }

    /**
     * If the employee is editable saves it to the database.
     * @return string for redirection
     */
    public String save() {
        for (EditableEmployee e : list) {
            if (e.isEditable()) {
                employeeController.merge(e.getEmployee());
                e.setEditable(false);
            }
        }
        return "";
    }

    /**
     * Adds a new user to the list and database.
     * @param name String for name
     * @param username String for username
     * @param password String for password
     * @return string for redirection
     */
    public String addNewUser(String name, String username, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        List<Employee> tmp = employeeController.getAll();
        for (Employee e : tmp) {
            if (e.getUserName().equals(username)) {
                context.addMessage(null, new FacesMessage("Fail",
                        "Username already in use."));
                return null;
            }
        }
        Employee addedEmployee = new Employee();
        addedEmployee.setName(name);
        addedEmployee.setUserName(username);
        addedEmployee.setPassword(password);
        addedEmployee.setCreatedDate(new Date(Calendar.getInstance()
                .getTime().getTime()));
        employeeController.add(addedEmployee);
        list.add(new EditableEmployee(addedEmployee));
        return "";
    }

    /**
     * Validation for user login.
     * @param userName String for username
     * @param password String for password
     * @return string for redirection
     */
    public String login(String userName, String password) {
        FacesContext context = FacesContext.getCurrentInstance();

        currentEmployee = employeeController.login(userName, password);
        if (currentEmployee == null) {
            context.addMessage(null, new FacesMessage("Fail",
                    "Incorrect username/password combo"));
            return null;
        }
        System.out.println(currentEmployee.isAdmin());
        return currentEmployee.isAdmin() ? "admin" : "success";
    }

    /**
     * Logs the user out and returns them to login screen.
     * @return string for redirection
     */
    public String logout() {
        currentEmployee = null;
        return "logoutSuccess";
    }

    /**
     * Redirects the admin to the edit password screen.
     * @param employee to be changed
     * @return string for redirection
     */
    public String editPasswordButton(Employee employee) {
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        employeeToBeChanged = employee;
        return "edit";
    }

    /**
     * Edits the users password.
     * @return string for redirection
     */
    public String editOneUserPassword() {
        employeeController.merge(employeeToBeChanged);
        return "adminSuccess";
    }

    /**
     * Deletes employee from the list and database.
     * @param employee employee to be deleted
     * @return string for redirection
     */
    public String deleteEmployee(EditableEmployee employee) {
        if (!currentEmployee.isAdmin()) {
            return null;
        }
        employeeController.remove(employee.getEmployee());
        list.remove(employee);
        return null;
    }

    /**
     * Method for user to update their own password.
     * @param oldPassword string for old password
     * @param password string for new password
     * @return string for redirection
     */
    public String updateEmployee(String oldPassword, String password) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (currentEmployee.getPassword().compareTo(oldPassword) != 0) {
            context.addMessage(null, new FacesMessage("Fail",
                    "Incorrect old password"));
            return null;
        }  else if (oldPassword.compareTo(password) == 0) {
            context.addMessage(null, new FacesMessage("Fail",
                    "Password must be different"));
            return null;
        } else {
            context.addMessage(null, new FacesMessage("Successful",
                    "Your password has been changed"));
            employeeToBeChanged = currentEmployee;
            employeeToBeChanged.setPassword(password);
            employeeController.merge(employeeToBeChanged);
            return "landing";
        }
    }

}
