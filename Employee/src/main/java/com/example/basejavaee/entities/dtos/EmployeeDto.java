package com.example.basejavaee.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    public String FullName;
    public String Birthday;
    public String Address;
    public String Position;
    public String Department;
}
