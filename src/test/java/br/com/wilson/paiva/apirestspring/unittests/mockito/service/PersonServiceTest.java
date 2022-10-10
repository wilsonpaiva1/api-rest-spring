package br.com.wilson.paiva.apirestspring.unittests.mockito.service;

import br.com.wilson.paiva.apirestspring.data.vo.v1.PersonVO;
import br.com.wilson.paiva.apirestspring.exceptions.RequiredObjectlsNullException;
import br.com.wilson.paiva.apirestspring.model.Person;
import br.com.wilson.paiva.apirestspring.repositories.PersonRepository;
import br.com.wilson.paiva.apirestspring.service.PersonService;
import br.com.wilson.paiva.apirestspring.unittests.mockito.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    MockPerson input;

    @InjectMocks
    private PersonService service;
    @Mock
    PersonRepository personRepository;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockPerson();
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void findById() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());

    }

    @Test
    void create() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);
        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(personRepository.save(entity)).thenReturn(persisted);
        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());
    }

    @Test
    void createWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectlsNullException.class,() -> {
            service.create(null);
        });
        String expectedMessage ="It is not allowed to persist a null object!";
        String actualMessage   = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Person entity = input.mockEntity(1);
        Person persisted = entity;
        persisted.setId(1L);

        PersonVO vo = input.mockVO(1);
        vo.setKey(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(personRepository.save(entity)).thenReturn(persisted);
        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
        assertEquals("Addres Test1",result.getAddress());
        assertEquals("First Name Test1",result.getFirstName());
        assertEquals("Last Name Test1",result.getLastName());
        assertEquals("Female",result.getGender());

    }

    @Test
    void updateWithNullPerson() {
        Exception exception = assertThrows(RequiredObjectlsNullException.class,() -> {
            service.update(null);
        });
        String expectedMessage ="It is not allowed to persist a null object!";
        String actualMessage   = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Person entity = input.mockEntity(1);
        entity.setId(1L);
        when(personRepository.findById(1L)).thenReturn(Optional.of(entity));
        service.delete(1L);
    }
}