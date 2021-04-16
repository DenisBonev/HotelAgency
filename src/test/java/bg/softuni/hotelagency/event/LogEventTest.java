package bg.softuni.hotelagency.event;

import bg.softuni.hotelagency.service.LogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class LogEventTest {

    private LogEvent logEvent;

    @Mock
    LogService logService;

    @BeforeEach
    public void setUp(){
        logEvent = new LogEvent(logService);
    }

    @Test
    public void testCleanLog(){
        logEvent.cleanLog();
        Mockito.verify(logService).cleanLog();
    }
    @Test
    public void testCleanExceptionLog(){
        logEvent.cleanExceptionLog();
        Mockito.verify(logService).cleanExceptions();
    }
}
