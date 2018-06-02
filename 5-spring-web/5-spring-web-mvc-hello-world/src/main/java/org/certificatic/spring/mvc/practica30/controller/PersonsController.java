package org.certificatic.spring.mvc.practica30.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.xml.ws.ResponseWrapper;

import org.certificatic.spring.mvc.practica30.controller.advice.RestResponseError;
import org.certificatic.spring.validation.practica30.parte1.domain.Person;
import org.certificatic.spring.validation.practica30.parte1.domain.Persons;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// Anotar Controller
@Controller
// Anotar request mapping "/persons"
@RequestMapping("/persons")
public class PersonsController {

	private List<Person> persons = Collections.synchronizedList(new ArrayList<Person>());

	@PostConstruct
	private void init() {
		for (int i = 0; i < 3; i++) {
			Person p = new Person();
			p.setId(i + 1);
			p.setName("Ivan " + (i + 1));
			p.setAge(28 + i + 1);
			persons.add(p);
		}
	}

	// Anotar request mapping "/", "", con metodo GET y produciendo json y xml
	@RequestMapping(value ={ "/", "" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseBody
	public Persons getAllPersons() {
		return new Persons(persons);
	}

	// Anotar request mapping "/{id}", con metodo GET y produciendo json y xml
	// Anotar response status ok
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(code = HttpStatus.OK)
	@ResponseBody
	public Person getPerson(@PathVariable Integer id) {
		return persons.get(id - 1);
	}

	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ResponseBody
	public RestResponseError indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		return RestResponseError.builder().status(HttpStatus.NOT_FOUND.value()).
				exception(IndexOutOfBoundsException.class.getName()).
				message("Un mensaje de error bonito para el usuario =(").build();
	}

	// Anotar request mapping "/", "", con metodo POST y produciendo json y xml
	// Anotar response status NO CONTENT
	@RequestMapping(value = { "/", "" }, method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void createPerson(@RequestBody Person person) {
		persons.add(person);
	}

}
