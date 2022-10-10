package br.com.wilson.paiva.apirestspring.integrationtests.controller.cors.withjson;

import br.com.wilson.paiva.apirestspring.configs.TestConfigs;
import br.com.wilson.paiva.apirestspring.data.vo.v1.security.AccountCredentialsVO;
import br.com.wilson.paiva.apirestspring.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.wilson.paiva.apirestspring.integrationtests.vo.PersonVO;
import br.com.wilson.paiva.apirestspring.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    public class PersonControllerCorsJsonTest extends AbstractIntegrationTest {

        private  static RequestSpecification specification;
        private static ObjectMapper objectMapper;

        private static PersonVO personVO;

        @BeforeAll
        public static void setup(){
            objectMapper = new ObjectMapper();
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

            personVO = new PersonVO();
        }

    @Test
    @Order(0)
    public void authorization() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro","admin123");
        var accessToken =  given()
                .basePath("/auth/signin")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                    .as(TokenVO.class)
                .getAccessToken();
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION,"Bearer "+accessToken)
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
            var content =
                    given()
                            .spec(specification)
                            .contentType(TestConfigs.CONTENT_TYPE_JSON)
                            .header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                            .body(personVO)
                            .when()
                            .post()
                            .then()
                            .statusCode(200)
                            .extract()
                            .body()
                            .asString();

            PersonVO persistedPerson = objectMapper.readValue(content,PersonVO.class);
            personVO = persistedPerson;
            assertNotNull(persistedPerson);
            assertNotNull(persistedPerson.getId());
            assertNotNull(persistedPerson.getFirstName());
            assertNotNull(persistedPerson.getLastName());
            assertNotNull(persistedPerson.getAddress());
            assertNotNull(persistedPerson.getGender());

            assertTrue(persistedPerson.getId()>0);

            assertEquals("Richart",persistedPerson.getFirstName());
            assertEquals("Stallman",persistedPerson.getLastName());
            assertEquals("New York",persistedPerson.getAddress());
            assertEquals("Male",persistedPerson.getGender());

        }

    @Test
    @Order(2)
    public void testCreateWithOrigin() throws JsonProcessingException {
        mockPerson();
        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                        .body(personVO)
                        .when()
                        .post()
                        .then()
                        .statusCode(403)
                        .extract()
                        .body()
                        .asString();


        assertNotNull(content);

        assertEquals("Invalid CORS request",content);

    }

    @Test
    @Order(3)
    public void testFindById() throws JsonProcessingException {
        mockPerson();
         var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_ERUDIO)
                        .pathParams("id",personVO.getId())
                        .when()
                        .get("{id}")
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();

        PersonVO persistedPerson = objectMapper.readValue(content,PersonVO.class);
        personVO = persistedPerson;
        assertNotNull(persistedPerson);
        assertNotNull(persistedPerson.getId());
        assertNotNull(persistedPerson.getFirstName());
        assertNotNull(persistedPerson.getLastName());
        assertNotNull(persistedPerson.getAddress());
        assertNotNull(persistedPerson.getGender());

        assertTrue(persistedPerson.getId()>0);

        assertEquals("Richart",persistedPerson.getFirstName());
        assertEquals("Stallman",persistedPerson.getLastName());
        assertEquals("New York",persistedPerson.getAddress());
        assertEquals("Male",persistedPerson.getGender());
    }
    @Test
    @Order(4)
    public void testFindByIdWithWronOrigin() throws JsonProcessingException {
        mockPerson();

        var content =
                given()
                        .spec(specification)
                        .contentType(TestConfigs.CONTENT_TYPE_JSON)
                        .header(TestConfigs.HEADER_PARAM_ORIGIN,TestConfigs.ORIGIN_SEMERU)
                        .pathParams("id",personVO.getId())
                        .when()
                        .get("{id}")
                        .then()
                        .statusCode(403)
                        .extract()
                        .body()
                        .asString();

        assertNotNull(content);

        assertEquals("Invalid CORS request",content);

    }
    private void mockPerson() {
            personVO.setFirstName("Richart");
            personVO.setLastName("Stallman");
            personVO.setAddress("New York");
            personVO.setGender("Male");
            personVO.setEnabled(true);
        }
    }
