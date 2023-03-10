package jp.co.axa.apidemo.api.models.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Employee update request model
 */
@Getter
@Setter
@AllArgsConstructor
@JsonRootName("employee")
public class EmployeeUpdateRequest {
    private String name;
    private Integer salary;
    private String department;
}
