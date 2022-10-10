package br.com.wilson.paiva.apirestspring.data.vo.v2;



import java.io.Serializable;
import java.util.Date;

public class PersonVOV2 implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private String gender;

    private Date birthDay;

    public PersonVOV2() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonVOV2 that)) return false;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
            return false;
        if (getAddress() != null ? !getAddress().equals(that.getAddress()) : that.getAddress() != null) return false;
        if (getGender() != null ? !getGender().equals(that.getGender()) : that.getGender() != null) return false;
        return getBirthDay() != null ? getBirthDay().equals(that.getBirthDay()) : that.getBirthDay() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getGender() != null ? getGender().hashCode() : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
        return result;
    }
}