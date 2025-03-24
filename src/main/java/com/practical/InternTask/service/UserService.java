package com.practical.InternTask.service;

import com.practical.InternTask.mapper.MealMapper;
import com.practical.InternTask.mapper.UserMapper;
import com.practical.InternTask.model.Order;
import com.practical.InternTask.model.User;
import com.practical.InternTask.modelDTO.DailyEating;
import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.ResultDTO;
import com.practical.InternTask.modelDTO.UserDTO;
import com.practical.InternTask.repository.MealRepository;
import com.practical.InternTask.repository.OrderRepository;
import com.practical.InternTask.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRep;

    private final UserMapper userMap;

    private final MealRepository mealRep;

    private final OrderRepository orderRep;

    private final MealMapper mealMap;

    private Logger log;

    public UserService(UserRepository userRep, UserMapper userMap, MealRepository mealRep, OrderRepository orderRep, MealMapper mealMap) {
        this.userRep = userRep;
        this.userMap = userMap;
        this.mealRep = mealRep;
        this.orderRep = orderRep;
        this.mealMap = mealMap;
    }

    public void createUser(UserDTO user) throws Exception {
        if (!userRep.existsByEmail(user.getEmail())) {
            log.info("New user {} registered", user.getName());
            userRep.save(userMap.toModel(user));
        }
        log.error("User with email {} already exists", user.getEmail());
        throw new Exception();
    }

    public List<MealDTO> getDailyEating(DailyEating daily) {
        User user = userRep.findById(daily.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id " + daily.getUserId() + " not found"));
        List<Order> orders = orderRep.findByUser(user);
        return mealMap.toDTO(orders.stream()
                .filter(order -> order.getDay().getDayOfYear() == daily.getDay().getDayOfYear())
                .map(Order::getMeal)
                .toList());
    }

    public Map<MealDTO, Float> getDailyEatingWithCalories(DailyEating daily) {
        User user = userRep.findById(daily.getUserId()).orElseThrow(() -> new EntityNotFoundException("User with id " + daily.getUserId() + " not found"));
        List<Order> orders = orderRep.findByUser(user);
        List<MealDTO> mealDTOS = mealMap.toDTO(orders.stream()
                .filter(order -> order.getDay().getDayOfYear() == daily.getDay().getDayOfYear())
                .map(Order::getMeal)
                .toList());
        Map<MealDTO, Float> result = new HashMap<>();
        mealDTOS.forEach(mealDTO -> result.put(mealDTO, mealDTO.getProtein() * 4 + mealDTO.getCarbs() * 4 + mealDTO.getFat() * 9));
        return result;
    }

    public ResultDTO getCheckDailyCalorie(UUID userId) {
        User user = userRep.findById(userId).orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        ResultDTO result = new ResultDTO();
        float dailyCal = dailyCalorie(user);
        float recommendedCal = recommendedCalorie(user);
        result.setDailyCalorie(dailyCal);
        result.setRecommendedCalorie(recommendedCal);
        switch (user.getPurpose()) {
            case BODY_CARE: {
                if (dailyCal == recommendedCal) result.setContent("Awesome");
                else result.setContent("Bad. You must do something");
            }
            break;
            case WEIGHT_GAIN: {
                if (dailyCal >= recommendedCal) result.setContent("Awesome");
                else result.setContent("Bad. You must do something");
            }
            break;
            case WEIGHT_LOSS: {
                if (dailyCal <= recommendedCal) result.setContent("Awesome");
                else result.setContent("Bad. You must do something");
            }
        }
        return result;
    }

    public float recommendedCalorie(User user) {
        if (user.getGender().name().equals("MALE"))
            return (float) (88.362 + 13.397 * user.getWeight() + 4.799 * user.getHeight() - 5.677 * user.getAge());
        else return (float) (447.593 + 9.247 * user.getWeight() + 3.098 * user.getHeight() - 4.33 * user.getAge());
    }

    public float dailyCalorie(User user) {
        List<MealDTO> mealDTOS = getDailyEating(DailyEating.builder()
                .day(LocalDate.now())
                .userId(user.getId())
                .build());
        return mealDTOS.stream()
                .map(mealDTO -> mealDTO.getProtein() * 4 + mealDTO.getCarbs() * 4 + mealDTO.getFat() * 9)
                .reduce((float) 0, Float::sum);
    }
}
