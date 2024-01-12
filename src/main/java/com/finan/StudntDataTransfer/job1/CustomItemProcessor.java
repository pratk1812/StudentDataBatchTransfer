package com.finan.StudntDataTransfer.job1;

import static com.finan.StudntDataTransfer.job1.MyJobConfig1.*;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.finan.StudntDataTransfer.bean.StudentBean;
import com.finan.StudntDataTransfer.entity.RowStatus;
import com.finan.StudntDataTransfer.entity.StudentEntity;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class CustomItemProcessor implements ItemProcessor<StudentEntity,StudentEntity> {
	
	private static Logger log = LogManager.getLogger(CustomItemProcessor.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private Unmarshaller unmarshaller;
	private Date checkDate;
	
	public CustomItemProcessor() {
		try {
			JAXBContext context = JAXBContext.newInstance(StudentBean.class);
			
			unmarshaller = context.createUnmarshaller();
			checkDate = dateFormat.parse(CHECK_DATE);
			
		} catch (JAXBException e) {
			
		} catch (ParseException e) {
			
		}
	}
	private boolean skipProcess(StudentEntity item) {
		boolean condition1 = item.getStatus()==RowStatus.Processed;
		boolean condition2 = item.getDate().before(checkDate);
		
		boolean skip = condition1|condition2;
		
		return skip;
	}
	
	@Override
	public StudentEntity process(StudentEntity item) throws Exception {
		if(skipProcess(item)) {
			return null;
		}
		else {
			StringReader sr = new StringReader(item.getXmlString());
			item.setBean((StudentBean)unmarshaller.unmarshal(sr));
			++dataProcessed;
			return item;
		}
	}

}
