package com.finan.StudntDataTransfer.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.finan.StudntDataTransfer.bean.StudentBean;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "StudentsXML")
public class StudentEntity {

	@Id
	private long id;
	
	@Lob
	@Column(name = "xml_string")
	private String xmlString;
	
	@Enumerated(EnumType.ORDINAL)
	private RowStatus status;
	
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    private Date date;
	
	@Transient
	private StudentBean bean;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getXmlString() {
		return xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public RowStatus getStatus() {
		return status;
	}

	public void setStatus(RowStatus status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public StudentBean getBean() {
		return bean;
	}

	public void setBean(StudentBean bean) {
		this.bean = bean;
	}

	@Override
	public String toString() {
		return "StudentEntity [id=" + id + ", xmlString=" + xmlString + ", status=" + status + ", date=" + date + "]";
	}
}
