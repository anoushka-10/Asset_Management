package com.Management.task.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Management.task.Models.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>  {

	List<Asset> findAll();
	List<Asset> findByNameContainingIgnoreCase(String name);
}
