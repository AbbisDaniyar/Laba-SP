package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Класс тестов для проверки реализации сервиса пользователей.
 * Проверяет работу методов сервиса пользователей с использованием mock-объектов.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private Role testRole;
    private UserDto testUserDto;

    /**
     * Подготавливает тестовые данные перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("USER");

        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
        testUser.setRole(testRole);

        testUserDto = new UserDto(1L, "testuser", "newPassword", "ROLE_USER", null);
    }

    /**
     * Тестирует получение всех пользователей.
     * Проверяет, что метод возвращает список всех пользователей.
     */
    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        List<User> users = Arrays.asList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<UserDto> result = userService.getAllUsers();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).username()).isEqualTo("testuser");
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Тестирует получение пользователя по ID, когда пользователь существует.
     * Проверяет, что метод возвращает корректного пользователя.
     */
    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<UserDto> result = userService.getUserById(1L);

        assertThat(result).isPresent();
        assertThat(result.get().id()).isEqualTo(1L);
        assertThat(result.get().username()).isEqualTo("testuser");
    }

    /**
     * Тестирует получение пользователя по ID, когда пользователь не существует.
     * Проверяет, что метод возвращает пустой результат.
     */
    @Test
    void getUserById_WhenUserNotExists_ShouldReturnEmpty() {

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.getUserById(999L);

        assertThat(result).isEmpty();
    }

    /**
     * Тестирует получение пользователя по имени, когда пользователь существует.
     * Проверяет, что метод возвращает корректного пользователя.
     */
    @Test
    void getUserByUsername_WhenUserExists_ShouldReturnUser() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));

        UserDto result = userService.getUserByUsername("testuser");

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("testuser");
    }

    /**
     * Тестирует получение пользователя по имени, когда пользователь не существует.
     * Проверяет, что метод возвращает null.
     */
    @Test
    void getUserByUsername_WhenUserNotExists_ShouldReturnNull() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        UserDto result = userService.getUserByUsername("unknown");

        assertThat(result).isNull();
    }

    /**
     * Тестирует создание пользователя с валидными данными.
     * Проверяет, что метод создает нового пользователя с корректными данными.
     */
    @Test
    void createUser_WithValidData_ShouldCreateUser() {
        UserDto newUserDto = new UserDto(null, "newuser", "password123", "ROLE_USER", null);

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(testRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(2L);
            return user;
        });

        UserDto result = userService.createUser(newUserDto);

        assertThat(result).isNotNull();
        assertThat(result.username()).isEqualTo("newuser");
        assertThat(result.role()).isEqualTo("ROLE_USER");

        verify(roleRepository, times(1)).findByName("USER");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Тестирует создание пользователя с невалидной ролью.
     * Проверяет, что метод выбрасывает исключение при попытке создать пользователя с несуществующей ролью.
     */
    @Test
    void createUser_WithInvalidRole_ShouldThrowException() {
        UserDto newUserDto = new UserDto(null, "newuser", "password123", "ROLE_INVALID", null);

        when(roleRepository.findByName("INVALID")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.createUser(newUserDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Роль не найдена");

        verify(userRepository, never()).save(any());
    }

    /**
     * Тестирует обновление пользователя, когда пользователь существует.
     * Проверяет, что метод корректно обновляет данные пользователя.
     */
    @Test
    void updateUser_WhenUserExists_ShouldUpdateUser() {
        UserDto updateDto = new UserDto(1L, "updateduser", "newPassword123", "ROLE_ADMIN", null);
        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName("ADMIN");

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(adminRole));
        when(passwordEncoder.encode("newPassword123")).thenReturn("newEncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDto result = userService.updateUser(1L, updateDto);

        assertThat(result).isNotNull();
        verify(userRepository, times(1)).findById(1L);
        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(passwordEncoder, times(1)).encode("newPassword123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Тестирует обновление пользователя, когда пользователь не существует.
     * Проверяет, что метод выбрасывает исключение.
     */
    @Test
    void updateUser_WhenUserNotExists_ShouldThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.updateUser(999L, testUserDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Пользователь не найден");

        verify(userRepository, never()).save(any());
    }

    /**
     * Тестирует обновление пользователя без изменения пароля.
     * Проверяет, что метод не вызывает кодирование пароля, если пароль не изменяется.
     */
    @Test
    void updateUser_WithoutPasswordChange_ShouldNotEncodePassword() {
        UserDto updateDto = new UserDto(1L, "updateduser", null, "ROLE_USER", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(testRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        userService.updateUser(1L, updateDto);

        verify(passwordEncoder, never()).encode(anyString());
    }

    /**
     * Тестирует удаление пользователя, когда пользователь существует.
     * Проверяет, что метод корректно удаляет пользователя.
     */
    @Test
    void deleteUser_WhenUserExists_ShouldDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    /**
     * Тестирует удаление пользователя, когда пользователь не существует.
     * Проверяет, что метод не выбрасывает исключение.
     */
    @Test
    void deleteUser_WhenUserNotExists_ShouldNotThrowException() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        userService.deleteUser(999L);

        verify(userRepository, times(1)).findById(999L);
        verify(userRepository, never()).deleteById(anyLong());
    }

    /**
     * Тестирует проверку существования пользователя, когда пользователь существует.
     * Проверяет, что метод возвращает true.
     */
    @Test
    void userExists_ShouldReturnTrueWhenUserExists() {
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        boolean result = userService.userExists("existinguser");

        assertThat(result).isTrue();
    }

    /**
     * Тестирует проверку существования пользователя, когда пользователь не существует.
     * Проверяет, что метод возвращает false.
     */
    @Test
    void userExists_ShouldReturnFalseWhenUserNotExists() {

        when(userRepository.existsByUsername("nonexistent")).thenReturn(false);

        boolean result = userService.userExists("nonexistent");

        assertThat(result).isFalse();
    }
}