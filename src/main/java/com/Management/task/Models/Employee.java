package com.Management.task.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity
public class Employee {
	
	@Id
	private Long id;
	private String fullName;
	private String designation;
	
}
