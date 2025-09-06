package com.Management.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Management.task.Models.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
