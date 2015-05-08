/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Backend.Car;
import Backend.CarBackend;
import Backend.Catalog;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roygalet
 */
public class AddEditDeleteServlet extends HttpServlet {

    String userName, selectedAction, defaultUserName;
    int selectedID;
    HttpSession session;
    Catalog backEnd;
    List<Car> myList;
    RequestDispatcher dispatcher;
    Car myCar;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        session = request.getSession();
        userName = (String) session.getAttribute("userName");
        defaultUserName = getServletContext().getInitParameter("DefaultUser");
        selectedAction = request.getParameter("selectedAction");
        if (request.getParameter("selectedID") != null) {
            if (request.getParameter("selectedID").trim().length() > 0) {
                selectedID = Integer.parseInt(request.getParameter("selectedID"));
            }
        }
        connectToDatabase();

        if (selectedAction.equalsIgnoreCase("Cancel")) {
            returnToReportPage(request, response);
        } else {
            if (selectedAction.equalsIgnoreCase("Add")) {
                try {
                    backEnd.addCar(request.getParameter("userName"), request.getParameter("make"), request.getParameter("model"), Integer.parseInt(request.getParameter("productionYear")));
                } catch (Exception ex) {

                }
                returnToReportPage(request, response);
            } else if (selectedAction.equalsIgnoreCase("Update")) {
                try {
                    backEnd.updateCar(Integer.parseInt(request.getParameter("selectedID")), request.getParameter("userName"), request.getParameter("make"), request.getParameter("model"), Integer.parseInt(request.getParameter("productionYear")));

                } catch (Exception ex) {

                }
                returnToReportPage(request, response);
            } else if (selectedAction.equalsIgnoreCase("Delete Record")) {
                try {
                    backEnd.removeCar(Integer.parseInt(request.getParameter("selectedID")));
                } catch (Exception ex) {

                }
                returnToReportPage(request, response);
            } else {

                /* TODO output your page here. You may use following sample code. */
                if (selectedAction.equalsIgnoreCase("Delete")) {
                    displayDeleteRecord(response);
                } else {
                    if (userName.equalsIgnoreCase(defaultUserName)) {
                        if (selectedAction.equalsIgnoreCase("Add New Record")) {
                            displayAddAdminRecord(response);
                        } else if (selectedAction.equalsIgnoreCase("Edit")) {
                            displayEditAdminRecord(response);
                        }
                    } else {
                        if (selectedAction.equalsIgnoreCase("Add New Record")) {
                            displayAddUserRecord(response);
                        } else if (selectedAction.equalsIgnoreCase("Edit")) {
                            displayEditUserRecord(response);
                        }
                    }
                }

            }
        }
    }

    void returnToReportPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (userName.equalsIgnoreCase(defaultUserName)) {
            dispatcher = request.getRequestDispatcher("AdminReportServlet");
        } else {
            dispatcher = request.getRequestDispatcher("UserReportServlet");
        }
        dispatcher.forward(request, response);
    }

    void connectToDatabase() {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
//                Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {

        }

        backEnd = new CarBackend("jdbc:derby://localhost:1527/Assignment1; create=true", "roy", "password");
//            backEnd = new CarBackend("jdbc:mysql://244715.spinetail.cdu.edu.au:3306/s244715_HIT337?zeroDateTimeBehavior=convertToNull", "s244715_roygalet", "password");

    }

    void displayAddAdminRecord(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Details</title>");
            out.println("<style>");
            out.println("body{font-family:Tahoma;}");
            out.println("h1{background-color: #20498D; color: white;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Add New Car");

            out.println("</h1>");

            out.println("<h2>");
            out.println("Current User: " + userName);
            out.println("<form method='POST' action='Logout'>");
            out.println("<input type='submit' value='Logout'/>");
            out.println("</form>");
            out.println("</h2>");
            out.println("<form name='detailsForm' action='AddEditDelete'>");
            out.println("<table>");

            out.println("<tr><td>User Name :</td><td><input type='text' name='userName'/></td></tr>");

            out.println("<tr><td>Make :</td><td><input type='text' name='make'/></td></tr>");
            out.println("<tr><td>Model :</td><td><input type='text' name='model'/></td></tr>");
            out.println("<tr><td>Production Year :</td><td><select name='productionYear'/>");
            for (int year = 2015; year > 1900; year--) {
                out.println("<option value='" + year + "'>" + year + "</option>");
            }
            out.println("</select></td></tr>");
            out.println("</table>");
            out.println("<input type='hidden' name='selectedAction'>");
            out.println("<input type='button' name='add' value='Add' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("<input type='button' name='cancel' value='Cancel' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    void displayEditAdminRecord(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Details</title>");
            out.println("<style>");
            out.println("body{font-family:Tahoma;}");
            out.println("h1{background-color: #20498D; color: white;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Edit Car Details");

            out.println("</h1>");

            out.println("<h2>");
            out.println("Current User: " + userName);
            out.println("<form method='POST' action='Logout'>");
            out.println("<input type='submit' value='Logout'/>");
            out.println("</form>");
            out.println("</h2>");
            out.println("<form name='detailsForm' action='AddEditDelete'>");
            out.println("<table>");

            try {
                myCar = backEnd.getCar(selectedID);
            } catch (SQLException ex) {

            }
            out.println("<tr><td>User Name :</td><td><input type='text' name='userName' value='" + myCar.getUserName() + "'/></td></tr>");

            out.println("<tr><td>Make :</td><td><input type='text' name='make' value='" + myCar.getMake() + "'/></td></tr>");
            out.println("<tr><td>Model :</td><td><input type='text' name='model' value='" + myCar.getModel() + "'/></td></tr>");
            out.println("<tr><td>Production Year :</td><td><select name='productionYear'/>");
            for (int year = 2015; year > 1900; year--) {
                out.println("<option value='" + year + "'");
                if (year == myCar.getYear()) {
                    out.println(" selected='selected'");
                }
                out.println(">" + year + "</option>");
            }
            out.println("</select></td></tr>");
            out.println("</table>");
            out.println("<input type='hidden' name='selectedID' value='" + selectedID + "'>");
            out.println("<input type='hidden' name='selectedAction'>");
            out.println("<input type='button' name='update' value='Update' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("<input type='button' name='cancel' value='Cancel' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    void displayDeleteRecord(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Details</title>");
            out.println("<style>");
            out.println("body{font-family:Tahoma;}");
            out.println("h1{background-color: #20498D; color: white;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Delete Record");
            out.println("</h1>");
            out.println("<h2>");
            out.println("Current User: " + userName);

            out.println("<form method='POST' action='Logout'>");
            out.println("<input type='submit' value='Logout'/>");
            out.println("</form>");
            out.println("</h2>");
            out.println("<h3>Are you sure you want to Delete this Record?</h3>");

            out.println("<form name='detailsForm' action='AddEditDelete'>");
            out.println("<table>");

            try {
                myCar = backEnd.getCar(selectedID);
            } catch (SQLException ex) {

            }
            out.println("<tr><td>User Name :</td><td>" + myCar.getUserName() + "</td></tr>");

            out.println("<tr><td>Make :</td><td>" + myCar.getMake() + "</td></tr>");
            out.println("<tr><td>Model :</td><td>" + myCar.getModel() + "</td></tr>");
            out.println("<tr><td>Production Year :</td><td>" + myCar.getYear() + "</td></tr>");

            out.println("</table>");
            out.println("<input type='hidden' name='selectedID' value='" + selectedID + "'>");
            out.println("<input type='hidden' name='selectedAction'>");
            out.println("<input type='button' name='delete' value='Delete Record' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("<input type='button' name='cancel' value='Cancel' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    void displayAddUserRecord(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Details</title>");
            out.println("<style>");
            out.println("body{font-family:Tahoma;}");
            out.println("h1{background-color: #20498D; color: white;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Add New Car");

            out.println("</h1>");

            out.println("<h2>");
            out.println("Current User: " + userName);
            out.println("<form method='POST' action='Logout'>");
            out.println("<input type='submit' value='Logout'/>");
            out.println("</form>");
            out.println("</h2>");
            out.println("<form name='detailsForm' action='AddEditDelete'>");
            out.println("<table>");

            out.println("<tr><td>User Name :</td><td>" + userName + "</td></tr>");

            out.println("<tr><td>Make :</td><td><input type='text' name='make'/></td></tr>");
            out.println("<tr><td>Model :</td><td><input type='text' name='model'/></td></tr>");
            out.println("<tr><td>Production Year :</td><td><select name='productionYear'/>");
            for (int year = 2015; year > 1900; year--) {
                out.println("<option value='" + year + "'>" + year + "</option>");
            }
            out.println("</select></td></tr>");
            out.println("</table>");
            out.println("<input type='hidden' name='selectedAction'/>");
            out.println("<input type='hidden' name='userName' value='" + userName + "'/>");
            out.println("<input type='button' name='add' value='Add' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("<input type='button' name='cancel' value='Cancel' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    void displayEditUserRecord(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Record Details</title>");
            out.println("<style>");
            out.println("body{font-family:Tahoma;}");
            out.println("h1{background-color: #20498D; color: white;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>");
            out.println("Edit Car Details");

            out.println("</h1>");

            out.println("<h2>");
            out.println("Current User: " + userName);
            out.println("</h2>");
            out.println("<form method='POST' action='Logout'>");
            out.println("<input type='submit' value='Logout'/>");
            out.println("</form>");
            out.println("<form name='detailsForm' action='AddEditDelete'>");
            out.println("<table>");

            try {
                myCar = backEnd.getCar(selectedID);
            } catch (SQLException ex) {

            }
            out.println("<tr><td>User Name :</td><td>" + myCar.getUserName() + "</td></tr>");

            out.println("<tr><td>Make :</td><td><input type='text' name='make' value='" + myCar.getMake() + "'/></td></tr>");
            out.println("<tr><td>Model :</td><td><input type='text' name='model' value='" + myCar.getModel() + "'/></td></tr>");
            out.println("<tr><td>Production Year :</td><td><select name='productionYear' value='" + myCar.getYear() + "'/>");
            for (int year = 2015; year > 1900; year--) {
                out.println("<option value='" + year + "'");
                if (year == myCar.getYear()) {
                    out.println(" selected='selected'");
                }
                out.println(">" + year + "</option>");
            }
            out.println("</select></td></tr>");
            out.println("</table>");
            out.println("<input type='hidden' name='selectedID' value='" + selectedID + "'>");
            out.println("<input type='hidden' name='userName' value='" + myCar.getUserName() + "'/>");
            out.println("<input type='hidden' name='selectedAction'>");
            out.println("<input type='button' name='update' value='Update' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("<input type='button' name='cancel' value='Cancel' onclick='{document.detailsForm.selectedAction.value = this.value; document.detailsForm.submit();}'/>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
