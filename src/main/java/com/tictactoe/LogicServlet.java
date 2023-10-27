package com.tictactoe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Field field = extractField(session);

        int index = getSelectedIndex(req);

        field.getField().put(index, Sign.CROSS);

        List<Sign> data = field.getFieldData();

        session.setAttribute("data", data);
        session.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    private int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

    private Field extractField(HttpSession session) {
        Object fieldAttribute = session.getAttribute("field");
        if (Field.class != fieldAttribute.getClass()) {
            session.invalidate();
            throw new RuntimeException();
        }
        return (Field) fieldAttribute;
    }
}
