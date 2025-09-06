package com.Management.task.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Management.task.DTOs.AssetDTO;
import com.Management.task.DTOs.AssignAssetRequest;
import com.Management.task.DTOs.createAssetDTO;
import com.Management.task.Service.AssetService;

@RestController
@RequestMapping("/asset")
public class AssetController {
	
	@Autowired
	private AssetService assetservice;
	
	@GetMapping("/getAll")
	ResponseEntity<List<AssetDTO>> listAssets(){
		List<AssetDTO> assets=assetservice.findAllAssets();
		return ResponseEntity.ok(assets);
	}
	
	@PostMapping("/create")
	ResponseEntity<AssetDTO> createAsset(@RequestBody createAssetDTO assetRequest){
	AssetDTO createdasset=assetservice.createAsset(assetRequest);
	return ResponseEntity.status(HttpStatus.CREATED).body(createdasset);
	}
	
	@GetMapping("/search")
	ResponseEntity<List<AssetDTO>> GetAssetByName(@RequestParam String name){
		List<AssetDTO> assets=assetservice.searchAsset(name);
		return ResponseEntity.ok(assets);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<AssetDTO> UpdateAsset(@PathVariable Long id, @RequestBody createAssetDTO assetRequest){
		AssetDTO updatedAsset=assetservice.updateAsset(id,assetRequest);
		return ResponseEntity.ok(updatedAsset);
	}
	
	@PutMapping("/recover/{assetId}")
	public ResponseEntity<AssetDTO> recoverAsset(@PathVariable Long assetId){
		AssetDTO recovered=assetservice.recoverAsset(assetId); 
		return ResponseEntity.ok(recovered);
	}
	@PutMapping("/assign/{assetId}")
	public ResponseEntity<AssetDTO> assignAsset(
	        @PathVariable Long assetId,
	        @RequestBody AssignAssetRequest request) {

	    AssetDTO assignedAsset = assetservice.assignAssetToEmployee(assetId, request.getEmployeeId());
	    return ResponseEntity.ok(assignedAsset);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteAsset(@PathVariable Long id){
		boolean deleted=assetservice.deleteAsset(id);
		if(deleted) return ResponseEntity.ok("Asset Deleted");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Asset cannot be deleted as it currently assigned");
	}
	
	
}
