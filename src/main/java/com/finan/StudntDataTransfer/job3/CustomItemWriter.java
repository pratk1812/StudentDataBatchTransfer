package com.finan.StudntDataTransfer.job3;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.core.io.FileSystemResource;

import com.finan.StudntDataTransfer.entity.RowStatus;
import com.finan.StudntDataTransfer.entity.StudentEntity;

public class CustomItemWriter extends FlatFileItemWriter<StudentEntity> {

	public CustomItemWriter(String filepath) {
		super();
		setName("job3-ItemWriter");
		setResource(new FileSystemResource(filepath));
		setLineAggregator(new PassThroughLineAggregator<>());
	}

	@Override
	public String doWrite(Chunk<? extends StudentEntity> items) {
		items.forEach(item->item.setStatus(RowStatus.Processed));
		return super.doWrite(items);
	}
	
}
