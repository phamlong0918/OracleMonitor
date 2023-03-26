package com.example.OracleMonitor.service;

import com.example.OracleMonitor.entity.ReportEntity;
import com.example.OracleMonitor.repository.DBAdmin.ReportRepository;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@EnableScheduling
@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduledService {

  private final ReportRepository reportRepository;
  private final Set<String> lstMonitoringSqlId = new HashSet<>();

  @Scheduled(fixedRateString = "10000")
  public void callDailyLuckyExpiredJob() throws IOException {
    log.info("Job schedule is running");

    List<String> runningSqlId = reportRepository.findExecutingSqlId();

    lstMonitoringSqlId.addAll(runningSqlId);

    if (CollectionUtils.isEmpty(lstMonitoringSqlId)) {
      log.warn("There is no sql to monitoring");
      return;
    }

    for(String sqlId : lstMonitoringSqlId) {
      String sqlName = reportRepository.findSqlName(sqlId);
      sqlName += ".html";
      ReportEntity str = reportRepository.findReport(sqlId);
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(sqlName));
        writer.write(str.getReport());
        writer.close();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
