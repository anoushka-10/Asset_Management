package com.Management.task.DTOs;

import java.time.LocalDate;

import com.Management.task.Models.Status;

import lombok.Data;

@Data
public class createAssetDTO {	
	
    private String name;
	private LocalDate purchaseDate;
	private String notes;
	private Long categoryId;
	private Long employeeId;
	private Status status;
}
