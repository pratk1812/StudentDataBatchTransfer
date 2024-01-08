package com.finan.StudntDataTransfer.job1;

import static com.finan.StudntDataTransfer.job1.MyJobConfig.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.finan.StudntDataTransfer.entity.RowStatus;
import com.finan.StudntDataTransfer.entity.StudentEntity;

public class CustomItemWriter implements ItemWriter<StudentEntity> {

	private static Logger log = LogManager.getLogger(MyJobConfig.class);

	private File file;
	
	CustomItemWriter(String filepath){
		file = new File(filepath);
		log.info("Output file exists :: " + file.exists());
		log.info("Output file executable :: " + file.canExecute());
		log.info("Output file writeable :: " + file.canWrite());
	}
	
	@Override
	public void write(Chunk<? extends StudentEntity> chunk) throws Exception {

		try(
				FileWriter fw = new FileWriter(file,true);
				BufferedWriter writer = new BufferedWriter(fw) ){
			for (StudentEntity entity : chunk) {
				String myString = entity.getBean().toString();
				writer.write(myString);
				writer.newLine();
				
				entity.setStatus(RowStatus.Processed);
			}
			log.info("writing chunk");
			log.info("data process : " + dataProcessed);
		}
		
	}

}
