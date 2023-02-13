package jp.co.axa.apidemo.api.models.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * New employee create request model
 */
@Getter
@Setter
@AllArgsConstructor
@JsonRootName("employee")
public class EmployeeCreateRequest {
    @NotNull
    private String name;
    @NotNull
    @Min(0)
    private Integer salary;
    @NotNull
    private String department;
}
