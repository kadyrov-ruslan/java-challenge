package jp.co.axa.apidemo.controllers;

import jakarta.inject.Inject;
import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Inject
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Employee>> getAll() {
        List<Employee> employees = employeeService.retrieveEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        Employee emp = employeeService.getEmployee(id);
        if (emp != null) {
            return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> create(Employee employee) {
        employeeService.saveEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Employee employee,
                                    @PathVariable Long id) {
        Employee emp = employeeService.getEmployee(id);
        if (emp != null) {
            employeeService.updateEmployee(employee);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
