package service;

import domain.Student;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.Assert.*;

public class ServiceTest {
    StudentValidator studentValidator = new StudentValidator();
    TemaValidator temaValidator = new TemaValidator();
    String filenameStudent = "fisiere/Studenti.xml";
    String filenameTema = "fisiere/Teme.xml";
    String filenameNota = "fisiere/Note.xml";
    StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
    TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
    NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
    NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
    private Service service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);;


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
        assertEquals("Id incorect!", response.getMessage());
    }
}