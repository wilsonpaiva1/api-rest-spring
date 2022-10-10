package br.com.wilson.paiva.apirestspring.controller;

import br.com.wilson.paiva.apirestspring.data.vo.v1.PersonVO;

import br.com.wilson.paiva.apirestspring.data.vo.v2.PersonVOV2;
import br.com.wilson.paiva.apirestspring.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.wilson.paiva.apirestspring.util.MediaType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController()
@RequestMapping("/api/person/v1")
@Tag(name = "People",description = "Endpoints for Managing People")
public class PersonController {

    @Autowired
    private PersonService personService;
    private Logger logger = Logger.getLogger(PersonController.class.getName());
    @GetMapping(produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all People",description = "Finds all People",
    tags = {"People"},
    responses = {
            @ApiResponse(description = "Success",responseCode = "200",
                    content ={
                        @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                        )
                    }),
            @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
            @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
            @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
            @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

    })
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll (
            @RequestParam (value = "page", defaultValue = "0") Integer page,
            @RequestParam (value = "size", defaultValue = "12") Integer size,
            @RequestParam( value = "direction",defaultValue = "asc") String direction
            ){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size,Sort.by(sortDirection,"firstName"));
        return  ResponseEntity.ok(personService.findAll(pageable));
    }

    @GetMapping(value = "/findPersonByName/{firstName}",
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Finds People by Name",description = "Finds People by Name",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success",responseCode = "200",
                            content ={
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName (
            @PathVariable (value = "firstName") String firstName,
            @RequestParam (value = "page", defaultValue = "0") Integer page,
            @RequestParam (value = "size", defaultValue = "12") Integer size,
            @RequestParam( value = "direction",defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction)? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size,Sort.by(sortDirection,"firstName"));
        return  ResponseEntity.ok(personService.findPersonByName(firstName,pageable));
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Finds a Person",description = "Finds a Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success",responseCode = "200",
                            content = @Content( schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content",responseCode = "204",content = @Content),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public PersonVO findById (
            @PathVariable(value = "id") Long id){
        return  personService.findById(id);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "add a new Person",description = "add a new Person by passing in a Json,XML  ou YML representation of ",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Create",responseCode = "200",
                            content = @Content( schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public PersonVO create (
            @RequestBody PersonVO person) {
        return  personService.create(person);
    }

    @PostMapping(value = "/v2",
            consumes = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    public PersonVOV2 createV2 (
            @RequestBody PersonVOV2 person) {
        return  personService.createV2(person);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "update a Person",description = "update a Person by passing in a Json,XML  ou YML representation of ",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Update",responseCode = "200",
                            content = @Content( schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public PersonVO update (
            @RequestBody PersonVO person) {
        return  personService.update(person);
    }


    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML,MediaType.APPLICATION_YML})
    @Operation(summary = "Disable a specific bu you id Person",description = "Disable a specific bu you id Person",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success",responseCode = "200",
                            content = @Content( schema = @Schema(implementation = PersonVO.class))
                    ),
                    @ApiResponse(description = "No Content",responseCode = "204",content = @Content),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public PersonVO disablePerson (
            @PathVariable(value = "id") Long id){
        return  personService.disablePerson(id);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Person",description = "Delete a Person by passing in a Json,XML  ou YML representation of ",
            tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content",responseCode = "204 ",content = @Content),
                    @ApiResponse(description = "Bad Request",responseCode = "400",content = @Content),
                    @ApiResponse(description = "Unauthorized",responseCode = "401",content = @Content),
                    @ApiResponse(description = "Not Found",responseCode = "404",content = @Content),
                    @ApiResponse(description = "Internal Error",responseCode = "500",content = @Content)

            })
    public ResponseEntity<?> delete (@PathVariable(value = "id") Long id){
          personService.delete(id);
          return ResponseEntity.noContent().build();
    }

}
