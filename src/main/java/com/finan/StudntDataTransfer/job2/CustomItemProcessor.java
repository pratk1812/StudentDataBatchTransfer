package com.finan.StudntDataTransfer.job2;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.finan.StudntDataTransfer.bean.StudentBean;
import com.finan.StudntDataTransfer.entity.StudentEntity;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class CustomItemProcessor implements ItemProcessor<StudentEntity,StudentEntity> {
	
	private static Logger log = LogManager.getLogger(CustomItemProcessor.class);

	
	private Unmarshaller unmarshaller;
	
	public CustomItemProcessor() {
		try {
			JAXBContext context = JAXBContext.newInstance(StudentBean.class);			
			unmarshaller = context.createUnmarshaller();
			
		} catch (JAXBException e) {
			log.error(e);
		} 
	}
	
	@Override
	public StudentEntity process(StudentEntity item) throws Exception {
			StringReader sr = new StringReader(item.getXmlString());
			item.setBean((StudentBean)unmarshaller.unmarshal(sr));
			return item;
	}

}
