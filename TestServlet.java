package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String serverInfo = getServletContext().getServerInfo();
        String javaVersion = System.getProperty("java.version");
        String now = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

        out.println("<!doctype html><html lang='fr'><head><meta charset='utf-8'/>");
        out.println("<title>Info serveur</title>");
        out.println("<style>body{font-family:Arial,Helvetica,sans-serif;padding:18px;background:#f7fafc} .box{background:#fff;padding:16px;border-radius:10px;box-shadow:0 6px 18px rgba(0,0,0,0.06)}</style>");
        out.println("</head><body><div class='box'>");
        out.printf("<h2>Informations du serveur</h2>");
        out.printf("<p><strong>Heure serveur :</strong> %s</p>", now);
        out.printf("<p><strong>Servlet container :</strong> %s</p>", serverInfo);
        out.printf("<p><strong>Java :</strong> %s</p>", javaVersion);
        out.println("<p><a href='/'>Retour à l'accueil</a></p>");
        out.println("</div></body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // On récupère les paramètres du formulaire
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String message = req.getParameter("message");

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!doctype html><html lang='fr'><head><meta charset='utf-8'/><title>Résultat</title></head><body>");
        out.println("<h2>Contenu reçu</h2>");
        out.printf("<p><strong>Nom :</strong> %s</p>", escapeHtml(name));
        out.printf("<p><strong>Message :</strong><br/>%s</p>", escapeHtml(message).replace("\n", "<br/>"));
        out.println("<p><a href='/'>Retour</a></p>");
        out.println("</body></html>");
    }

    // Très simple échappement HTML (pour ce test)
    private String escapeHtml(String s){
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#x27;");
    }
}