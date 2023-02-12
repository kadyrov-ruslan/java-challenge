package jp.co.axa.apidemo.api.models.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonRootName("employee")
public class EmployeeUpdateRequest {
    private String name;
    private Integer salary;
    private String department;
}
