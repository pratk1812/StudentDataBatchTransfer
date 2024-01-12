package com.finan.StudntDataTransfer.job3;

import java.io.StringReader;
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

import com.finan.StudntDataTransfer.bean.StudentBean;
import com.finan.StudntDataTransfer.entity.StudentEntity;
import com.finan.StudntDataTransfer.job2.MyJobConfig2;
import com.finan.StudntDataTransfer.repo.StudentRepository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

@Configuration
public class MyJobConfig3 extends DefaultBatchConfiguration{

	private static Logger log = LogManager.getLogger(MyJobConfig2.class);

	private static final String filepath = "src/main/java/com/finan/StudntDataTransfer/students3.txt";
	private static final String CHECK_DATE = "2023-12-27";
	private static final int chunkSize = 10;
	
	@Autowired
	private StudentRepository studentRepository;

	@Bean(name = "myJob3")
	public Job myJob3(JobRepository jobRepository, Step step3) {
		return new JobBuilder("myJob3", jobRepository)
				.start(step3)
				.build();
	}
	
	@Bean(name = "step3")
	Step step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("step2", jobRepository)
				.<StudentEntity,StudentEntity>chunk(chunkSize , transactionManager)
				.allowStartIfComplete(true)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.build();
	}
	
	
	private Unmarshaller unmarshaller() {
		JAXBContext context = null;
		Unmarshaller unmarshaller = null;
		try {
			 context = JAXBContext.newInstance(StudentBean.class);
			 unmarshaller = context.createUnmarshaller();
		} catch (JAXBException e) {
			log.error(e);
		}
		return unmarshaller;
	}

	private ItemWriter<? super StudentEntity> writer() {
		return new CustomItemWriter(filepath);
	}

	private ItemProcessor<? super StudentEntity, ? extends StudentEntity> processor() {
		return item -> {
			StringReader sr = new StringReader(item.getXmlString());
			item.setBean((StudentBean)unmarshaller().unmarshal(sr));
		return item;};
	}

	private ItemReader<? extends StudentEntity> reader() {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(CHECK_DATE);
		} catch (ParseException e) {
			log.error(e);
		}
		return new RepositoryItemReaderBuilder<StudentEntity>()
				.name("job3-customQueryReader")
				.saveState(false)
				.pageSize(chunkSize)
				.repository(studentRepository)
				.methodName("finadAllValid")
				.sorts(Map.of("id", Direction.ASC))
				.arguments(Collections.singletonList(date))
				.build();
	}
}
