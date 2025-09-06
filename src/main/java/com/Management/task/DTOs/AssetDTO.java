package com.Management.task.DTOs;

import java.time.LocalDate;

import com.Management.task.Models.Category;
import com.Management.task.Models.Status;

import lombok.Data;

@Data
public class AssetDTO {
    private Long id;
    private String name;
    private LocalDate purchaseDate;
    private String conditionNotes;
    private Category category;
    private EmployeeDTO assignTo;
    private Status assignStatus;
}