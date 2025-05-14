package com.example.demo.Controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Department;
import com.example.demo.form.DepartmentForm;
import com.example.demo.form.DepartmentSearchForm;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.DepartmentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DepatmentController {
	private final DepartmentService departmentService;
	
	// 部署一覧画面
	@GetMapping("/department/index")
	public String index(Model model) {
		// departmentsのレコードを全件取得する
		List<Department> allDepartments = departmentService.findAllByOrderByNameJp();
		// 取得したレコードを変数へ格納
		model.addAttribute("departments", allDepartments);
		
		// 部署一覧画面を表示
		return "department/index";
	}
	
	// 部署検索
	@GetMapping("/department/search")
	public String search(@Validated @ModelAttribute("departmentSearchForm") DepartmentSearchForm form,
			BindingResult result,
			RedirectAttributes ra,
			Model model,
			HttpSession session) {
		
		// バリデーションエラー発生時
		if (result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentSearchForm", result);
			// 検索値の保持
			ra.addFlashAttribute("word", form.getQ());
			// エラーメッセージの保持
			ra.addFlashAttribute("departmentSearchForm", form);
			// 部署一覧画面へリダイレクトする
			return "redirect:/department/index";
		}
		
		try {
			// departmentsテーブルのレコードをあいまい検索する
			List<Department> searchResult = departmentService.findByNameJpContainingOrNameEnContainingOrderByNameJp(form.getQ(), form.getQ()).orElse(null);
			// 検索結果を保持
			model.addAttribute("departments", searchResult);
			// 検索結果件数を保持
			model.addAttribute("numberSearchResult", searchResult.size());
			// 検索値を保持
			model.addAttribute("searchTxt", form.getQ());
		} catch (Throwable e) {
			// エラーログ出力
			System.out.println(e);
			// 画面に表示するエラーメッセージを設定
			ra.addFlashAttribute("failureMessage", "データの取得に失敗しました");
			// 検索値を保持
			model.addAttribute("searchTxt", form.getQ());
			// 部署一覧画面へリダイレクトする
			return "redirect:/department/index";
		}
		
		// 部署一覧画面を表示
		return "department/index";
	}
	
	// 部署一覧画面の初期表示時にバリデーション用のオブジェクトが未定義扱いされエラーが発生するため定義
	@ModelAttribute("departmentSearchForm")
	public DepartmentSearchForm getValidationSearchForm() {
		return new DepartmentSearchForm();
	}
	
	// クリアボタン押下時処理
	@GetMapping("/department/clear")
	public String clear() {
		// 部署一覧初期画面を表示
		return "redirect:/department/index";
	}
	
	@GetMapping("/department/create")
	public String create() {
		// 部署新規追加画面を表示
		return "department/create";
	}
	
	// 新規登録画面の表示時にバリデーション用のオブジェクトが未定義扱いされエラーが発生するので、定義しておく
	@ModelAttribute("departmentForm")
	public DepartmentForm getValidationForm() {
		return new DepartmentForm();
	}
	
	@PostMapping("/department/store")
	public String store(@Validated @ModelAttribute("departmentForm") DepartmentForm form,
			BindingResult result,
			RedirectAttributes ra,
			@AuthenticationPrincipal CustomUserDetails userDetails) {
		
		// バリデーションエラー発生時
		if (result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
			// 入力された部署名を保持
			ra.addFlashAttribute("nameJp", form.getNameJp());
			// 入力された部署名（英語）を保持
			ra.addFlashAttribute("nameEn", form.getNameEn());
			// エラーメッセージの保持
			ra.addFlashAttribute("departmentForm", form);
			// 部署新規追加画面へリダイレクトする
	        return "redirect:/department/create";
		}
		
		Department department = new Department();
		// 入力された部署名をセット
		department.setNameJp(form.getNameJp());
		// 入力された部署名（英語）をセット
		department.setNameEn(form.getNameEn());
		
		try {
			// 入力内容をdepartmentsテーブルへ登録
			departmentService.save(department);
		} catch (Throwable e) {
			// エラーログ出力
			System.out.println(e);
			// 画面に表示するエラーメッセージを設定
			ra.addFlashAttribute("failureMessage", "部署登録に失敗しました");
			// 部署一覧画面へリダイレクト
			return "redirect:/department/index";
		}
		
		// リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "部署が登録されました。");
        // 部署一覧画面へリダイレクト
		return "redirect:/department/index";
	}
	
	@GetMapping("/department/edit/{departmentId}")
	public String edit(Model model,
			@PathVariable("departmentId") Long departmentId) {
		// 表示される部署レコードを取得する
		Department department = departmentService.findById(departmentId).orElse(new Department());
		// 取得した部署レコードを保持
		model.addAttribute("department", department);

		// 部署情報編集画面を表示
		return "department/edit";
	}
	
	@PutMapping("/department/update/{departmentId}")
	public String update(@PathVariable("departmentId") Long id,
			@Validated @ModelAttribute("departmentUpdateForm") DepartmentForm form,
			BindingResult result,
			RedirectAttributes ra) {
		
		// バリデーションエラー発生時
		if (result.hasErrors()) {
			ra.addFlashAttribute("org.springframework.validation.BindingResult.departmentForm", result);
			// 入力された部署名を保持
			ra.addFlashAttribute("nameJp", form.getNameJp());
			// 入力された部署名（英語）を保持
			ra.addFlashAttribute("nameEn", form.getNameEn());
			// エラーメッセージを保持
			ra.addFlashAttribute("departmentForm", form);
			// 部署情報編集画面を際表示
			return "redirect:/department/edit/" + id;
		}
		
		Department department = new Department();
		// 対象の部署IDをセット
		department.setId(id);
		// 入力された部署名をセット
		department.setNameJp(form.getNameJp());
		// 入力された部署名（英語）をセット
		department.setNameEn(form.getNameEn());
		// 対象の部署レコードを更新
		departmentService.save(department);
		
		// 際表示時にメッセージを追加
        ra.addFlashAttribute("successMessage", "部署が更新されました。");
        // 部署情報編集画面を際表示
		return "redirect:/department/edit/" + id;
	}
	
	@DeleteMapping("/department/delete/{departmentId}")
	public String delete(@PathVariable("departmentId") Long id,
			RedirectAttributes ra) {
		// 対象の部署レコードをdepartmentsテーブルから削除
		departmentService.deleteById(id);
		
		// リダイレクト時にメッセージを追加
        ra.addFlashAttribute("successMessage", "部署が削除されました。");
        // 部署一覧画面へリダイレクト
		return "redirect:/department/index";
	}
}
