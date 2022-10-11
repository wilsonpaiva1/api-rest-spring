package br.com.wilson.paiva.apirestspring.integrationtests.controller.withyaml;

import br.com.wilson.paiva.apirestspring.configs.TestConfigs;
import br.com.wilson.paiva.apirestspring.data.vo.v1.security.AccountCredentialsVO;
import br.com.wilson.paiva.apirestspring.integrationtests.controller.withyaml.mapper.YamlMapper;
import br.com.wilson.paiva.apirestspring.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.wilson.paiva.apirestspring.integrationtests.vo.PersonVO;
import br.com.wilson.paiva.apirestspring.integrationtests.vo.TokenVO;
import br.com.wilson.paiva.apirestspring.unittests.vo.pagedmodels.PagedModelPerson;
import br.com.wilson.paiva.apirestspring.unittests.vo.wrappers.WrapperPersonVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YamlMapper objectMapper;

    private static PersonVO personVO;

    @BeforeAll
    public static void setup() {
        objectMapper = new YamlMapper();
        personVO = new PersonVO();
    }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {

        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");

        var accessToken = given()
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(user, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenVO.class, objectMapper)
                .getAccessToken();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    @Order(1)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(personVO, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, objectMapper);

        personVO = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());

        assertTrue(persistedPerson.getId() > 0);

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(2)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        personVO.setLastName("Piquet Souto Maior");

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .body(personVO, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, objectMapper);

        personVO = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertTrue(persistedPerson.getEnabled());
        assertEquals(personVO.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(3)
    public void testDisablePersonById() throws JsonProcessingException {

        var persistedPerson =
                given()
                        .spec(specification)
                        .config(
                                RestAssuredConfig
                                        .config()
                                        .encoderConfig(EncoderConfig.encoderConfig()
                                                .encodeContentTypeAs(
                                                        TestConfigs.CONTENT_TYPE_YML,
                                                        ContentType.TEXT)))
                        .contentType(TestConfigs.CONTENT_TYPE_YML)
                        .accept(TestConfigs.CONTENT_TYPE_YML)
                        .pathParams("id",personVO.getId())
                        .when()
                        .patch("{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .as(PersonVO.class, objectMapper);


        personVO = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());

        assertEquals(personVO.getId() ,persistedPerson.getId());

        assertEquals("Nelson",persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male",persistedPerson.getGender());
    }
    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        mockPerson();

        var persistedPerson = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", personVO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonVO.class, objectMapper);

        personVO = persistedPerson;

        assertNotNull(persistedPerson);

        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());
        assertFalse(persistedPerson.getEnabled());
        assertEquals(personVO.getId(), persistedPerson.getId());

        assertEquals("Nelson", persistedPerson.getFirstName());
        assertEquals("Piquet Souto Maior", persistedPerson.getLastName());
        assertEquals("Brasília - DF - Brasil", persistedPerson.getAddress());
        assertEquals("Male", persistedPerson.getGender());
    }

    @Test
    @Order(5)
    public void testDelete() throws JsonMappingException, JsonProcessingException {

        given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("id", personVO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var wrapper = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .queryParams("page",3,"size",10,"direction","asc")
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PagedModelPerson.class, objectMapper);


        var people = wrapper.getContent();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());
        assertEquals(675, foundPersonOne.getId());

        assertEquals("Alic", foundPersonOne.getFirstName());
        assertEquals("Terbrug", foundPersonOne.getLastName());
        assertEquals("3 Eagle Crest Court", foundPersonOne.getAddress());
        assertEquals("Male", foundPersonOne.getGender());

        PersonVO foundPersonSix = people.get(5);

        assertNotNull(foundPersonSix.getId());
        assertNotNull(foundPersonSix.getFirstName());
        assertNotNull(foundPersonSix.getLastName());
        assertNotNull(foundPersonSix.getAddress());
        assertNotNull(foundPersonSix.getGender());
        assertTrue(foundPersonSix.getEnabled());
        assertEquals(909, foundPersonSix.getId());

        assertEquals("Allegra", foundPersonSix.getFirstName());
        assertEquals("Dome", foundPersonSix.getLastName());
        assertEquals("57 Roxbury Pass", foundPersonSix.getAddress());
        assertEquals("Female", foundPersonSix.getGender());
    }

    @Test
    @Order(7)
    public void testFindByName() throws JsonMappingException, JsonProcessingException {

        var wrapper = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .pathParam("firstName","rua")
                .queryParams("page",0,"size",6,"direction","asc")
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get("findPersonByName/{firstName}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PagedModelPerson.class, objectMapper);


        var people = wrapper.getContent();

        PersonVO foundPersonOne = people.get(0);

        assertNotNull(foundPersonOne.getId());
        assertNotNull(foundPersonOne.getFirstName());
        assertNotNull(foundPersonOne.getLastName());
        assertNotNull(foundPersonOne.getAddress());
        assertNotNull(foundPersonOne.getGender());
        assertTrue(foundPersonOne.getEnabled());
        assertEquals(1, foundPersonOne.getId());
        assertEquals("rua", foundPersonOne.getFirstName());
        assertEquals("wilson", foundPersonOne.getLastName());
        assertEquals("male", foundPersonOne.getAddress());
        assertEquals("paiva", foundPersonOne.getGender());

    }


    @Test
    @Order(8)
    public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

        RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        given().spec(specificationWithoutToken)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get()
                .then()
                .statusCode(403);
    }
/*
    @Test
    @Order(9)
    public void testHATOAS() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .config(
                        RestAssuredConfig
                                .config()
                                .encoderConfig(EncoderConfig.encoderConfig()
                                        .encodeContentTypeAs(
                                                TestConfigs.CONTENT_TYPE_YML,
                                                ContentType.TEXT)))
                .contentType(TestConfigs.CONTENT_TYPE_YML)
                .queryParams("page",3,"size",10,"direction","asc")
                .accept(TestConfigs.CONTENT_TYPE_YML)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertTrue(content.contains("rel: \"self\"\n" +
                "    href: \"http://localhost:8080/api/person/v1/675"));
        assertTrue(content.contains("rel: \"self\"\n" +
                "    href: \"http://localhost:8080/api/person/v1/412"));
        assertTrue(content.contains("rel: \"self\"\n" +
                "    href: \"http://localhost:8080/api/person/v1/844"));

        assertTrue(content.contains("rel: \"first\"\n" +
                "  href: \"http://localhost:8080/api/person/v1?direction=asc&page=0&size=10&sort=firstName,asc"));
        assertTrue(content.contains("rel: \"prev\"\n" +
                "  href: \"http://localhost:8080/api/person/v1?direction=asc&page=2&size=10&sort=firstName,asc"));
        assertTrue(content.contains("rel: \"self\"\n" +
                "  href: \"http://localhost:8080/api/person/v1?page=3&size=10&direction=asc"));
        assertTrue(content.contains("rel: \"next\"\n" +
                "  href: \"http://localhost:8080/api/person/v1?direction=asc&page=4&size=10&sort=firstName,asc"));
        assertTrue(content.contains("rel: \"last\"\n" +
                "  href: \"http://localhost:8080/api/person/v1?direction=asc&page=100&size=10&sort=firstName,asc"));/*       assertTrue(content.contains("<page><size>10</size><totalElements>1006</totalElements><totalPages>101</totalPages><number>3</number></page>"));

    }
*/
    private void mockPerson() {
        personVO.setFirstName("Nelson");
        personVO.setLastName("Piquet");
        personVO.setAddress("Brasília - DF - Brasil");
        personVO.setGender("Male");
        personVO.setEnabled(true);
    }
}