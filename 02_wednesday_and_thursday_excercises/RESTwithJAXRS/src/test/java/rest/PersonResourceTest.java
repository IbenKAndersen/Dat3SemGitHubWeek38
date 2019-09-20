package rest;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator.Strategy;

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.TEST, Strategy.DROP_AND_CREATE);

        httpServer = startServer();

        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;

        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        httpServer.shutdownNow();
    }

    private Person person1;
    private Person person2;
    private Person person3;
    private List<Person> people;

    @BeforeEach
    public void setUp() {
        people = new ArrayList<>();
        person1 = new Person("Iben", "Andersen", "28514154");
        person2 = new Person("Stine", "Andersen", "20304050");
        person3 = new Person("Vibeke", "Andersen", "12345678");

        people.add(person1);
        people.add(person2);
        people.add(person3);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("truncate table person_test.PERSON;");
            query.executeUpdate();
            em.getTransaction().commit();

            for (Person p : people) {
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getAllPersonsTestNew() {
        List<PersonDTO> persons;
        persons = given()
                .contentType("application/json")
                .when()
                .get("person")
                .then()
                .extract().body().jsonPath().getList("all", PersonDTO.class);
        
        assertThat(persons, containsInAnyOrder(new PersonDTO(person1), new PersonDTO(person2), new PersonDTO(person3)));
    }

    @Test
    public void getPersonByIdTest() {
        given()
                .contentType("application/json").when()
                .get("/person/1").then().log().body().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("id", equalTo(1))
                .body("fName", equalTo("Iben"))
                .body("lName", equalTo("Andersen"))
                .body("phone", equalTo("28514154"));
    }

    @Test
    public void deletePersonByIdTest() {
        given()
                .contentType("application/json").when()
                .delete("/person/1").then().log().body().assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("status", equalTo("removed"));
    }

//    @Test
//    public void putPersonTest() {
//        given()
//                .contentType("application/json").when()
//                .put("/person").then().log().body().assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("id", equalTo(1))
//                .body("fName", equalTo("Malte"))
//                .body("lName", equalTo("Magnussen"))
//                .body("phone", equalTo("42301207"));
//    }
    
    @Test
    public void postPersonTest() {
        given()
                .contentType("application/json")
                .body(new PersonDTO("Martin", "Sørensen", "12345678"))
                .when()
                .post("person")
                .then()
                .body("fName", equalTo("Martin"))
                .body("lName", equalTo("Sørensen"))
                .body("phone", equalTo("12345678"));
    }

}
