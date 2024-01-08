package com.finan.StudntDataTransfer.bean;

import org.apache.commons.lang3.StringUtils;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentBean {

	@XmlElement
    private long id;

	@XmlElement
    private String name;

    @XmlElement(name = "class")
    private String classString;

    @XmlElement
    private String division;

    @XmlElement
    private String address;

    @XmlElement
    private long fee;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassString() {
		return classString;
	}

	public void setClassString(String classString) {
		this.classString = classString;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}
    
	@Override
	public String toString() {
		String[] formattedStrings = {
			StringUtils.substringBefore(name, " "),
			StringUtils.leftPad(classString, 2, '0'),
			StringUtils.rightPad(division, 2, '0'),
			String.valueOf(id),
			StringUtils.join(address.replaceAll("-", ""), ""),
			StringUtils.leftPad(String.valueOf(fee/1000), 4, '0')
		};
		return StringUtils.join(formattedStrings, "|");
	}
}
