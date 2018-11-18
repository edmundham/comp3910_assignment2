package ca.bcit.infosys;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ca.bcit.infosys.timesheet.TimesheetController;

@Named
@SessionScoped
public class TimesheetApplication implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private TimesheetController timesheetController;
    
    @Inject EmployeeApplication employeeApplication;

}
