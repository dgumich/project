package ru.innopolis.university.stc27.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.innopolis.university.stc27.domain.Category;
import ru.innopolis.university.stc27.domain.Expense;
import ru.innopolis.university.stc27.domain.Income;
import ru.innopolis.university.stc27.domain.User;
import ru.innopolis.university.stc27.repositories.CategoryRepository;
import ru.innopolis.university.stc27.repositories.ExpenseRepository;
import ru.innopolis.university.stc27.repositories.IncomeRepository;
import ru.innopolis.university.stc27.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/income")
public class IncomeController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @GetMapping
    public String Income(HttpServletRequest request, Model model) {
        User user = userRepository.findUserBylogin(request.getRemoteUser());
        List<Category> categories = categoryRepository.findByUserIdAndType(user.getId(), true);

        List<Income> incomes = incomeRepository.findIncomesByUser(user);

        model.addAttribute("incomes", incomes);
        model.addAttribute("categories",categories);
        return "income";
    }

    @PostMapping
    public String addIncome(HttpServletRequest request,
                             @RequestParam String comment,
                             @RequestParam String categoryName,
                             @RequestParam Double sum,
                             @RequestParam String incomeDate) {

        LocalDate date = LocalDate.parse(incomeDate);

        User user = userRepository.findUserBylogin(request.getRemoteUser());


        Income income = new Income(sum, comment, date);
        Category category = categoryRepository.findByUserIdAndName(user.getId(), categoryName);

        income.setCategory(category);
        income.setUser(user);

        incomeRepository.save(income);



        return "redirect:/income";
    }


}
