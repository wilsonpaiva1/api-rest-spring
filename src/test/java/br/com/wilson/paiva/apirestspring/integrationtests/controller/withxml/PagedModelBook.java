package br.com.wilson.paiva.apirestspring.integrationtests.controller.withxml;

import br.com.wilson.paiva.apirestspring.integrationtests.vo.BookVO;
import br.com.wilson.paiva.apirestspring.integrationtests.vo.PersonVO;
import jakarta.xml.bind.annotation.XmlElement;

import java.util.List;

public class PagedModelBook {

    @XmlElement(name = "content")
    private List<BookVO> content;

    public PagedModelBook() {}

    public List<BookVO> getContent() {
        return content;
    }

    public void setContent(List<BookVO> content) {
        this.content = content;
    }
}
