package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {

    private Storage storage; // Config.getInstance().getStorage();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        List<Resume> resumes = storage.getAllSorted();
        writer.write(
                """
                        <html>
                        <head>
                            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                            <title>Список резюме:</title>
                        </head>
                        <body>
                            <table border=1>
                              <tr>
                                 <th>Идентификатор</th>
                                 <th>ФИО</th>
                              </tr>
                        """
        );
        for (Resume resume : resumes) {
            writer.write(
                    "<tr>\n" +
                            "<td>" + resume.getUuid() + "</td>\n" +
                            "<td>" + resume.getFullName() + "</td>\n" +
                            "</tr>\n"
            );
        }
        writer.write(
                """
                        </table>
                        </body>
                        </html>
                        """
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
