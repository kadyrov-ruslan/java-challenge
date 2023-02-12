package jp.co.axa.apidemo.api.models.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Single employee info response model
 */
@Getter
@Setter
@NoArgsConstructor
@JsonRootName("employee")
public class EmployeeResponse {
    private String name;
    private Integer salary;
    private String department;
}
