package ru.otus.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.config.DataConfig;
import ru.otus.jdbc.crm.model.Client;

import java.io.IOException;
import java.util.List;

public class ClientsApiServlet extends HttpServlet {

    private static final String PARAM_NAME = "name";
    private final DataConfig data;

    public ClientsApiServlet(DataConfig data) {
        this.data = data;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Client> clients = data.getDbClientService().findAll();

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(data.getGson().toJson(clients));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter(PARAM_NAME);
        data.getDbClientService().save(new Client(name));
        response.sendRedirect("/clients");
    }
}
