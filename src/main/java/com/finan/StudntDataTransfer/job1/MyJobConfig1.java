package com.finan.StudntDataTransfer.job1;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

import com.finan.StudntDataTransfer.entity.StudentEntity;
import com.finan.StudntDataTransfer.repo.StudentRepository;



@Configuration
public class MyJobConfig1 extends DefaultBatchConfiguration {

	private static Logger log = LogManager.getLogger(MyJobConfig1.class);
	
	private static final int chunkSize = 10;
	private static final String filepath = "src/main/java/com/finan/StudntDataTransfer/students1.txt";
	
	public static final String CHECK_DATE = "2023-12-27";
	public static int dataProcessed = 0;

	@Autowired
	private StudentRepository studentRepository;

	@Bean(name = "myJob1")
	public Job myJob1(JobRepository jobRepository, Step step1) {
		return new JobBuilder("myJob1", jobRepository)
				.start(step1)
				.build();
	}
	
	@Bean(name = "step1")
	Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step1", jobRepository)
				.<StudentEntity,StudentEntity>chunk(chunkSize , transactionManager)
				.allowStartIfComplete(true)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}

	private ItemWriter<? super StudentEntity> writer() {
		return new CustomItemWriter(filepath);
	}

	private ItemProcessor<? super StudentEntity, ? extends StudentEntity> processor() {
		return new CustomItemProcessor();
	}

	private ItemReader<? extends StudentEntity> reader() {
		return new RepositoryItemReaderBuilder<StudentEntity>()
				.name("studentReader")
				.saveState(false)
				.repository(studentRepository)
				.sorts(Map.of("id", Direction.ASC))
				.methodName("findAll")
				.build();
	}
}
