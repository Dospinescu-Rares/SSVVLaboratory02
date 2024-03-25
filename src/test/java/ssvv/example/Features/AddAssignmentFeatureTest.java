package ssvv.example.Features;

import domain.Tema;
import org.junit.After;
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

public class AddAssignmentFeatureTest {

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
    public void addAssignmentSuccessful() throws Exception {
        Tema newTema = new Tema("11","tema test", 3, 3);

        Tema response = service.addTema(newTema);

        assertEquals("11", response.getID());
        assertEquals("tema test", response.getDescriere());
        assertEquals(3, response.getDeadline());
        assertEquals(3, response.getPrimire());
    }

    @Test
    public void addAssignmentInvalid() throws Exception {
        Tema newTema = new Tema(null,"tema test 2", 11, 10);

        Throwable response = assertThrows(ValidationException.class, () -> service.addTema(newTema));
        assertEquals("Numar tema invalid!", response.getMessage());


    }



    @After
    public void tearDown(){
        studentFileRepository.delete("1");
        studentFileRepository.delete("x001");
        studentFileRepository.delete("x002");
        studentFileRepository.delete("x003");
    }

}
