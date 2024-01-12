# StudentDataBatchTransfer

## Description
This is a Spring Batch project that uses JPA to read data from a MySQL database and writes a pipe-separated formatted output to a text file. The project contains two jobs in the `MyJobConfig1` and `MyJobConfig2` classes.

- `MyJobConfig1`: Reads all rows and filters rows in the item processor using status and Date fields.
- `MyJobConfig2`: Reads data using JPQL to only read the necessary filtered rows.
- `MyJobConfig3`: Uses a FlatFileItemWriter instead of a custom write logic.

## Repository
The project repository is available at: https://github.com/pratk1812/StudentDataBatchTransfer.git

## Versions
- Spring Boot Starter Parent: 3.2.1
- Java: 17
- Project Version: 0.0.1-SNAPSHOT

## Dependencies
- Spring Boot Starter Batch
- Spring Boot Starter Data JPA
- MySQL Connector/J (runtime)
- Spring Boot Starter Test (test)
- Spring Batch Test (test)
- Jakarta XML Bind API
- Spring OXM
- Apache Commons Lang3

## Build
The project uses the Spring Boot Maven Plugin for building.

## Usage
To use this project, you need to have a MySQL database set up with the necessary tables and data. After setting up the database, you can run the Spring Batch jobs to read the data and write the output to a text file.
