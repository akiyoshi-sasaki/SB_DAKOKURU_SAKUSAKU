package com.example.demo.form;

import com.example.demo.validate.department.UnusedNameEn;
import com.example.demo.validate.department.UnusedNameJp;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentForm implements ValidationGroups {
	
	private Long id;
	
	@UnusedNameJp(message = "部署名が存在しています")
	@Size(min = 1, max = 255, message = "部署名は1文字以上、255文字以内で入力してください")
	private String nameJp;
	
	@UnusedNameEn(message = "部署名（英語）が存在しています")
	@Size(min = 1, max = 255, message = "部署名（英語）は1文字以上、255文字以内で入力してください")
	@Pattern(regexp = "^[a-zA-z0-9 ]*$", message = "部署名（英語）は半角英数字で入力してください")
	private String nameEn;
}