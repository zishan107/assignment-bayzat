package com.bayzdelivery.controller;

import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.PersonRepository;
import com.bayzdelivery.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PersonControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @Autowired
    private TestRestTemplate template;

    @Autowired
    PersonRepository personRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
    }

    @Test
    public void testRegisterPerson() throws Exception {
        Person person = new Person();
        person.setName("John Doe");
        person.setEmail("john.doe@example.com");
        person.setRole("customer");

        when(personService.save(any(Person.class))).thenReturn(person);

        mockMvc.perform(post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"role\": \"customer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"role\": \"customer\"}"));
    }

    @Test
    public void testGetAllPersons() throws Exception {
        Person person1 = new Person();
        person1.setName("John Doe");
        person1.setEmail("john.doe@example.com");
        person1.setRole("customer");

        Person person2 = new Person();
        person2.setName("Jane Doe");
        person2.setEmail("jane.doe@example.com");
        person2.setRole("delivery_man");

        List<Person> persons = Arrays.asList(person1, person2);

        when(personService.getAll()).thenReturn(persons);

        mockMvc.perform(get("/api/person"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"role\": \"customer\"}, {\"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\", \"role\": \"delivery_man\"}]"));
    }

}
