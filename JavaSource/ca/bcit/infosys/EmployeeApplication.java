package ca.bcit.infosys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ca.bcit.infosys.employee.EditableEmployee;
import ca.bcit.infosys.employee.Employee;
import ca.bcit.infosys.employee.EmployeeController;

@Named
@SessionScoped
public class EmployeeApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject private EmployeeController employeeController;

    @Inject private Employee toBeAddedEmployee;
    @Inject private Employee toBeEdited;
    
    @Inject private Employee currentUser;
    private String oldPassword;
    private String newPassword;
    private String adminSetPassword;
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

    @Transactional
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

    @Transactional
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
    
    public String logIn() {
        currentUser = employeeController.authenticate(currentUser.getUserName(), currentUser.getPassword());
        if (currentUser == null) {
            return null;
        } else {
            return "landing";
        }
    }
    
    public String logOut() {
        currentUser = null;
        return "index";
    }
    
    @Transactional
    public String updatePassword(String oldPass, String newPass) {
        FacesContext context = FacesContext.getCurrentInstance();
        if(oldPass.compareTo(currentUser.getPassword()) != 0) {
            context.addMessage(null, new FacesMessage("Fail",
                    "Incorrect old password"));
            return null;
        } else if (oldPass.compareTo(newPass) == 0) {
            context.addMessage(null, new FacesMessage("Fail",
                    "Password must be different"));
            return null;
        } else {
            currentUser.setPassword(newPass);
            employeeController.merge(currentUser);
            context.addMessage(null, new FacesMessage("Successful",
                    "Your password has been changed"));
            return null;
        }
    }
    
    @Transactional
    public String adminUpdatePassword() {
        if (adminSetPassword.isEmpty() || adminSetPassword.equals("")) {
            adminSetPassword = null;
            return null;
        } else {
            toBeEdited.setPassword(adminSetPassword);
            employeeController.merge(toBeEdited);
            return "admin";
        }
    }
    
    public String adminEditPass(Employee e) {
        toBeEdited = e;
        return "editPassword";
    }

    /**
     * @return the currentUser
     */
    public Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * @param currentUser the currentUser to set
     */
    public void setCurrentUser(Employee currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * @return the oldPassword
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * @param oldPassword the oldPassword to set
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * @return the newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword the newPassword to set
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return the employeeController
     */
    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    /**
     * @param employeeController the employeeController to set
     */
    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }

    /**
     * @return the toBeAddedEmployee
     */
    public Employee getToBeAddedEmployee() {
        return toBeAddedEmployee;
    }

    /**
     * @param toBeAddedEmployee the toBeAddedEmployee to set
     */
    public void setToBeAddedEmployee(Employee toBeAddedEmployee) {
        this.toBeAddedEmployee = toBeAddedEmployee;
    }

    /**
     * @return the toBeEdited
     */
    public Employee getToBeEdited() {
        return toBeEdited;
    }

    /**
     * @param toBeEdited the toBeEdited to set
     */
    public void setToBeEdited(Employee toBeEdited) {
        this.toBeEdited = toBeEdited;
    }

    /**
     * @return the adminSetPassword
     */
    public String getAdminSetPassword() {
        return adminSetPassword;
    }

    /**
     * @param adminSetPassword the adminSetPassword to set
     */
    public void setAdminSetPassword(String adminSetPassword) {
        this.adminSetPassword = adminSetPassword;
    }
    

}
