package com.practical.InternTask.service;

import com.practical.InternTask.mapper.MealMapper;
import com.practical.InternTask.model.Meal;
import com.practical.InternTask.model.Order;
import com.practical.InternTask.model.User;
import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.OrderDTO;
import com.practical.InternTask.repository.MealRepository;
import com.practical.InternTask.repository.OrderRepository;
import com.practical.InternTask.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MealService {

    private final MealMapper mealMap;

    private final MealRepository mealRep;

    private final OrderRepository orderRep;

    private final UserRepository userRep;

    final private Logger log = LogManager.getLogger(MealService.class);

    @Autowired
    public MealService(MealMapper mealMap, MealRepository mealRep, OrderRepository orderRep, UserRepository userRep) {
        this.mealMap = mealMap;
        this.mealRep = mealRep;
        this.orderRep = orderRep;
        this.userRep = userRep;
    }

    public void createMeal(MealDTO mealDTO) throws Exception {
        if (!mealRep.existsByName(mealDTO.getName())) {
            log.info("New meal {} created", mealDTO.getName());
            mealRep.save(mealMap.toModel(mealDTO));
            return;
        }
        log.error("Meal {} already exists", mealDTO.getName());
        throw new Exception();
    }

    public void eating(OrderDTO orderDTO) {
        Meal meal = mealRep.findById(orderDTO.getMealId()).orElseThrow(() -> new EntityNotFoundException("Meal not found"));
        User user = userRep.findById(orderDTO.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        log.info("{} ordered {}", user.getName(), meal.getName());
        orderRep.save(Order.builder()
                .day(LocalDate.now())
                .meal(meal)
                .user(user)
                .build());
    }
}
