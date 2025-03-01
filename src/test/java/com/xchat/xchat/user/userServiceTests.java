package com.xchat.xchat.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class userServiceTests {

    // @Autowired
    // private UserRepository repository;
    //
    //
    // @Test
    // public void testGetUserByEmail(){
    // assertEquals(4, 2+2);
    // assertNotNull(repository.findByEmail("veekshithcb@gmail.com"));
    // }
    //
    // @ParameterizedTest
    // @CsvSource({
    // "1,1,2",
    // "9,4,13",
    // "2,4,6"
    // })
    // public void test(int a , int b , int c){
    // assertEquals(a+b , c , "test failed for "+a +"and" +b);
    // }

    // @InjectMocks
    // private UserService userService;
    // @Mock
    // private UserRepository repository;

    // @BeforeEach
    // void setUp() {
    // MockitoAnnotations.openMocks(this); // Use this instead of initMocks()
    // }
    // @Test
    // public void testGetUserByEmail(){
    // when(repository.findByEmail(ArgumentMatchers.anyString())).thenReturn(new
    // User("vee","vee@veekshith.dev","secret",Status.ONLINE));
    // User user = userService.getUserByEmail("vee@veekshith.dev");
    // Assertions.assertNotNull(user);
    // }

    @Mock
    private UserRepository userRepository;  // Mocking UserRepository

    @InjectMocks
    private UserService userService;  // Injecting mock into UserService

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create a sample user
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("test@example.com");
        testUser.setStatus(Status.ONLINE);
    }

    @Test
    void getUserByEmailTest() {
        // Arrange: Mock the repository response
        when(userRepository.findByEmail("test@example.com")).thenReturn(testUser);

        // Act: Call the service method
        User result = userService.getUserByEmail("test@example.com");

        // Assert: Verify the response
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(Status.ONLINE, result.getStatus());

        // Verify that the repository method was called once
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

}
