package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Log;
import bg.softuni.hotelagency.repository.LogRepository;
import bg.softuni.hotelagency.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LogServiceImplTest {

    private LogServiceImpl serviceToTest;
    private Log regLog1, regLog2, exLog1, exLog2;

    @Mock
    LogRepository logRepository;
    @Mock
    UserService userService;


    @BeforeEach
    public void setUp() {
        regLog1 = new Log();
        regLog1.setAction("register").
                setException(null);
        regLog2 = new Log();
        regLog2.setAction("register").
                setException(null);
        exLog1 = new Log();
        exLog1.setAction("register").
                setException("Test exception 1");
        exLog2 = new Log();
        exLog2.setAction("register").
                setException("Test Exception 2");

        serviceToTest = new LogServiceImpl(logRepository, userService);
    }

    @Test
    public void testGetRegisterLog() {
        when(logRepository.getLogsByExceptionIsNull()).thenReturn(List.of(regLog1, regLog2));

        List<Log> logs = serviceToTest.getRegisterLog();

        assertEquals(2, logs.size());
        assertNull(logs.get(0).getException());
        assertNull(logs.get(1).getException());
        assertEquals(regLog1.getAction(),logs.get(0).getAction());
        assertEquals(regLog2.getAction(),logs.get(1).getAction());
    }

    @Test
    public void testGetExceptionLog() {
        when(logRepository.getLogsByExceptionNotNull()).thenReturn(List.of(exLog1, exLog2));

        List<Log> logs = serviceToTest.getExceptionLog();

        assertEquals(2, logs.size());
        assertEquals(exLog1.getException(),logs.get(0).getException());
        assertEquals(exLog2.getException(),logs.get(1).getException());
        assertNotNull(logs.get(0).getException());
        assertNotNull(logs.get(1).getException());
        assertEquals(exLog1.getAction(),logs.get(0).getAction());
        assertEquals(exLog2.getAction(),logs.get(1).getAction());
    }
}
