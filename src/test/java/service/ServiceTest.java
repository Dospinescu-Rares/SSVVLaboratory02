package service;

import domain.Student;
import org.junit.Test;
import validation.ValidationException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.Assert.*;

public class ServiceTest {

    private Service service;


    @Test
    public void addStudent() {
        Student newStudent = new Student("1", "Mihai", 2, "mihai@yahoo.com");
        Student response = service.addStudent(newStudent);
        assertEquals("1", response.getID());
        assertEquals("Mihai", response.getNume());
        assertEquals(2, response.getGrupa());
        assertEquals("mihai@yahoo.com", response.getEmail());
    }

    @Test
    public void addStudentInvalidId() {
        Student newStudent = new Student("", "Mihai", 2, "mihai@yahoo.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Id incorect!", response.getMessage());

        Student newStudent2 = new Student(null, "Mihai", 2, "mihai@yahoo.com");
        Throwable response2 = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("\"Id incorect!", response.getMessage());
    }
}