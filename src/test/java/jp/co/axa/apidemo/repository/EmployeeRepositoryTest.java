package jp.co.axa.apidemo.repository;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.repository.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
public class EmployeeRepositoryTest {
    @Autowired
    CacheManager cacheManager;

    @Autowired
    EmployeeRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        repository.save(new Employee(1L, "John", 3500, "IT"));
        repository.save(new Employee(2L, "Anna", 5200, "Finance"));
    }

    @Test
    public void findById_existingEmployee_shouldBeCached() {
        Optional<Employee> employee1 = repository.findById(1L);
        Assertions.assertEquals(employee1, getCachedEmployee(1L));

        Optional<Employee> employee2 = repository.findById(2L);
        Assertions.assertEquals(employee2, getCachedEmployee(2L));
    }

    @Test
    public void findById_nonExistingEmployee_shouldNotBeCached() {
        Assertions.assertFalse(getCachedEmployee(5L).isPresent());
    }

    @Test
    public void save_newEmployee_shouldBeCached() {
        repository.save(new Employee(3L, "Ayako", 4500, "HR"));
        Optional<Employee> employee = repository.findById(3L);
        Assertions.assertEquals(employee, getCachedEmployee(3L));
    }

    private Optional<Employee> getCachedEmployee(long id) {
        return Optional.ofNullable(cacheManager.getCache("employees")).map(x -> x.get(id, Employee.class));
    }
}