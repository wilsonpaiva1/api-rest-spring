package br.com.wilson.paiva.apirestspring.integrationtests.vo;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;


@XmlRootElement
public class BookVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    public BookVO() {
    }

    private Long id;
    private String author;
    private Date launchDate;
    private Double price;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookVO bookVO)) return false;
        if (!super.equals(o)) return false;

        if (getId() != null ? !getId().equals(bookVO.getId()) : bookVO.getId() != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(bookVO.getAuthor()) : bookVO.getAuthor() != null) return false;
        if (getLaunchDate() != null ? !getLaunchDate().equals(bookVO.getLaunchDate()) : bookVO.getLaunchDate() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(bookVO.getPrice()) : bookVO.getPrice() != null) return false;
        return getTitle() != null ? getTitle().equals(bookVO.getTitle()) : bookVO.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getLaunchDate() != null ? getLaunchDate().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }
}
