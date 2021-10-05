import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.exception.DuplicateUserException;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private final UserServiceImpl userService = new UserServiceImpl();

    @Mock
    UserDao userDao;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test(expected = DuplicateUserException.class)
    public void testCreateUserDuplicated() {
        when(passwordEncoder.encode(anyString())).thenReturn("mocking_the_best_encryption_wow");
        when(userDao.create(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyBoolean()))
                .thenThrow(DuplicateKeyException.class);
        userService.create(123, "John", "Doe", "johndoe",
                "jdoe@itba.edu.ar", "jdoe123", false);
        Assert.fail("Should have thrown DuplicateUserException");
    }
}
