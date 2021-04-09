package bg.softuni.hotelagency.service;


import bg.softuni.hotelagency.model.entity.Log;

import java.util.List;

public interface LogService {
    void createLog(String action,String exception);

    void createRegisterLog(Long result);

    void cleanLog();

    void cleanExceptions();

    List<Log> getRegisterLog();

    List<Log> getExceptionLog();
}
