package bg.softuni.hotelagency.event;

import bg.softuni.hotelagency.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class LogEvent {

    private final LogService logService;
    private final Logger LOGGER = LoggerFactory.getLogger(LogEvent.class);

    public LogEvent(LogService logService) {
        this.logService = logService;
    }


    @Async
    @Transactional
    @Scheduled(cron = "0 59 23 */1 * 7")
    public void cleanLog() {
        logService.cleanLog();
        LOGGER.info("Log history cleaned");
    }

    @Async
    @Transactional
    @Scheduled(cron = "0 0 18 */1 * *")
    public void cleanExceptionLog() {
        logService.cleanExceptions();
        LOGGER.info("Exception history cleaned");
    }
}
