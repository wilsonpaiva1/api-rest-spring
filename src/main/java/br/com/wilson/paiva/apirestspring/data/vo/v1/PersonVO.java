package br.com.wilson.paiva.apirestspring.data.vo.v1;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import jakarta.persistence.Column;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


@JsonPropertyOrder({"id","firstName","lastName","address","gender","enabled"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @Mapping("id")
    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    private Long key;

    private String firstName;

    private String lastName;
    private String address;

    private String gender;

    private Boolean enabled;

    public PersonVO() {}


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVO personVO)) return false;
        if (!super.equals(o)) return false;

        if (getKey() != null ? !getKey().equals(personVO.getKey()) : personVO.getKey() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(personVO.getFirstName()) : personVO.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(personVO.getLastName()) : personVO.getLastName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(personVO.getAddress()) : personVO.getAddress() != null)
            return false;
        if (getGender() != null ? !getGender().equals(personVO.getGender()) : personVO.getGender() != null)
            return false;
        return getEnabled() != null ? getEnabled().equals(personVO.getEnabled()) : personVO.getEnabled() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getEnabled() != null ? getEnabled().hashCode() : 0);
        return result;
    }
}