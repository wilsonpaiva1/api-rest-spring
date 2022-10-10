package br.com.wilson.paiva.apirestspring.service;
import java.util.logging.Logger;

import br.com.wilson.paiva.apirestspring.controller.BookController;
import br.com.wilson.paiva.apirestspring.controller.PersonController;
import br.com.wilson.paiva.apirestspring.data.vo.v1.BookVO;
import br.com.wilson.paiva.apirestspring.data.vo.v1.PersonVO;
import br.com.wilson.paiva.apirestspring.exceptions.RequiredObjectlsNullException;
import br.com.wilson.paiva.apirestspring.exceptions.ResourceNotFoundException;
import br.com.wilson.paiva.apirestspring.mapper.DozerMapper;
import br.com.wilson.paiva.apirestspring.model.Book;
import br.com.wilson.paiva.apirestspring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;
    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {

        logger.info("Finding all books!");

        var booksPage = repository.findAll(pageable);

        var booksVOs = booksPage.map(p -> DozerMapper.parseObject(p, BookVO.class));
        booksVOs.map(
                p -> p.add(
                        linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        Link findAllLink = linkTo(
                methodOn(BookController.class)
                        .findAll(pageable.getPageNumber(),
                                pageable.getPageSize(),
                                "asc")).withSelfRel();

        return assembler.toModel(booksVOs, findAllLink);
    }


    public BookVO findById(Long id) {

        logger.info("Finding one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var vo = DozerMapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book) {

        if (book == null) throw new RequiredObjectlsNullException();

        logger.info("Creating one book!");
        var entity = DozerMapper.parseObject(book, Book.class);
        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book) {

        if (book == null) throw new RequiredObjectlsNullException();

        logger.info("Updating one book!");

        var entity = repository.findById(book.getKey())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        var vo =  DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one book!");

        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(entity);
    }

    public PagedModel<EntityModel<BookVO>> findBookByTitle(String title, Pageable pageable) {
        logger.info("Finding Book by Title");
        var personPage = repository.findBookByTitle(title,pageable);
        var personVosPage = personPage.map(p -> DozerMapper.parseObject(p, BookVO.class) );
        personVosPage.map(
                p -> p.add(
                        linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(BookController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();
        return  assembler.toModel(personVosPage,link);
    }
}
