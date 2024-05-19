package com.example.lab3;
import java.io.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
    private static final String filePath = "Reg2.json";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String lastname = request.getParameter("lastname");
        int age = Integer.parseInt(request.getParameter("age"));
        String nap = request.getParameter("nap");
        String telephone_number = request.getParameter("telephone_number");

        JSONObject reg = new JSONObject();
        reg.put("name", name);
        reg.put("lastname", lastname);
        reg.put("age", age);
        reg.put("nap", nap);
        reg.put("telephone_number", telephone_number);

        JSONArray RegList = new JSONArray();

        try {
            JSONParser parser = new JSONParser();
            File file = new File(filePath);
            String fullPath = file.getAbsolutePath();
            System.out.println(fullPath);
            if (file.exists()) {
                RegList = (JSONArray) parser.parse(new FileReader(filePath));
            }
            RegList.add(reg);
            System.out.println("Reg List: " + RegList);
            FileWriter fileWriter = new FileWriter(filePath);

            fileWriter.write(RegList.toJSONString());
            fileWriter.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        response.sendRedirect("/lab3_war");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            JSONParser parser = new JSONParser();
            JSONArray RegList = (JSONArray) parser.parse(new FileReader(filePath));

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>Таблица</title><link href=\"css/bootstrap.min.css\" rel=\"stylesheet\"></head><body style=\"background-color: #f279ff\"><div><table class=\"table\"><thead><tr><th scope=\"col\">Имя</th><th scope=\"col\">Фамилия</th><th scope=\"col\">Возраст</th> <th scope=\"col\">Напраавление</th><th scope=\"col\">Номер телефона</th></tr></thead>");
            for (Object obj : RegList) {
                JSONObject reg = (JSONObject) obj;
                out.println("<tbody> <tr><td>" + reg.get("name") + "</td><td>" + reg.get("lastname") + "</td><td>" + reg.get("age") + "</td><td>" + reg.get("nap") + "</td><td>" + reg.get("telephone_number") + "</td>");
            }

            out.println("</tbody></table></div ><script src =\"js/bootstrap.bundle.min.js \"></script ></body ></html >");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}