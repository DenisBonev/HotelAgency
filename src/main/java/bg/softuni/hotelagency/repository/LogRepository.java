package bg.softuni.hotelagency.repository;

import bg.softuni.hotelagency.model.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    void deleteLogsByExceptionIsNull();
    void deleteLogsByExceptionNotNull();
    List<Log> getLogsByExceptionIsNull();
    List<Log> getLogsByExceptionNotNull();
}
