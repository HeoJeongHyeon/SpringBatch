package com.example.batchtest.controller;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public MainController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    @GetMapping("/first")
    public String firstApi(@RequestParam("value") String value)throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data", value)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("firstJob"), jobParameters);

        return "ok";
    }

    @GetMapping("/second")
    public String secondApi(@RequestParam("value") String value)throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data", value)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("secondJob"), jobParameters);

        return "ok";
    }

    @GetMapping("/third")
    public String thridApi(@RequestParam("value") String value)throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("data", value)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("ThirdJob"), jobParameters);

        return "ok";
    }

}
