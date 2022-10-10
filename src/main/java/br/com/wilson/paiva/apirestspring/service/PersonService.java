package br.com.wilson.paiva.apirestspring.service;

import br.com.wilson.paiva.apirestspring.controller.PersonController;
import br.com.wilson.paiva.apirestspring.data.vo.v1.PersonVO;
import br.com.wilson.paiva.apirestspring.data.vo.v2.PersonVOV2;
import br.com.wilson.paiva.apirestspring.exceptions.RequiredObjectlsNullException;
import br.com.wilson.paiva.apirestspring.exceptions.ResourceNotFoundException;
import br.com.wilson.paiva.apirestspring.mapper.DozerMapper;
import br.com.wilson.paiva.apirestspring.mapper.custom.PersonMapper;
import br.com.wilson.paiva.apirestspring.model.Person;
import br.com.wilson.paiva.apirestspring.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class PersonService {

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PagedResourcesAssembler<PersonVO> assembler;
    @Autowired
    PersonMapper mapper;
    public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable){
        logger.info("Finding all peaple");
        var personPage = repository.findAll(pageable);
        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p,PersonVO.class) );
        personVosPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();
        return  assembler.toModel(personVosPage,link);
    }

    public PagedModel<EntityModel<PersonVO>> findPersonByName(String firstName, Pageable pageable){
        logger.info("Finding all peaple");
        var personPage = repository.findPersonsByName(firstName,pageable);
        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p,PersonVO.class) );
        personVosPage.map(
                p -> p.add(
                        linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();
        return  assembler.toModel(personVosPage,link);
    }

    public PersonVO findById(Long id) {
        logger.info("Finding on person");

         var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID! "));
         var vo =  DozerMapper.parseObject(entity,PersonVO.class);
         vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
         return vo;
    }

    public PersonVO create(PersonVO person) {

        if (person == null) throw new RequiredObjectlsNullException();
        logger.info("Creating one person");

        var entity = DozerMapper.parseObject(person,Person.class);
        var vo =  DozerMapper.parseObject(repository.save(entity),PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }
    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person with v2!");
        var entity = mapper.convertVoToEntity(person);
        var vo =  mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {
        if (person == null) throw new RequiredObjectlsNullException();
        logger.info("Update  one person");
        var entity = repository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID! "));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());
        var vo =  DozerMapper.parseObject(repository.save(entity),PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    @Transactional
    public PersonVO disablePerson(Long id) {
        logger.info("Disabling on person");
        repository.disablePerson(id);
        var entity =  repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID! "));
        var vo =  DozerMapper.parseObject(entity,PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one person");
        var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID! "));
        repository.delete(entity);
    }

}
