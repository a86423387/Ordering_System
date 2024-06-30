package com.OrderingSystem.OrderingSystem.Controller;

import com.OrderingSystem.OrderingSystem.Dto.DishDTO;
import com.OrderingSystem.OrderingSystem.Service.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
@RequiredArgsConstructor
@Slf4j
public class DishController {
    private final DishService dishService;

    @GetMapping
    public String getAllDishes(Model model) {
        List<DishDTO> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "dishes";
    }

    @GetMapping("/create")
    public String createDishForm(Model model) {
        model.addAttribute("dish", new DishDTO());
        model.addAttribute("dishTypes", dishService.getAllDishTypes());
        return "create-dish";
    }

    @PostMapping("/create")
    public String createDish(@ModelAttribute("dish") DishDTO dishDTO) {
        dishService.createDish(dishDTO);
        return "redirect:/dishes";
    }

     // 顯示編輯表單的 GET 請求方法
    @GetMapping("/edit/{dishId}")
    public String editDishForm(@PathVariable Long dishId, Model model) {
        DishDTO dishDTO = dishService.getDishById(dishId);
        model.addAttribute("dish", dishDTO);
        model.addAttribute("dishTypes", dishService.getAllDishTypes());
        return "edit-dish";
    }

    // 處理編輯表單提交的 POST 請求方法
    @PostMapping("/edit/{dishId}")
    public String updateDish(@PathVariable Long dishId, @ModelAttribute DishDTO dishDTO) {
        dishService.updateDish(dishId, dishDTO);
        return "redirect:/dishes";
    }

    @GetMapping("/delete/{dishId}")
    public String deleteDish(@PathVariable Long dishId) {
        dishService.deleteDish(dishId);
        return "redirect:/dishes";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        log.error("Error occurred: ", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    @GetMapping("/test-error")
    public String testError() {
        throw new RuntimeException("Test exception");
    }
}
