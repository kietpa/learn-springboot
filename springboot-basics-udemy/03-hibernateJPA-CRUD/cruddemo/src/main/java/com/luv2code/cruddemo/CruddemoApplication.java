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

			// queryStudentsByLastName(studentDAO);

			// updateStudent(studentDAO);

			// deleteStudent(studentDAO);

			deleteAllStudents(studentDAO);
		};
	}

	private void deleteAllStudents(StudentDAO studentDAO) {
		System.out.println("deleting all...");
		int rows = studentDAO.deleteAll();

		System.out.println("rows deleted = " + rows);
	}

	private void deleteStudent(StudentDAO studentDAO) {
		int studentId = 2;
		System.out.println("Deleting student id: " + studentId);

		studentDAO.delete(studentId);
	}

	private void updateStudent(StudentDAO studentDAO) {

		System.out.println("getting student with id 1...");
		Student s = studentDAO.findById(1);

		System.out.println("Updating name");
		s.setLastName("Doe");

		studentDAO.update(s);

		System.out.println("Printing updated student:");
		System.out.println(s);
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
