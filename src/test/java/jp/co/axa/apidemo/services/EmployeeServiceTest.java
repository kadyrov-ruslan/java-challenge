package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
class EmployeeServiceTest {
    @Autowired
    EmployeeService service;

    @Autowired
    EmployeeRepository repository;

    @BeforeEach
    void setUp() {
        createNewEmployees();
    }

    @AfterEach
    void clear() {
        repository.deleteAllInBatch();
    }

    @Test
    void getAllEmployees_noEmployees_returnsEmptyList() {
        clear();
        EmployeesResponse allEmployees = service.getAllEmployees();
        Assertions.assertTrue(allEmployees.getEmployees().isEmpty());
        Assertions.assertEquals(0, allEmployees.getEmployeesCount());
    }

    @Test
    void getAllEmployees_someEmployees_returnsFilledList() {
        EmployeesResponse allEmployees = service.getAllEmployees();
        Assertions.assertFalse(allEmployees.getEmployees().isEmpty());
        Assertions.assertEquals(3, allEmployees.getEmployeesCount());
    }

    @Test
    void getEmployee_nonExistingEmployee_returnsEmpty() {
        Assertions.assertFalse(service.getEmployee(100L).isPresent());
    }

    @Test
    void getEmployee_existingEmployee_returnsActualEmployee() {
        EmployeeResponse employee = getExistingEmployee();

        Optional<EmployeeResponse> employeeResponseOptional = service.getEmployee(employee.getId());
        Assertions.assertTrue(employeeResponseOptional.isPresent());
        Assertions.assertEquals(employee.getName(), employeeResponseOptional.get().getName());
        Assertions.assertEquals(employee.getSalary(), employeeResponseOptional.get().getSalary());
        Assertions.assertEquals(employee.getDepartment(), employeeResponseOptional.get().getDepartment());
    }

    @Test
    void saveEmployee_ok() {
        service.saveEmployee(new EmployeeCreateRequest("Linda", 3500, "HR"));

        EmployeesResponse allEmployees = service.getAllEmployees();
        Long lastId = allEmployees.getEmployees().get((int) (allEmployees.getEmployeesCount() - 1)).getId();
        Optional<EmployeeResponse> employeeResponseOptional = service.getEmployee(lastId);

        Assertions.assertTrue(employeeResponseOptional.isPresent());
        Assertions.assertEquals("Linda", employeeResponseOptional.get().getName());
        Assertions.assertEquals(3500, (int) employeeResponseOptional.get().getSalary());
        Assertions.assertEquals("HR", employeeResponseOptional.get().getDepartment());
    }

    @Test
    void deleteEmployee_existingEmployee_ok() {
        EmployeeResponse employee = getExistingEmployee();

        Optional<EmployeeResponse> employeeResponseOptional = service.getEmployee(employee.getId());
        Assertions.assertTrue(employeeResponseOptional.isPresent());
        Assertions.assertEquals(employee.getName(), employeeResponseOptional.get().getName());

        service.deleteEmployee(employee.getId());

        Optional<EmployeeResponse> s = service.getEmployee(employee.getId());
        Assertions.assertFalse(s.isPresent());
    }

    @Test
    void deleteEmployee_nonExistingEmployee_ok() {
        long id = 201L;
        Optional<EmployeeResponse> employeeResponseOptional = service.getEmployee(id);
        Assertions.assertFalse(employeeResponseOptional.isPresent());

        service.deleteEmployee(id);

        employeeResponseOptional = service.getEmployee(id);
        Assertions.assertFalse(employeeResponseOptional.isPresent());
    }

    @Test
    void updateEmployee_existingEmployee_ok() {
        EmployeeResponse employee = getExistingEmployee();
        EmployeeUpdateRequest updateRequest = new EmployeeUpdateRequest(
                employee.getName(),
                employee.getSalary() * 10,
                employee.getDepartment()
        );

        service.updateEmployee(employee.getId(), updateRequest);

        Optional<EmployeeResponse> employeeResponseOptional = service.getEmployee(employee.getId());
        Assertions.assertTrue(employeeResponseOptional.isPresent());
        Assertions.assertEquals(employee.getName(), employeeResponseOptional.get().getName());
        Assertions.assertEquals(employee.getSalary() * 10, (int)employeeResponseOptional.get().getSalary());
    }

    // TODO move to common test utils
    private void createNewEmployees() {
        service.saveEmployee(new EmployeeCreateRequest("John", 3500, "IT"));
        service.saveEmployee(new EmployeeCreateRequest("Anna", 5200, "Finance"));
        service.saveEmployee(new EmployeeCreateRequest("Ayako", 4500, "HR"));
    }

    // TODO move to common test utils
    private EmployeeResponse getExistingEmployee() {
        EmployeesResponse allEmployees = service.getAllEmployees();
        return allEmployees.getEmployees().get(0);
    }
}