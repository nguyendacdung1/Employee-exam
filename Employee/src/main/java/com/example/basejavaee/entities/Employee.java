package com.example.basejavaee.entities;

import com.example.basejavaee.entities.basic.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {
    public int ID;
    public String FullName;
    public String Birthday;
    public String Address;
    public String Position;
    public String Department;
}
