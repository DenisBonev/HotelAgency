package bg.softuni.hotelagency.service;


import bg.softuni.hotelagency.model.entity.Log;

import java.util.List;
import java.util.Map;

public interface LogService {
    void createLog(String action,String exception);

    void createRegisterLog(Long result);

    void cleanLog();

    void cleanExceptions();

    List<Log> getRegisterLog();

    List<Log> getExceptionLog();

    Map<String,Integer> getRegisterStats();

    Map<String,Integer> getExceptionsThrownStats();
}
