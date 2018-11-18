package ca.bcit.infosys.timesheet;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TimesheetRow")
public class TimesheetRow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="timesheetrowid")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long timesheetRowId;

    @ManyToOne
    @JoinColumn(name="timesheetid")
    private Long timesheetId;

    @Column(name="projectid")
    private Long projectId;

    @Column(name="workpackageid")
    private String workPackageId;

    @Column(name="monday")
    private Integer monday;

    @Column(name="tuesday")
    private Integer tuesday;

    @Column(name="wednesday")
    private Integer wednesday;

    @Column(name="thursday")
    private Integer thursday;

    @Column(name="friday")
    private Integer friday;

    @Column(name="saturday")
    private Integer saturday;

    @Column(name="sunday")
    private Integer sunday;

    public TimesheetRow() {

    }

    public Long getTimesheetRowId() {
        return timesheetRowId;
    }

    public void setTimesheetRowId(Long timesheetRowId) {
        this.timesheetRowId = timesheetRowId;
    }

    public Long getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Long timesheetId) {
        this.timesheetId = timesheetId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getWorkPackageId() {
        return workPackageId;
    }

    public void setWorkPackageId(String workPackageId) {
        this.workPackageId = workPackageId;
    }

    public Integer getMonday() {
        return monday;
    }

    public void setMonday(Integer monday) {
        this.monday = monday;
    }

    public Integer getTuesday() {
        return tuesday;
    }

    public void setTuesday(Integer tuesday) {
        this.tuesday = tuesday;
    }

    public Integer getWednesday() {
        return wednesday;
    }

    public void setWednesday(Integer wednesday) {
        this.wednesday = wednesday;
    }

    public Integer getThursday() {
        return thursday;
    }

    public void setThursday(Integer thursday) {
        this.thursday = thursday;
    }

    public Integer getFriday() {
        return friday;
    }

    public void setFriday(Integer friday) {
        this.friday = friday;
    }

    public Integer getSaturday() {
        return saturday;
    }

    public void setSaturday(Integer saturday) {
        this.saturday = saturday;
    }

    public Integer getSunday() {
        return sunday;
    }

    public void setSunday(Integer sunday) {
        this.sunday = sunday;
    }
}
