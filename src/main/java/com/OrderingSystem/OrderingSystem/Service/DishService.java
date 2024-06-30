package com.OrderingSystem.OrderingSystem.Service;

import com.OrderingSystem.OrderingSystem.Repository.DishRepository;
import com.OrderingSystem.OrderingSystem.Repository.DishTypeRepository;
import com.OrderingSystem.OrderingSystem.Dto.DishDTO;
import com.OrderingSystem.OrderingSystem.Dto.DishTypeDTO;
import com.OrderingSystem.OrderingSystem.Entity.DishEntity;
import com.OrderingSystem.OrderingSystem.Entity.DishTypeEntity;
import com.OrderingSystem.OrderingSystem.Exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final DishTypeRepository dishTypeRepository;

    public List<DishDTO> getAllDishes() {
        List<DishEntity> dishes = dishRepository.findAll();
        return dishes.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DishDTO getDishById(Long dishId) {
        DishEntity dish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
        return convertToDTO(dish);
    }

    public void createDish(DishDTO dishDTO) {
        DishEntity dish = convertToEntity(dishDTO);
        dishRepository.save(dish);
    }

    public void updateDish(Long dishId, DishDTO updatedDishDTO) {
        DishEntity existingDish = dishRepository.findById(dishId).orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
        existingDish.setName(updatedDishDTO.getName());
        existingDish.setDescription(updatedDishDTO.getDescription());
        existingDish.setPrice(updatedDishDTO.getPrice());

        DishTypeEntity dishType = dishTypeRepository.findByName(updatedDishDTO.getType_id())
                .orElseGet(() -> {
                    DishTypeEntity newDishType = new DishTypeEntity();
                    newDishType.setName(updatedDishDTO.getType_id());
                    return dishTypeRepository.save(newDishType);
                });
        existingDish.setType_id(dishType);

        dishRepository.save(existingDish);
    }

    public void deleteDish(Long dishId) {
        dishRepository.deleteById(dishId);
    }

    public List<DishTypeDTO> getAllDishTypes() {
        List<DishTypeEntity> dishTypes = dishTypeRepository.findAll();
        return dishTypes.stream().map(this::convertToDishTypeDTO).collect(Collectors.toList());
    }

    private DishDTO convertToDTO(DishEntity dish) {
        DishDTO dishDTO = new DishDTO();
        dishDTO.setId(dish.getId());
        dishDTO.setName(dish.getName());
        dishDTO.setDescription(dish.getDescription());
        dishDTO.setPrice(dish.getPrice());
        dishDTO.setType_id(dish.getType().getName());
        return dishDTO;
    }

    private DishTypeDTO convertToDishTypeDTO(DishTypeEntity dishType) {
        DishTypeDTO dishTypeDTO = new DishTypeDTO();
        dishTypeDTO.setId(dishType.getId());
        dishTypeDTO.setName(dishType.getName());
        return dishTypeDTO;
    }

    private DishEntity convertToEntity(DishDTO dishDTO) {
        DishTypeEntity dishType = dishTypeRepository.findByName(dishDTO.getType_id())
                .orElseGet(() -> {
                    DishTypeEntity newDishType = new DishTypeEntity();
                    newDishType.setName(dishDTO.getType_id());
                    return dishTypeRepository.save(newDishType);
                });

        DishEntity dish = new DishEntity();
        dish.setName(dishDTO.getName());
        dish.setDescription(dishDTO.getDescription());
        dish.setPrice(dishDTO.getPrice());
        dish.setType_id(dishType);
        return dish;
    }
}
