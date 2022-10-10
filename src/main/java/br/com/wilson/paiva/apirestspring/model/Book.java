package br.com.wilson.paiva.apirestspring.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String author;
    @Column(name = "launch_date")
    @Temporal(TemporalType.DATE)
    private Date launchDate;
    @Column
    private Double price;
    @Column
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

    public void setLaunchDate(Date launch_date) {
        this.launchDate = launch_date;
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
        if (!(o instanceof Book book)) return false;

        if (getId() != null ? !getId().equals(book.getId()) : book.getId() != null) return false;
        if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
        if (getLaunchDate() != null ? !getLaunchDate().equals(book.getLaunchDate()) : book.getLaunchDate() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(book.getPrice()) : book.getPrice() != null) return false;
        return getTitle() != null ? getTitle().equals(book.getTitle()) : book.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
        result = 31 * result + (getLaunchDate() != null ? getLaunchDate().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        return result;
    }
}
