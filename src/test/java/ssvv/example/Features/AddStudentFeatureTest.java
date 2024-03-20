package ssvv.example.Features;

import domain.Student;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;


import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddStudentFeatureTest {

    StudentXMLRepo studentFileRepository;
    StudentValidator studentValidator;
    TemaXMLRepo temaFileRepository;
    TemaValidator temaValidator;
    NotaXMLRepo notaFileRepository;
    NotaValidator notaValidator;
    private Service service;

    @Before
    public void setUp() throws Exception{
        this.studentFileRepository = new StudentXMLRepo("students.xml");
        this.temaFileRepository = new TemaXMLRepo("homework.xml");
        this.notaFileRepository = new NotaXMLRepo("grades.xml");
        this.studentValidator = new StudentValidator();
        this.temaValidator = new TemaValidator();
        this.notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        this.service = new Service(studentFileRepository,studentValidator, temaFileRepository, temaValidator, notaFileRepository, notaValidator);
    }


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

/////-----------------------------------------------------------------------------------------------------

    @Test
    public void addStudentSuccessfully_TC_1() {
        Student newStudent = new Student("x001", "ion", 1, "ion@stud.com");
        Student response = service.addStudent(newStudent);
        assertEquals(newStudent, response);
        assertEquals("x001", response.getID());
        assertEquals("ion", response.getNume());
        assertEquals(1, response.getGrupa());
        assertEquals("ion@stud.com", response.getEmail());
    }

    @Test
    public void addStudEmptyStrings_TC_2 () {
        Student newStudent = new Student("", "ion", 1, "ion@stud.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Id incorect!", response.getMessage());

        Student newStudent2 = new Student("x002", "", 1, "ion@stud.com");
        Throwable response2 = assertThrows(ValidationException.class, () -> service.addStudent(newStudent2));
        assertEquals("Nume incorect!", response2.getMessage());
    }

    @Test
    public void addStudNullFields_TC_3 () {
        Student newStudent = new Student(null, "ion", 1, "ion@stud.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Id incorect!", response.getMessage());

        Student newStudent2 = new Student("x002", null, 1, "ion@stud.com");
        Throwable response2 = assertThrows(ValidationException.class, () -> service.addStudent(newStudent2));
        assertEquals("Nume incorect!", response2.getMessage());

        Student newStudent3 = new Student("x003", "ion", 1, null);
        Throwable response3 = assertThrows(ValidationException.class, () -> service.addStudent(newStudent3));
        assertEquals("Email incorect!", response3.getMessage());
    }


    @Test
    public void addStudIdNotUnique_TC_4() {
        Student newStudent = new Student("x001", "ion", 1, "ion@stud.com");
        assertThrows(Exception.class, () -> service.addStudent(newStudent));
    }

    @Test
    public void addStudSpecialChars_TC_5() {
        Student newStudent = new Student("x001", "i0%n", 1, "ion@stud.com");
        assertThrows(Exception.class, () -> service.addStudent(newStudent));
    }

    @Test
    public void addStudInvalidGroup_TC_6 () {
        Student newStudent = new Student("x001", "ion", -1, "ion@stud.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Grupa incorecta!", response.getMessage());
    }

    @Test
    public void addStudInvalidGroup_TC_7 () {
        Student newStudent = new Student("x001", "ion", Integer.MAX_VALUE + 1, "ion@stud.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Grupa incorecta!", response.getMessage());
    }

    @Test
    public void addStudInvalidEmail_TC_8 () {
        Student newStudent = new Student("x001", "ion", 1, "ion.com");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Email incorect!", response.getMessage());
    }

    @Test
    public void addStudInvalidEmail_TC_9 () {
        Student newStudent = new Student("x001", "ion", 1, "ion@stud");
        Throwable response = assertThrows(ValidationException.class, () -> service.addStudent(newStudent));
        assertEquals("Email incorect!", response.getMessage());
    }

    @Test
    public void addStudValidBVA_TC_10 () {
        Student newStudent = new Student("x002", "ion", 0, "ion@stud.com");
        Student response = service.addStudent(newStudent);
        assertEquals(newStudent, response);
        assertEquals("x002", response.getID());
        assertEquals("ion", response.getNume());
        assertEquals(0, response.getGrupa());
        assertEquals("ion@stud.com", response.getEmail());
    }

    @Test
    public void addStudValidBVA_TC_11 () {
        Student newStudent = new Student("x003", "ion", Integer.MAX_VALUE - 1, "ion@stud.com");
        Student response = service.addStudent(newStudent);
        assertEquals(newStudent, response);
        assertEquals("x003", response.getID());
        assertEquals("ion", response.getNume());
        assertEquals(Integer.MAX_VALUE - 1, response.getGrupa());
        assertEquals("ion@stud.com", response.getEmail());
    }

    @Test
    public void addStudValidBVA_TC_12 () {
        Student newStudent = new Student("x003", "ion", Integer.MAX_VALUE, "ion@stud.com");
        Student response = service.addStudent(newStudent);
        assertEquals(newStudent, response);
        assertEquals("x003", response.getID());
        assertEquals("ion", response.getNume());
        assertEquals(Integer.MAX_VALUE, response.getGrupa());
        assertEquals("ion@stud.com", response.getEmail());
    }
}