package com.gestion.GestionDeProductos.logging;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class BitacoraInterceptor implements HandlerInterceptor {

    private static final String LOG_FILE = "accesos.log";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String usuario = (request.getUserPrincipal() != null) ? request.getUserPrincipal().getName() : "ANONIMO";
        String endpoint = request.getRequestURI();
        String metodo = request.getMethod();
        int status = response.getStatus();
        String fechaHora = LocalDateTime.now().toString();

        String log = String.format("[%s] Usuario: %s | Endpoint: %s | Método: %s | Status: %d%n",
                fechaHora, usuario, endpoint, metodo, status);

        escribirLog(log);
    }

    private void escribirLog(String log) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

