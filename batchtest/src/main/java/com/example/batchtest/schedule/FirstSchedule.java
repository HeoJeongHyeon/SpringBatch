package com.example.batchtest.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@Slf4j
public class FirstSchedule {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public FirstSchedule(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @Scheduled(cron = "10 * * * * *", zone = "Asia/Seoul")
    public void runFirstJob() throws Exception {
        log.info("Schedule Batch Start");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        String date = dateFormat.format(new Date());

        JobParameters jobparameters = new JobParametersBuilder()
                .addString("date", date)
                .toJobParameters();
        jobLauncher.run(jobRegistry.getJob("firstJob"), jobparameters);

    }
}
