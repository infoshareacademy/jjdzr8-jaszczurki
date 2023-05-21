package justbuild.it.web.app.service;

import justbuild.it.web.app.entity.User;
import justbuild.it.web.app.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private final static String TEST_USERNAME_ONE = "energoInstall#1";
    private final static String TEST_PASSWORD_ONE = "eNErgo111#";
    private final static String TEST_ROLE_ONE = "USER_VIP";
    private final static String TEST_USERNAME_TWO = "jankoMuzykant87";
    private final static String TEST_PASSWORD_TWO = "J@nek87!";
    private final static String TEST_ROLE_TWO = "USER";

    private UserService userService;
    private UserRepository userRepositoryMock;
    private BCryptPasswordEncoder bCryptPasswordEncoderMock;
    private static User user1;
    private static User user2;

    @BeforeAll
    static void setUpTest() {
        user1 = new User(TEST_USERNAME_ONE, TEST_PASSWORD_ONE, Set.of(new SimpleGrantedAuthority(TEST_ROLE_ONE)));
        user2 = new User(TEST_USERNAME_TWO, TEST_PASSWORD_TWO, Set.of(new SimpleGrantedAuthority(TEST_ROLE_TWO)));
    }

    @AfterAll
    static void closeTest() {
        user1 = null;
        user2 = null;
    }

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);
        bCryptPasswordEncoderMock = mock(BCryptPasswordEncoder.class);
        userService = new UserService(userRepositoryMock, bCryptPasswordEncoderMock);
    }


    @Test
    void testIfUserIsProperlyAddedFromRegisterForm() {
        // given
        when(bCryptPasswordEncoderMock.encode(TEST_PASSWORD_ONE)).thenReturn(TEST_PASSWORD_ONE);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        doAnswer(invocationOnMock -> {
            User user = userCaptor.getValue();
            user.setUserId(1L);
            return user;
        }).when(userRepositoryMock).save(userCaptor.capture());

        // when
        userService.addUserFromForm(TEST_USERNAME_ONE, TEST_PASSWORD_ONE);

        // then
        verify(userRepositoryMock, times(1)).save(userCaptor.getValue());
        assertEquals(1L, userCaptor.getValue().getUserId());
        assertEquals(TEST_USERNAME_ONE, userCaptor.getValue().getUsername());
        assertEquals(TEST_PASSWORD_ONE, userCaptor.getValue().getPassword());
        assertNotEquals(Set.of(new SimpleGrantedAuthority(TEST_ROLE_ONE)), userCaptor.getValue().getAuthorities());  // it should be TRUE (not equals) !!! because tested method gives the role: "USER" for all registered users, the role: "USER_VIP" does not exist in our application
        assertEquals(Set.of(new SimpleGrantedAuthority(TEST_ROLE_TWO)), userCaptor.getValue().getAuthorities());
    }

    @Test
    void testIfUserIsCorrectlyFoundByEmail() {
        // given
        when(userRepositoryMock.findUserByUsername(TEST_USERNAME_TWO)).thenReturn(user2);

        // when
        User userFoundByEmail = userService.findUserByLogin(TEST_USERNAME_TWO);

        // then
        verify(userRepositoryMock, times(1)).findUserByUsername(TEST_USERNAME_TWO);
        assertThat(userFoundByEmail.getPassword())
                .isEqualTo(user2.getPassword())
                .isNotEqualTo(user1.getPassword());
        assertThat(userFoundByEmail)
                .hasFieldOrPropertyWithValue("authorities", Set.of(new SimpleGrantedAuthority("USER")))
                .hasNoNullFieldsOrPropertiesExcept("userId", "firstName", "lastName", "company", "emailAddress", "telephoneNumber", "offers");

    }

    @Test
    void testGetUserIdByUsername() {
        // Given
        String username = "john";
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        when(userRepositoryMock.findUserByUsername(username)).thenReturn(user);

        // When
        Long result = userService.getUserIdByUsername(username);

        // Then
        verify(userRepositoryMock, times(1)).findUserByUsername(username);
        assertEquals(userId, result);
    }
}