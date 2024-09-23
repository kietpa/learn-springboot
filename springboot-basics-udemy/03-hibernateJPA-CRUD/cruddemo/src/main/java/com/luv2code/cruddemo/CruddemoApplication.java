package com.luv2code.cruddemo;

import com.luv2code.cruddemo.dao.StudentDAO;
import com.luv2code.cruddemo.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
		return runner -> {
			// readStudent(studentDAO);
			
			// queryAllStudents(studentDAO);

			queryStudentsByLastName(studentDAO);
		};
	}

	private void queryStudentsByLastName(StudentDAO studentDAO) {
		System.out.println("Getting students w/name doe");
		List<Student> students = studentDAO.findByLastName("doe");

		System.out.println(students);
	}

	private void queryAllStudents(StudentDAO studentDAO) {
		// get all
		System.out.println("Getting a list o students...");
		List<Student> students = studentDAO.findAll();

		for (Student s:students) {
			System.out.println(s);
		}
	}

	private void readStudent(StudentDAO studentDAO) {
		// create student object
		System.out.println("Creating new student object...");
		Student tempStudent = new Student("John", "Doe", "jd@gmail.com");

		// save
		System.out.println("Saving student...");
		studentDAO.save(tempStudent);

		// display id of saved student
		System.out.println("Generated studentId: " + tempStudent.getId());

		System.out.println("Retrieving student...");

		Student tempAgain = studentDAO.findById(tempStudent.getId());

		System.out.println("found student: " + tempAgain.toString());

		System.out.println(tempAgain == tempStudent);
	}
}
