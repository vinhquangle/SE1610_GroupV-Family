
package controller.login;

import dao.CustomerDAO;
import dao.StaffDAO;
import java.io.IOException;

//Quốc Thịnh >>>>>>>>>>
public class LogoutController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String SUCCESS = "LoginController?action=Login";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            CustomerDAO cusDao = new CustomerDAO();
            StaffDAO staffDao = new StaffDAO();
            String cus = request.getParameter("cusID");
            String staff = request.getParameter("staffID");
            if (cus != null) {
                cusDao.updateStatusOffline(cus);//Cập nhật trạng thái tài khoản khách hành
            } else if (staff != null) {
                staffDao.updateStatusOffline(staff);//Cập nhật trạng thái tài khoản nhân viên
            
            }
            session = request.getSession(false);
            if (session != null) {
                session.invalidate();//Xóa session scope
            }
        } catch (Exception e) {
            log("Error at LogoutController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
