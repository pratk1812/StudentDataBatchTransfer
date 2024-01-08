package com.finan.StudntDataTransfer.job2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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
public class MyJobConfig2 extends DefaultBatchConfiguration {
	
	private static Logger log = LogManager.getLogger(MyJobConfig2.class);

	private static final String filepath = "src/main/java/com/finan/StudntDataTransfer/students2.txt";
	private static final String CHECK_DATE = "2023-12-27";
	private static final int chunkSize = 10;
	
	@Autowired
	private StudentRepository studentRepository;

	@Bean(name = "myJob2")
	public Job myJob2(JobRepository jobRepository, Step step2) {
		return new JobBuilder("myJob2", jobRepository)
				.start(step2)
				.build();
	}
	
	@Bean(name = "step2")
	Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step2", jobRepository)
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
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(CHECK_DATE);
		} catch (ParseException e) {
			log.error(e);
		}
		return new RepositoryItemReaderBuilder<StudentEntity>()
				.name("customQueryReader")
				.saveState(false)
				.repository(studentRepository)
				.methodName("finadAllValid")
				.sorts(Map.of("id", Direction.ASC))
				.arguments(Collections.singletonList(date))
				.build();
	}
}
