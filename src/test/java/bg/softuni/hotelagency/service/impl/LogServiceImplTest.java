package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Log;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.repository.LogRepository;
import bg.softuni.hotelagency.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LogServiceImplTest {

    private LogServiceImpl serviceToTest;
    private Log regLog1, regLog2, exLog1, exLog2;
    private User user;

    @Mock
    LogRepository logRepository;
    @Mock
    UserService userService;


    @BeforeEach
    public void setUp() {
        regLog1 = new Log();
        regLog1.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,5,11,0));//MONDAY
        regLog2 = new Log();
        regLog2.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,6,11,0));//TUESDAY
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
        assertEquals(regLog1.getAction(), logs.get(0).getAction());
        assertEquals(regLog2.getAction(), logs.get(1).getAction());
    }

    @Test
    public void testGetExceptionLog() {
        when(logRepository.getLogsByExceptionNotNull()).thenReturn(List.of(exLog1, exLog2));

        List<Log> logs = serviceToTest.getExceptionLog();

        assertEquals(2, logs.size());
        assertEquals(exLog1.getException(), logs.get(0).getException());
        assertEquals(exLog2.getException(), logs.get(1).getException());
        assertNotNull(logs.get(0).getException());
        assertNotNull(logs.get(1).getException());
        assertEquals(exLog1.getAction(), logs.get(0).getAction());
        assertEquals(exLog2.getAction(), logs.get(1).getAction());
    }

    @Test
    public void testCleanLog() {
        serviceToTest.cleanLog();

        Mockito.verify(logRepository).deleteLogsByExceptionIsNull();
    }

    @Test
    public void testCleanExceptions() {
        serviceToTest.cleanExceptions();

        Mockito.verify(logRepository).deleteLogsByExceptionNotNull();
    }

    @Test
    public void testGetRegisterStats() {
        Log regLog3 = new Log();
        regLog3.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,7,11,0));//WEDNESDAY
        Log regLog4 = new Log();
        regLog4.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,8,11,0));//THURSDAY
        Log regLog5 = new Log();
        regLog5.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,9,11,0));//FRIDAY
        Log regLog6 = new Log();
        regLog6.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,10,11,0));//SATURDAY
        Log regLog7 = new Log();
        regLog7.setAction("register").
                setException(null).
                setDateTime(LocalDateTime.of(2021,4,11,11,0));//SUNDAY

        when(logRepository.getLogsByExceptionIsNull())
                .thenReturn(List.of(regLog1,regLog2,regLog3,regLog4,regLog5,regLog6,regLog7));

        Map<String,Integer> result = serviceToTest.getRegisterStats();

        assertEquals(1,result.get(DayOfWeek.MONDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.TUESDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.WEDNESDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.THURSDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.FRIDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.SATURDAY.toString().toLowerCase(Locale.ROOT)));
        assertEquals(1,result.get(DayOfWeek.SUNDAY.toString().toLowerCase(Locale.ROOT)));
    }
}
