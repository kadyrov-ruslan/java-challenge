package jp.co.axa.apidemo.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "EMPLOYEE_NAME")
    @NotNull
    private String name;

    @Getter
    @Setter
    @Column(name = "EMPLOYEE_SALARY")
    @NotNull
    private Integer salary;

    @Getter
    @Setter
    @Column(name = "DEPARTMENT")
    @NotNull
    private String department;
}
