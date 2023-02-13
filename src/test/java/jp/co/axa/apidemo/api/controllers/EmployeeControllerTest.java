package jp.co.axa.apidemo.api.controllers;

import jp.co.axa.apidemo.ApiDemoApplication;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.repository.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApiDemoApplication.class)
public class EmployeeControllerTest {
    @Autowired
    EmployeeController controller;

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
    public void testGetAll_noEmployee_returnsOk() {
        clear();
        ResponseEntity<EmployeesResponse> response = controller.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertTrue(response.getBody().getEmployees().isEmpty());
        Assertions.assertEquals(0, response.getBody().getEmployeesCount());
    }

    @Test
    public void testGetAll_withEmployee_returnsOk() {
        ResponseEntity<EmployeesResponse> response = controller.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertFalse(response.getBody().getEmployees().isEmpty());
        Assertions.assertEquals(3, response.getBody().getEmployeesCount());
    }

    @Test
    public void testGet_existingEmployee_returnsOk() {
        EmployeeResponse employee = getExistingEmployee();
        ResponseEntity<EmployeeResponse> response = controller.get(employee.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());

        Assertions.assertEquals(employee.getName(), response.getBody().getName());
        Assertions.assertEquals(employee.getSalary(), response.getBody().getSalary());
        Assertions.assertEquals(employee.getDepartment(), response.getBody().getDepartment());
    }

    @Test
    public void testGet_existingEmployee_returnsNotFound() {
        ResponseEntity<EmployeeResponse> response = controller.get(100L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testCreate_valid_returnCreated() {
        EmployeeCreateRequest createRequest = new EmployeeCreateRequest("Linda", 3500, "HR");
        ResponseEntity<?> response = controller.create(createRequest);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testCreate_valid_returnValidationError() {
        EmployeeCreateRequest createRequest = new EmployeeCreateRequest("Linda", null,"HR");
        Assertions.assertThrows(ConstraintViolationException.class, () -> controller.create(createRequest));
    }

    @Test
    public void testDelete_existingEmployee_returnsOk() {
        EmployeeResponse employee = getExistingEmployee();
        ResponseEntity<?> response = controller.delete(employee.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNull(response.getBody());

        ResponseEntity<EmployeeResponse> response1 = controller.get(employee.getId());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response1.getStatusCode());
    }

    @Test
    public void testDelete_nonExistingEmployee_returnsOk() {
        ResponseEntity<?> response = controller.delete(1000L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testUpdate_existingEmployee_returnsNoContent() {
        EmployeeResponse employee = getExistingEmployee();
        ResponseEntity<?> response = controller.update(
                new EmployeeUpdateRequest(
                        employee.getName(),
                        employee.getSalary() * 10,
                        employee.getDepartment()),
                employee.getId()
        );

        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void testUpdate_nonExistingEmployee_returnsNotFound() {
        ResponseEntity<?> response = controller.update(
                new EmployeeUpdateRequest("Name", 1000, "Department"),
                1000L
        );

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
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