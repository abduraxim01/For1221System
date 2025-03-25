package com.practical.InternTask.service;

import com.practical.InternTask.mapper.MealMapper;
import com.practical.InternTask.mapper.UserMapper;
import com.practical.InternTask.model.Meal;
import com.practical.InternTask.model.Order;
import com.practical.InternTask.model.User;
import com.practical.InternTask.modelDTO.DailyEating;
import com.practical.InternTask.modelDTO.MealDTO;
import com.practical.InternTask.modelDTO.UserDTO;
import com.practical.InternTask.repository.OrderRepository;
import com.practical.InternTask.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userSer;

    @Mock
    private UserRepository userRep;

    @Mock
    private UserMapper userMap;

    @Mock
    private OrderRepository orderRep;

    @Mock
    private MealMapper mealMap;

    @Nested
    class createUserTest {

        UserDTO dto;
        User model;

        @BeforeEach
        void setUp() {
            dto = UserDTO.builder()
                    .name("Test user")
                    .email("test@gmail.com")
                    .build();
            model = User.builder()
                    .name("Test user")
                    .email("test@gmail.com")
                    .build();
        }

        @Test
        void createUserTest_Exception() {

            when(userRep.existsByEmail(dto.getEmail())).thenReturn(true);

            assertThrows(Exception.class, () -> userSer.createUser(dto));

            verify(userRep, never()).save(any(User.class));
            verify(userRep, times(1)).existsByEmail(dto.getEmail());
        }

        @Test
        void createUserTest_Success() throws Exception {

            when(userRep.existsByEmail(dto.getEmail())).thenReturn(false);
            when(userMap.toModel(dto)).thenReturn(model);

            userSer.createUser(dto);

            verify(userRep, times(1)).save(model);
            verify(userRep, times(1)).existsByEmail(dto.getEmail());
            verify(userMap, times(1)).toModel(dto);
        }
    }

    @Nested
    class getDailyEatingTest {

        User user;
        List<Order> orderList;
        Meal meal;
        MealDTO mealDTO;
        DailyEating dailyEating;
        List<MealDTO> mealDTOS;
        List<Meal> meals;

        @BeforeEach
        void setUp() {
            user = new User();
            user.setId(UUID.randomUUID());

            dailyEating = DailyEating.builder()
                    .userId(user.getId().toString())
                    .day(LocalDate.of(2025, 3, 25))
                    .build();

            meal = Meal.builder()
                    .name("Palov")
                    .protein(120)
                    .calorie(150)
                    .carbs(100)
                    .fat(50)
                    .build();

            mealDTO = MealDTO.builder()
                    .name(meal.getName())
                    .calorie(meal.getCalorie())
                    .carbs(meal.getCarbs())
                    .fat(meal.getFat())
                    .protein(meal.getProtein())
                    .build();

            Order order1 = Order.builder()
                    .user(user)
                    .meal(meal)
                    .day(LocalDate.of(2025, 3, 25))
                    .build();

            mealDTOS = List.of(mealDTO);
            meals = List.of(meal);
            orderList = List.of(order1);
        }

        @Test
        void getDailyEatingTest_EntityNotFound() {

            when(userRep.findById(user.getId())).thenReturn(Optional.empty());

            EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> userSer.getDailyEating(dailyEating));
            assertEquals("User with id " + dailyEating.getUserId() + " not found", e.getMessage());
        }

        @Test
        void getDailyEatingTest_NoOrderForGivenDay() {
            when(userRep.findById(user.getId())).thenReturn(Optional.of(user));
            when(orderRep.findByUser(user)).thenReturn(List.of());
            when(mealMap.toDTO(anyList())).thenReturn(List.of());

            List<MealDTO> mealDTOS = userSer.getDailyEating(dailyEating);

            assertTrue(mealDTOS.isEmpty());
            verify(userRep, times(1)).findById(user.getId());
            verify(orderRep, times(1)).findByUser(user);
        }

        @Test
        void getDailyEatingTest_ExistsOrderForGivenDay() {
            when(userRep.findById(user.getId())).thenReturn(Optional.of(user));
            when(orderRep.findByUser(user)).thenReturn(orderList);
            when(mealMap.toDTO(anyList())).thenReturn(mealDTOS);

            List<MealDTO> mealDTOS1 = userSer.getDailyEating(dailyEating);

            assertNotNull(mealDTOS1);
            assertEquals(mealDTOS1.size(), mealDTOS.size());
            verify(mealMap, times(1)).toDTO(anyList());
        }
    }
}