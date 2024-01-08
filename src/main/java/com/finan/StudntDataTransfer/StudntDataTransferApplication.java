package com.finan.StudntDataTransfer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudntDataTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudntDataTransferApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(JobLauncher jobLauncher, 
			@Qualifier("myJob2") Job myJob2) {
		return args -> jobLauncher.run(myJob2, new JobParameters());	
	}
}
