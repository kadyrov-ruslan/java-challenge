package jp.co.axa.apidemo.api.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jakarta.inject.Inject;
import jp.co.axa.apidemo.api.models.request.EmployeeCreateRequest;
import jp.co.axa.apidemo.api.models.request.EmployeeUpdateRequest;
import jp.co.axa.apidemo.api.models.response.EmployeeResponse;
import jp.co.axa.apidemo.api.models.response.EmployeesResponse;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApiOperation("Provides REST API to CRUD employees")
@ApiResponses(value = {
        @ApiResponse(code = 500, message = "An unexpected error has occurred. The error has been logged and is being investigated.")
})
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Inject
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ApiOperation(value = "Returns list of all employees and their number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of employees successfully returned")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<EmployeesResponse> getAll() {
        return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
    }

    @ApiOperation(value = "Returns details of employee by her id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee details is successfully returned"),
            @ApiResponse(code = 404, message = "Employee with provided id does not exist") })
    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<EmployeeResponse> get(@PathVariable Long id) {
        Optional<EmployeeResponse> emp = employeeService.getEmployee(id);
        return emp.map(employeeResponse -> new ResponseEntity<>(employeeResponse, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Creates new employee")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New employee is successfully created"),
            @ApiResponse(code = 400, message = "Invalid input")
    })
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeCreateRequest createRequest) {
        employeeService.saveEmployee(createRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Deletes existing employee")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee is successfully deleted")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Updates existing employee")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Employee is successfully updated"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "Employee with provided id does not exist")
    })
    @PutMapping("{id}")
    public ResponseEntity<?> update(@Valid @RequestBody EmployeeUpdateRequest updateRequest,
                                    @PathVariable Long id) {
        Optional<EmployeeResponse> emp = employeeService.getEmployee(id);
        if (emp.isPresent()) {
            employeeService.updateEmployee(id, updateRequest);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
