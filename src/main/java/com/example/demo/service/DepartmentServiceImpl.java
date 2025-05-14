package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	// departmentsのレコードを全件取得
	@Override
	public List<Department> findAllByOrderByNameJp() {
		// DepartmentRepository.javaのメソッド実行
		return departmentRepository.findAllByOrderByNameJp();
	};
	
	// 部署名または部署名（英語）が部分一致するレコードを取得
	@Override
	public Optional<List<Department>> findByNameJpContainingOrNameEnContainingOrderByNameJp(String searchJp, String searchEn) {
		// DepartmentRepository.javaのメソッド実行
		return departmentRepository.findByNameJpContainingOrNameEnContainingOrderByNameJp(searchJp, searchEn);
	}
	
	// 部署名が一致するレコードを取得
	@Override
	public Optional<List<Department>> findByNameJp(String searchTxt) {
		// DepartmentRepository.javaのメソッド実行
		return departmentRepository.findByNameJp(searchTxt);
	}
	
	// 部署名（英語）が一致するレコードを取得
	@Override
	public Optional<List<Department>> findByNameEn(String searchTxt) {
		// DepartmentRepository.javaのメソッド実行
		return departmentRepository.findByNameEn(searchTxt);
	}
	
	// 対象の部署レコードを取得
	@Override
	public Optional<Department> findById(Long departmentId) {
		// DepartmentRepository.javaのメソッド実行
		return departmentRepository.findById(departmentId);
	}
	
	// 対象データをdepartmentsテーブルへ登録・更新
	@Override
	public void save(Department department) {
		departmentRepository.save(department);
	}
	
	// 対象の部署データを削除
	@Override
	public void deleteById(Long departmentId) {
		departmentRepository.deleteById(departmentId);
	}
}
