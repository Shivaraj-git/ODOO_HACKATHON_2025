package com.BitByBit.ExpenSR.repository;

import com.BitByBit.ExpenSR.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
