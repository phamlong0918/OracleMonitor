package com.example.OracleMonitor.repository.DBAdmin;

import com.example.OracleMonitor.entity.ReportEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {

 @Query(value = "SELECT DBMS_SQLTUNE.REPORT_SQL_MONITOR (sql_id => :sqlId, type => 'ACTIVE', report_level => 'ALL') AS report FROM dual", nativeQuery = true)
 ReportEntity findReport(String sqlId);

 @Query(value = "select distinct SQL_ID from v$session where STATUS = 'ACTIVE'", nativeQuery = true)
 List<String> findExecutingSqlId();

 @Query(value = "select distinct TARGET || '.' || SQL_ID as SQL_NAME from V$SESSION_LONGOPS where SQL_ID = :sqlId", nativeQuery = true)
 String findSqlName(String sqlId);
}
