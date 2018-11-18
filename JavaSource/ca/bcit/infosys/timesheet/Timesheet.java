package ca.bcit.infosys.timesheet;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Timesheet")
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="timesheetid")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long timesheetId;

    @OneToOne
    @JoinColumn(name="employeeid")
    private Long employeeId;

    @Column(name="endweek")
    private Date endWeek;

    @Column(name="startweek")
    private Date startWeek;

    public Timesheet() {

    }

    public Long getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Long timesheetId) {
        this.timesheetId = timesheetId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Date getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(Date endWeek) {
        this.endWeek = endWeek;
    }

    public Date getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(Date startWeek) {
        this.startWeek = startWeek;
    }


}
