package br.com.wilson.paiva.apirestspring.mapper.custom;

import br.com.wilson.paiva.apirestspring.data.vo.v2.PersonVOV2;
import br.com.wilson.paiva.apirestspring.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
    public PersonVOV2 convertEntityToVo(Person person){
        PersonVOV2 vov2 = new PersonVOV2();
        vov2.setId(person.getId());
        vov2.setAddress(person.getAddress());
        vov2.setBirthDay(new Date());
        vov2.setFirstName(person.getFirstName());
        vov2.setLastName(person.getLastName());
        vov2.setGender(person.getGender());
        return vov2;
    }
    public Person convertVoToEntity(PersonVOV2 person){
        Person entity = new Person();
        entity.setId(person.getId());
        entity.setAddress(person.getAddress());
        //entity.setBirthDay(new Date());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setGender(person.getGender());
        return entity;
    }
}
