package com.Management.task.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Management.task.Models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
