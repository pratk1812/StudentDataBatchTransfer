package com.finan.StudntDataTransfer.repo;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.finan.StudntDataTransfer.entity.StudentEntity;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

	@Query("SELECT s FROM StudentEntity s WHERE s.status = 1 AND s.date >= :date")
    Page<StudentEntity> finadAllValid(@Param("date") Date date, Pageable pageable);
}
/*	("2024-01-06");
	("2024-01-03");
	("2023-12-27"); //check date
	("2023-11-15");
	("2023-09-22");
	("2023-07-13");*/
