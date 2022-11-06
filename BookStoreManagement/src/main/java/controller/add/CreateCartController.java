/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.add;

import dao.BookDAO;
import dao.CategoryDAO;
import dao.CustomerDAO;
import dao.PromotionDAO;
import dao.PublisherDAO;
import dto.BookDTO;
import dto.CategoryDTO;
import dto.CustomerDTO;
import dto.PromotionDTO;
import dto.PublisherDTO;
import dto.StaffDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thịnh
 */
public class CreateCartController extends HttpServlet {

    private static final String ERROR = "WEB-INF/JSP/HomePage/error.jsp";
    private static final String FAIL = "WEB-INF/JSP/LoginPage/login.jsp";
    private static final String SUCCESS = "WEB-INF/JSP/CartPage/createCart.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        int indexCount = 0;
        int index = 0;
        String search = new String();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        List<BookDTO> list = new ArrayList<BookDTO>();
        try {
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            CustomerDAO cusDao = new CustomerDAO();
            CategoryDAO cateDao = new CategoryDAO();
            PublisherDAO pubDao = new PublisherDAO();
            LocalDate localDate = LocalDate.now();
            LocalDate dateEnd = LocalDate.now();
            PromotionDAO proDao = new PromotionDAO();
            List<CustomerDTO> listCus = new ArrayList<>();
            listCus = cusDao.getlistCustomer();
            StaffDTO staff = new StaffDTO();
            staff = (StaffDTO) session.getAttribute("LOGIN_STAFF");
            List<CategoryDTO> listCate = cateDao.getListCategory("1"); //Lấy tất cả thể loại
            List<PublisherDTO> listPub = pubDao.getListPublisher("1"); //Lấy tất cả NXB
            session.setAttribute("LIST_CUS", listCus);
            if (staff == null) {
                url = FAIL;
            } else {
                url = SUCCESS;
            }
            if (action != null) {
                if (action.equals("Create")) {
                    List<PromotionDTO> listPro = proDao.loadAvailablePromotion("1");
                    for (PromotionDTO promotionDTO : listPro) {
                        dateEnd = LocalDate.parse(promotionDTO.getDateEnd());
                        if (dateEnd.isBefore(localDate)) {
                            promotionDTO.setStatus("0");
                            proDao.updatePromotion(promotionDTO);
                        }
                    }
                    session.setAttribute("LIST_PUB", listPub);
                    session.setAttribute("LIST_CATE", listCate);
                    session.setAttribute("PROMOTION", proDao.loadAvailablePromotion("1"));
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }
            try {
                index = Integer.parseInt(request.getParameter("index"));
            } catch (Exception e) {
                index = 1;
            }
            search = request.getParameter("searchBook");
            BookDAO dao = new BookDAO();
            indexCount = dao.countBook("1");
            if (search == null || search == "") {
                search = "";
                list = dao.getListBook(index, "1");//Lấy sản phẩm theo phân trang
            } else {
                request.setCharacterEncoding("UTF-8");
                String temp = Normalizer.normalize(request.getParameter("searchBook"), Normalizer.Form.NFD);
                Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
                String txtSearch = pattern.matcher(temp).replaceAll("");
                indexCount = dao.searchBook(txtSearch, "1").size();
                list = dao.searchBook9(txtSearch, index, "1");
            }
        } catch (Exception e) {
            log("ERROR AT CreateCartController : " + e.toString());
        } finally {
            PrintWriter out = response.getWriter();
            if (list.size() > 0) {
                int count = 0;
                int quantityCheck = 0;
                for (BookDTO book : list) {
                    count--;
                    quantityCheck = book.getQuantity();
                    out.println("<div style=\"margin-top: 10px; margin-bottom: 5px; border: 1px solid #dedede;\" class=\"row\">\n"
                            + "                                <div class=\"col-md-2\"><img style=\"width: 100px;\" src=\"" + book.getImg() + "\"></div>\n"
                            + "                                <div style=\"font-weight: bold;\" class=\"col-md-4\">" + book.getName() + "</div>\n"
                            + "                                <div style=\"font-weight: bold;\" class=\"col-md-2\">" + book.getQuantity() + "</div>\n"
                            + "                                <div style=\"font-weight: bold;\" class=\"col-md-2\">" + currencyVN.format(book.getPrice()) + "</div>\n"
                            + "                                <div style=\"font-weight: bold;\" class=\"col-md-2\">\n"
                            + "                                   <div style=\"margin-top: 10px;\" class=\"input-number\">\n"
                            + "                                           <input onkeypress=\"return isNumberKey(event)\" type=\"number\" name=\"quantity\" id=\"" + count + "\" value=\"1\">\n"
                            + "                                             <span class=\"qty-up\" onclick=\"add(" + count + ")\">+</span>\n"
                            + "                                              <span class=\"qty-down\" onclick=\"subtract(" + count + ")\">-</span>\n"
                            + "                                             </div>\n"
                            + "                                     <button onclick=\"loadAdd(document.getElementById('" + count + "').value,'" + book.getIsbn() + "','" + quantityCheck + "','Add')\" type=\"button\" style=\" margin-top: 1px; color: white; background-color: #009933; width: 50px; text-align: center;\">Thêm</button>"
                            + "                                 </div>\n"
                            + "                            </div>");
                }
            } else {
                out.println(" <p style=\"margin-top:100px; font-size: 100px; text-align: center;\">Không tìm thấy!</p>");
            }
            out.println("       <div class=\"row\">\n"
                    + "         <div class=\"col-md-12\" style=\"text-align: center\" >\n"
                    + "         <div class=\"pagination\">\n");
            int page = 0;
            if ((indexCount % 9) == 0) {
                page = indexCount / 9;
            } else {
                page = (indexCount / 9) + 1;
            }
            for (int j = 1; j < page + 1; j++) {
                out.println("                    <a id=\"" + j + "\" onclick=\"loadCreate('" + search + "'," + j + ")\" href=\"#\">" + j + "</a>\n");
            }
            out.println("                       </div>\n"
                    + "                        </div>\n"
                    + "                    </div>\n"
                    + "  </body>\n"
                    + "</html>\n");
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
