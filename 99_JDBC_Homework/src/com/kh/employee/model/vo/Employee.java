package com.kh.employee.model.vo;

import java.sql.Date;

/**
 * @author user1
 *
 */
public class Employee {

private String empId;
private String empName;
private String empNo;
private String email;
private String phone;
private String deptCode;
private String jobCode;
private String salLevel;
private int salary;
private Double bonus;
private String managerId;
private Date hireDate;
private Date entDate;
private String entYn;

public Employee() {}

public Employee(String empId, String empName, String empNo, String email, String phone, String deptCode, String jobCode, String salLevel,
int salary, double bonus, String managerId, Date hireDate, Date entDate, String entYn) {
super();
this.empId = empId;
this.empName = empName;
this.empNo = empNo;
this.email = email;
this.phone = phone;
this.deptCode = deptCode;
this.jobCode = jobCode;
this.salLevel = salLevel;
this.salary = salary;
this.bonus = bonus;
this.managerId = managerId;
this.hireDate = hireDate;
this.entDate = entDate;
this.entYn = entYn;
}



public Employee(String empName, String empNo, String email, String phone, String deptCode, String jobCode, String salLevel,
int salary, double bonus, String managerId) {
super();
this.empName = empName;
this.empNo= empNo;
this.email = email;
this.phone = phone;
this.deptCode = deptCode;
this.jobCode = jobCode;
this.salLevel = salLevel;
this.salary = salary;
this.bonus = bonus;
this.managerId = managerId;
}

public String getEmpName() {
return empName;
}

public void setEmpName(String empName) {
this.empName = empName;
}

public String getEmpNo() {
return empNo;
}

public void setEmpNo(String empNo) {
this.empNo = empNo;
}
public Date getHireDate() {
return hireDate;
}

public void setHireDate(Date hireDate) {
this.hireDate = hireDate;
}

public Date getEntDate() {
return entDate;
}

public void setEntDate(Date entDate) {
this.entDate = entDate;
}

public String getEntYn() {
return entYn;
}

public void setEntYn(String entYn) {
this.entYn = entYn;
}

public String getEmpId() {
return empId;
}
public void setEmpId(String empId) {
this.empId = empId;
}
public String getEmail() {
return email;
}
public void setEmail(String email) {
this.email = email;
}
public String getPhone() {
return phone;
}
public void setPhone(String phone) {
this.phone = phone;
}
public String getDeptCode() {
return deptCode;
}
public void setDepCode(String deptCode) {
this.deptCode = deptCode;
}
public String getJobCode() {
return jobCode;
}
public void setJobCode(String jobCode) {
this.jobCode = jobCode;
}
public String getSalLevel() {
return salLevel;
}
public void setSalLevel(String salLevel) {
this.salLevel = salLevel;
}
public int getSalary() {
return salary;
}
public void setSalary(int salary) {
this.salary = salary;
}
public double getBonus() {
return bonus;
}
public void setBonus(double bonus) {
this.bonus = bonus;
}
public String getManagerId() {
return managerId;
}
public void setManagerId(String managerId) {
this.managerId = managerId;
}

@Override
public String toString() {
	return "Employee [empName=" + empName + ", empNo=" + empNo + ", email=" + email + ", phone=" + phone + ", depCode="
			+ deptCode + ", jobCode=" + jobCode + ", salLevel=" + salLevel + ", salary=" + salary + ", bonus=" + bonus
			+ ", managerId=" + managerId + "]";
}




}
