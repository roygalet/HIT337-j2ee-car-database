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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author roygalet
 */
public class UserReportServlet extends HttpServlet {

    String userName;
    HttpSession session;
    Catalog backEnd;
    List<Car> myList;
    int maximumNumberOfCarsPerUser;

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
        maximumNumberOfCarsPerUser = Integer.parseInt(getServletConfig().getInitParameter("MaximumCarsPerUser").trim());
//        log(getServletConfig().getInitParameter("MaximumCarsPerUser"));
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Car Database Report</title>");
            out.println("<style>");
        out.println("body{font-family:Tahoma;}");
        out.println("h1{background-color: #20498D; color: white;}");
        out.println("thead{background-color: #BAD5FD;font-weight:bold;text-decoration:underline;}");
        out.println("tr:nth-child(even){background-color: #BAD5FD;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
        out.println("<h1>Welcome to Car Database Report</h1>");
        out.println("<h2>Hello " + userName + "!");
        out.println("<form method='POST' action='Logout'>");
        out.println("<input type='submit' value='Logout'/>");
        out.println("</form></h2>");
            try {
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                    backEnd = new CarBackend("jdbc:derby://localhost:1527/Assignment1; create=true", "roy", "password");
//                        Class.forName("com.mysql.jdbc.Driver").newInstance();
//                        backEnd = new CarBackend("jdbc:mysql://244715.spinetail.cdu.edu.au:3306/s244715_HIT337?zeroDateTimeBehavior=convertToNull", "s244715_roygalet", "password");
                    try {
                        myList = backEnd.getAllCarsForUser(userName);
                        if (myList != null) {
//                            log("Records : " + myList.size());
                            out.println("<form method='POST' name='adminRecords' action='AddEditDelete'>");
                            out.println("<input type='hidden' name='selectedID'>");
                            out.println("<input type='hidden' name='selectedAction'>");
                            out.println("<table>");
                            out.println("<thead>");
                            out.println("<tr>");
                            out.println("<td>ID</td>");
                            out.println("<td>Make</td>");
                            out.println("<td>Model</td>");
                            out.println("<td>Year</td>");
                            out.println("<td colspan=2>Actions</td>");
                            out.println("</tr>");
                            out.println("</thead>");
                            out.println("<tbody>");
                            for (int index = 0; index < myList.size(); index++) {
                                out.println("<tr>");
                                out.println("<td>" + myList.get(index).getID() + "</td>");
                                out.println("<td>" + myList.get(index).getMake() + "</td>");
                                out.println("<td>" + myList.get(index).getModel() + "</td>");
                                out.println("<td>" + myList.get(index).getYear() + "</td>");
                                out.println("<td><input type='button' name='edit' value='Edit' onclick='{document.adminRecords.selectedID.value=" + myList.get(index).getID() + "; document.adminRecords.selectedAction.value=this.value; document.adminRecords.submit();}'/></td>");
                                out.println("<td><input type='button' name='delete' value='Delete' onclick='{document.adminRecords.selectedID.value=" + myList.get(index).getID() + "; document.adminRecords.selectedAction.value=this.value; document.adminRecords.submit();}'/></td>");
                                out.println("</tr>");
                            }
                            if (myList.size() < maximumNumberOfCarsPerUser) {
                                out.println("<tr><td colspan=6><input type='button' name='add' value='Add New Record' onclick='{document.adminRecords.selectedAction.value=this.value; document.adminRecords.submit();}'/></td></tr>");
                            } else {
                                out.println("<tr><td colspan=6>Cannot add more cars.  Only " + maximumNumberOfCarsPerUser + " cars are allowed per user.  Delete existing cars before adding more cars.</td></tr>");
                            }
                            out.println("</tbody>");
                            out.println("</table>");
                            out.println("</form>");
                        }
                    } catch (Exception ex) {
//                    Logger.getLogger(ServletReport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (ClassNotFoundException ex) {
//                            Logger.getLogger(ServletReport.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (InstantiationException | IllegalAccessException ex) {
//                        Logger.getLogger(ServletReport.class.getName()).log(Level.SEVERE, null, ex);
            }
//                        Logger.getLogger(ServletReport.class.getName()).log(Level.SEVERE, null, ex);

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
