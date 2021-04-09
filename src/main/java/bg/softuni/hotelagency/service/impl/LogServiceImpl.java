package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Log;
import bg.softuni.hotelagency.repository.LogRepository;
import bg.softuni.hotelagency.service.LogService;
import bg.softuni.hotelagency.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final UserService userService;

    public LogServiceImpl(LogRepository logRepository, UserService userService) {
        this.logRepository = logRepository;
        this.userService = userService;
    }

    @Override
    public void createLog(String action, String exception) {
        Authentication principal = SecurityContextHolder
                .getContext()
                .getAuthentication();
        Log log = new Log().
                setException(exception).
                setAction(action).
                setDateTime(LocalDateTime.now()).
                setUser(userService.getUserByEmail(principal.getName()));
        logRepository.save(log);
    }

    @Override
    public void createRegisterLog(Long id) {
        Log log = new Log().
                setAction("register").
                setDateTime(LocalDateTime.now()).
                setUser(userService.getUserById(id));
        logRepository.save(log);
    }

    @Override
    public void cleanLog() {
        logRepository.deleteLogsByExceptionIsNull();
    }

    @Override
    public void cleanExceptions() {
        logRepository.deleteLogsByExceptionNotNull();
    }

    @Override
    public List<Log> getRegisterLog() {
        return logRepository.getLogsByExceptionIsNull();
    }

    @Override
    public List<Log> getExceptionLog() {
        return logRepository.getLogsByExceptionNotNull();
    }

}
