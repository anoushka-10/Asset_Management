package com.Management.task.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Management.task.DTOs.AssetDTO;
import com.Management.task.DTOs.EmployeeDTO;
import com.Management.task.DTOs.createAssetDTO;
import com.Management.task.Models.Asset;
import com.Management.task.Models.Category;
import com.Management.task.Models.Employee;
import com.Management.task.Models.Status;
import com.Management.task.Repository.AssetRepository;
import com.Management.task.Repository.CategoryRepository;
import com.Management.task.Repository.EmployeeRepository;

@Service
public class AssetService {
	
	@Autowired
	private AssetRepository assetrepo;
	
	@Autowired
	private CategoryRepository categoryrepo;
	
	@Autowired
	private EmployeeRepository emprepo;
	

	public List<AssetDTO> findAllAssets() {
		return assetrepo.findAll().stream()
				.map(this::mapToDTO)
				.toList();
	}

	public AssetDTO createAsset(createAssetDTO assetRequest) {
		Asset newasset= new Asset();
		newasset.setConditionNotes(assetRequest.getNotes());
		newasset.setName(assetRequest.getName());
		newasset.setPurchaseDate(assetRequest.getPurchaseDate());
		
		Category category=categoryrepo.findById(assetRequest.getCategoryId())
				.orElseThrow(()->new RuntimeException("Category Not found"));
		newasset.setCategory(category);
		
		if (assetRequest.getEmployeeId() != null) {
		    Employee employee = emprepo.findById(assetRequest.getEmployeeId())
		            .orElseThrow(() -> new RuntimeException("Employee Not found"));
		    newasset.setAssignTo(employee);
		    newasset.setAssignStatus(Status.Assigned);
		} else {
		    newasset.setAssignTo(null);
		    newasset.setAssignStatus(Status.Available);
		}

		Asset savedAsset = assetrepo.save(newasset);
	    return mapToDTO(savedAsset);
		
	}

	public List<AssetDTO> searchAsset(String name) {
		return assetrepo.findByNameContainingIgnoreCase(name)
				.stream()
				.map(this::mapToDTO)
				.toList();
	}

	public AssetDTO updateAsset(Long id, createAssetDTO assetRequest) {
		Asset asset= assetrepo.findById(id)
				.orElseThrow(()->new RuntimeException("No Asset with id"+ id));
		asset.setName(assetRequest.getName());
		asset.setPurchaseDate(assetRequest.getPurchaseDate());
		asset.setConditionNotes(assetRequest.getNotes());
			    if (assetRequest.getCategoryId() != null) {
	        Category category = categoryrepo.findById(assetRequest.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found with id: " + assetRequest.getCategoryId()));
	        asset.setCategory(category);
	    }

	    if (assetRequest.getEmployeeId() != null) {
	        Employee employee = emprepo.findById(assetRequest.getEmployeeId())
	                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + assetRequest.getEmployeeId()));
	        asset.setAssignTo(employee);
	        asset.setAssignStatus(Status.Assigned);
	    } else {
	        asset.setAssignTo(null); 
	        asset.setAssignStatus(Status.Available);
	    }
	    
	    Asset savedAsset = assetrepo.save(asset);
	    return mapToDTO(savedAsset);
	}

	public AssetDTO recoverAsset(Long assetId) {
		Asset asset= assetrepo.findById(assetId)
				.orElseThrow(()->new RuntimeException("No Asset with id"+ assetId));
		asset.setAssignStatus(Status.Recovered);
		asset.setAssignTo(null);
		Asset savedAsset = assetrepo.save(asset);
	    return mapToDTO(savedAsset);
	}

	public AssetDTO assignAssetToEmployee(Long assetId, Long employeeId) {
		Asset asset= assetrepo.findById(assetId)
				.orElseThrow(()->new RuntimeException("No Asset with id"+ assetId));
		 Employee employee = emprepo.findById(employeeId)
	                .orElseThrow(() -> new RuntimeException("Employee not found with id: " +employeeId ));
		asset.setAssignTo(employee);
		asset.setAssignStatus(Status.Assigned);
		Asset savedAsset = assetrepo.save(asset);
	    return mapToDTO(savedAsset);
	}

	public boolean deleteAsset(Long id) {
		Asset asset= assetrepo.findById(id)
				.orElseThrow(()->new RuntimeException("No Asset with id"+ id));
		if(asset.getAssignStatus()==Status.Assigned) {
			return false;
		}
		assetrepo.delete(asset);
		return true;
	}
	
	private AssetDTO mapToDTO(Asset asset) {
	    AssetDTO dto = new AssetDTO();
	    dto.setId(asset.getId());
	    dto.setName(asset.getName());
	    dto.setPurchaseDate(asset.getPurchaseDate());
	    dto.setConditionNotes(asset.getConditionNotes());
	    dto.setCategory(asset.getCategory());
	    dto.setAssignStatus(asset.getAssignStatus());

	    if (asset.getAssignTo() != null) {
	        EmployeeDTO empDTO = new EmployeeDTO();
	        empDTO.setId(asset.getAssignTo().getId());
	        empDTO.setFullName(asset.getAssignTo().getFullName());
	        empDTO.setDesignation(asset.getAssignTo().getDesignation());
	        dto.setAssignTo(empDTO);
	    }

	    return dto;
	}

	
	
}
