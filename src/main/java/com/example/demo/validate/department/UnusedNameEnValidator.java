package com.example.demo.validate.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UnusedNameEnValidator implements ConstraintValidator<UnusedNameEn, String> {

	@Autowired
    private DepartmentService service;

    public void initialize(UnusedNameEn constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
    	// 存在する部署名のレコードが存在するかチェック
    	List<Department> nameEn = service.findByNameEn(value).orElse(null);
    	// レコードが存在しない場合はエラーとしない
        if(nameEn.isEmpty()){
        	return true;
        }
        return false;
    }
}