package bg.softuni.hotelagency.service.impl;

import bg.softuni.hotelagency.model.entity.Log;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.repository.LogRepository;
import bg.softuni.hotelagency.service.LogService;
import bg.softuni.hotelagency.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        User user;
        if (principal == null) {
            user = userService.getUserByEmail("unknown@abv.bg");
        } else {
            user = userService.getUserByEmail(principal.getName());
        }
        Log log = new Log().
                setException(exception).
                setAction(action).
                setDateTime(LocalDateTime.now()).
                setUser(user);
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

    @Override
    public Map<String, Integer> getRegisterStats() {
        List<Log> logs = logRepository.
                getLogsByExceptionIsNull();
        return mapStats(logs);
    }


    @Override
    public Map<String, Integer> getExceptionsThrownStats() {
        List<Log> logs = logRepository.getLogsByExceptionNotNull();
        return mapStats(logs);
    }

    private Map<String,Integer> mapStats(List<Log> logs) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put(DayOfWeek.MONDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.TUESDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.WEDNESDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.THURSDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.FRIDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.SATURDAY.toString().toLowerCase(Locale.ROOT), 0);
        stats.put(DayOfWeek.SUNDAY.toString().toLowerCase(Locale.ROOT), 0);

        for (Log l : logs) {
            switch (l.getDateTime().getDayOfWeek()) {
                case MONDAY:
                    stats.put(DayOfWeek.MONDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.MONDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case TUESDAY:
                    stats.put(DayOfWeek.TUESDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.TUESDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case WEDNESDAY:
                    stats.put(DayOfWeek.WEDNESDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.WEDNESDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case THURSDAY:
                    stats.put(DayOfWeek.THURSDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.THURSDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case FRIDAY:
                    stats.put(DayOfWeek.FRIDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.FRIDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case SATURDAY:
                    stats.put(DayOfWeek.SATURDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.SATURDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
                case SUNDAY:
                    stats.put(DayOfWeek.SUNDAY.toString().toLowerCase(Locale.ROOT),
                            stats.get(DayOfWeek.SUNDAY.toString().toLowerCase(Locale.ROOT)) + 1);
                    break;
            }
        }
        return stats;
    }
}
