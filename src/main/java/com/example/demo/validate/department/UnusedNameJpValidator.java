package com.example.demo.validate.department;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.entity.Department;
import com.example.demo.service.DepartmentService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UnusedNameJpValidator implements ConstraintValidator<UnusedNameJp, String> {

	@Autowired
    private DepartmentService service;

    public void initialize(UnusedNameJp constraintAnnotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
    	// 存在する部署名（英語）のレコードが存在するかチェック
    	List<Department> nameJp = service.findByNameJp(value).orElse(null);
    	// レコードが存在しない場合はエラーとしない
        if(nameJp.isEmpty()){
        	return true;
        }
        return false;
    }
}