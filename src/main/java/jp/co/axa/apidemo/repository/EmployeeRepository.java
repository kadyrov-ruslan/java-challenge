package jp.co.axa.apidemo.repository;

import jp.co.axa.apidemo.repository.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RDB employee repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
