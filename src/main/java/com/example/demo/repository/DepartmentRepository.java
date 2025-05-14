package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	// departmentsのレコードを全件取得
	List<Department> findAllByOrderByNameJp();
	
	// 部署名または部署名（英語）が部分一致するレコードを取得
	Optional<List<Department>> findByNameJpContainingOrNameEnContainingOrderByNameJp(@Param("searchTxt") String searchJp, @Param("searchTxt") String searchEn);
	
	// 部署名が一致するレコードを取得
	Optional<List<Department>> findByNameJp(@Param("searchTxt") String searchTxt);
	
	// 部署名（英語）が一致するレコードを取得
	Optional<List<Department>> findByNameEn(@Param("searchTxt") String searchTxt);
	
	// 対象の部署レコードを取得
	Optional<Department> findById(@Param("departmentId") Long departmentId);
}
