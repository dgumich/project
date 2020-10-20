package ru.innopolis.university.stc27.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.university.stc27.domain.Category;
import ru.innopolis.university.stc27.domain.Expense;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.CategoryRepository;
import ru.innopolis.university.stc27.repositories.ExpenseRepository;
import ru.innopolis.university.stc27.repositories.UserRepository;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String CategoryList(HttpServletRequest request, Model model) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        //SecurityContextHolder.getContext().getAuthentication().
        List<Category> categories = categoryRepository.findByUserId(user.getId());
        System.out.println(categories.toString());
        model.addAttribute("categories", categories);
        model.addAttribute("user", user);

        return "category";
    }

    @PostMapping
    public String addCategory(HttpServletRequest request, @RequestParam String categoryName, @RequestParam String type) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        boolean categoryType = true;
        if ("расход".equals(type)) {
            categoryType = false;
        }
        Category category = new Category(categoryName, categoryType,user.getId());
        categoryRepository.save(category);
        return "redirect:/category";
    }
}
