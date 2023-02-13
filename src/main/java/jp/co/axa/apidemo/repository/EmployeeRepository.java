package jp.co.axa.apidemo.repository;

import jp.co.axa.apidemo.repository.entity.Employee;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

/**
 * RDB employee repository
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Override
    @Cacheable("employees")
    Optional<Employee> findById(Long id);

    @Override
    @CacheEvict(cacheNames = "employees", key = "#result.id")
    <S extends Employee> S save(S employee);

    @Override
    @CacheEvict(cacheNames = "employees", key = "#id")
    void deleteById(Long id);
}
