package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;

@Service
public interface DepartmentService {
	
	// departmentsのレコードを全件取得
	public List<Department> findAllByOrderByNameJp();
	
	// 部署名または部署名（英語）が部分一致するレコードを取得
	public Optional<List<Department>> findByNameJpContainingOrNameEnContainingOrderByNameJp(String searchJp, String searchEn);
	
	// 部署名が一致するレコードを取得
	public Optional<List<Department>> findByNameJp(String searchTxt);
	
	// 部署名（英語）が一致するレコードを取得
	public Optional<List<Department>> findByNameEn(String searchTxt);
	
	// 対象の部署レコードを取得
	public Optional<Department> findById(Long departmentId);
	
	// 対象データをdepartmentsテーブルへ登録・更新
	public void save(Department department);
	
	// 対象の部署データを削除
	public void deleteById(Long departmentId);
}
