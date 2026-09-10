package com.finanzas.appfinanzasweb.servlet;

import java.io.IOException;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "BienvenidaServlet", urlPatterns = {"/bienvenida"})
public class BienvenidaServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String mensaje
                = "El Servlet recibió correctamente la solicitud GET.";

        Date fechaSolicitud = new Date();

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("fechaSolicitud", fechaSolicitud);

        request.getRequestDispatcher("/bienvenida.jsp")
                .forward(request, response);
    }
}