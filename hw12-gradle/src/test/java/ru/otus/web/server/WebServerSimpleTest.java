package ru.otus.web.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.WebApplication;
import ru.otus.config.DataConfig;
import ru.otus.config.WebServerConfig;
import ru.otus.config.WebServerConfigImpl;
import ru.otus.web.dao.UserDao;
import ru.otus.web.model.User;
import ru.otus.web.services.TemplateProcessor;
import ru.otus.web.services.UserAuthService;
import ru.otus.web.services.UserAuthServiceImpl;
import ru.otus.web.servlet.AuthorizationFilter;
import ru.otus.web.servlet.CustomizeLoginServlet;
import ru.otus.web.servlet.UsersApiServlet;
import ru.otus.web.servlet.UsersServlet;

import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static ru.otus.web.server.WebServerHelper.*;

class WebServerSimpleTest {
    private static final int WEB_SERVER_PORT = 8989;
    private static final String WEB_SERVER_URL = "http://localhost:" + WEB_SERVER_PORT + "/";
    private static final String LOGIN_URL = "login";
    private static final String API_USER_URL = "api/user";

    private static final long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_USER_LOGIN = "user1";
    private static final String DEFAULT_USER_PASSWORD = "11111";
    private static final User DEFAULT_USER = new User(DEFAULT_USER_ID, "Vasya", DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD);
    private static final String INCORRECT_USER_LOGIN = "BadUser";

    private static final Logger log = LoggerFactory.getLogger(WebServerSimpleTest.class);

    private static Gson gson;
    private static WebServer webServer;
    private static HttpClient http;

    @BeforeEach
    void setUp() throws Exception {
        http = HttpClient.newHttpClient();

        DataConfig dataConfig = mock(DataConfig.class);
        UserDao userDao = mock(UserDao.class);

        given(userDao.findById(DEFAULT_USER_ID)).willReturn(Optional.of(DEFAULT_USER));
        given(userDao.findRandomUser()).willReturn(Optional.of(DEFAULT_USER));
        given(dataConfig.getUserDao()).willReturn(userDao);

        WebServerConfig serverConfig = new WebServerConfigImpl(webServerConfig -> initServlets(webServerConfig, dataConfig),
                (webServerConfig) -> initSecurity(webServerConfig, dataConfig));

        gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        webServer = new WebServerSimple(WEB_SERVER_PORT, serverConfig);
        webServer.start();
    }

    private static ServletContextHandler initSecurity(WebServerConfig webServerConfig, DataConfig dataConfig) {
        UserAuthService userAuthService = mock(UserAuthService.class);

        given(userAuthService.authenticate(DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD)).willReturn(true);
        given(userAuthService.authenticate(INCORRECT_USER_LOGIN, DEFAULT_USER_PASSWORD)).willReturn(false);

        webServerConfig.getServletContextHandler()
                .addServlet(new ServletHolder(new CustomizeLoginServlet(webServerConfig, userAuthService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        webServerConfig.getPaths().forEach(path -> webServerConfig.getServletContextHandler().addFilter(new FilterHolder(authorizationFilter), path, null));
        return webServerConfig.getServletContextHandler();
    }

    private static void initServlets(WebServerConfig webServerConfig, DataConfig dataConfig) {
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        webServerConfig.putServlet("/users", new UsersServlet(webServerConfig.getTemplateProcessor(), dataConfig.getUserDao()));
        webServerConfig.putServlet("/api/user/*", new UsersApiServlet(dataConfig.getUserDao(), gson));
    }

    @AfterEach
    void tearDown() throws Exception {
        webServer.stop();
    }

    @DisplayName("возвращать 302 при запросе пользователя по id если не выполнен вход ")
    @Test
    void shouldReturnForbiddenStatusForUserRequestWhenUnauthorized() throws Exception {
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(buildUrl(WEB_SERVER_URL, API_USER_URL)))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_MOVED_TEMP);
    }

    @DisplayName("возвращать ID сессии при выполнении входа с верными данными")
    @Test
    void shouldReturnJSessionIdWhenLoggingInWithCorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD);
        assertThat(jSessionIdCookie).isNotNull();
    }

    @DisplayName("не возвращать ID сессии при выполнении входа если данные входа не верны")
    @Test
    void shouldNotReturnJSessionIdWhenLoggingInWithIncorrectData() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), INCORRECT_USER_LOGIN, DEFAULT_USER_PASSWORD);
        assertThat(jSessionIdCookie).isNull();
    }

    @DisplayName("возвращать корректные данные при запросе пользователя по id если вход выполнен")
    @Test
    void shouldReturnCorrectUserWhenAuthorized() throws Exception {
        HttpCookie jSessionIdCookie = login(buildUrl(WEB_SERVER_URL, LOGIN_URL), DEFAULT_USER_LOGIN, DEFAULT_USER_PASSWORD);
        assertThat(jSessionIdCookie).isNotNull();

        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(buildUrl(WEB_SERVER_URL, API_USER_URL,String.valueOf(DEFAULT_USER_ID))))
                .setHeader(COOKIE_HEADER, String.format("%s=%s", jSessionIdCookie.getName(), jSessionIdCookie.getValue()))
                .build();
        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode()).isEqualTo(HttpURLConnection.HTTP_OK);
        assertThat(response.body()).isEqualTo(gson.toJson(DEFAULT_USER));
    }
}