package jp.co.axa.apidemo.api.controllers;

import jakarta.inject.Inject;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Inject
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<EmployeesResponse> getAll() {
        return new ResponseEntity<>(employeeService.retrieveEmployees(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) {
        Optional<EmployeeResponse> emp = employeeService.getEmployee(id);
        return emp.map(employeeResponse -> new ResponseEntity<>(employeeResponse, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody EmployeeCreateRequest createRequest) {
        employeeService.saveEmployee(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody EmployeeUpdateRequest updateRequest,
                                    @PathVariable Long id) {
        Optional<EmployeeResponse> emp = employeeService.getEmployee(id);
        if (emp.isPresent()) {
            employeeService.updateEmployee(updateRequest);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
