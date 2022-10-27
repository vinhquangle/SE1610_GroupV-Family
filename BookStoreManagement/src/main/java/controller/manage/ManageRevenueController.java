/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.manage;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Thịnh
 */
public class ManageRevenueController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        LocalDate statistic;
        LocalDate localDate = LocalDate.now();
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        try {

        } catch (Exception e) {

        } finally {
            PrintWriter out = response.getWriter();
            out.println("<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "    </head>\n"
                    + "    <body style=\"background-color: #f5f5f5\">\n"
                    + "        <div style=\"box-shadow: 5px 5px 5px #e8e8e8; font-weight: bold; color: #1e1e27; text-align: center; background-color: white; width: 98.5%; margin: auto; margin-top: 10px;\">\n"
                    + "            <p style=\"font-size: 50px;\">Đơn hàng<i class=\"fa fa-cubes\" aria-hidden=\"true\"></i></p>\n"
                    + "            <div class=\"row\">\n"
                    + "                <div style=\"color: green\" class=\"col-md-4\">\n"
                    + "                    <i class=\"fa fa-check-square-o\" aria-hidden=\"true\"></i>\n"
                    + "                    <div style=\"display: inline-block\">Hoàn thành</div>\n"
                    + "                    <div style=\"font-size: 40px;\">60%</div>\n"
                    + "                    <p style=\"font-size: 20px;\">60 / 100<i style=\"font-size: 20px;\" class=\"fa fa-cubes\" aria-hidden=\"true\"></i></p>\n"
                    + "                </div>\n"
                    + "                <div style=\"color: #d68829\" class=\"col-md-4\">\n"
                    + "                    <i class=\"fa fa-spinner\" aria-hidden=\"true\"></i>\n"
                    + "                    <div style=\"display: inline-block\">Đang tiến hành</div>\n"
                    + "                    <div style=\"font-size: 40px;\">60%</div>\n"
                    + "                    <p style=\"font-size: 20px;\">60 / 100<i style=\"font-size: 20px;\" class=\"fa fa-cubes\" aria-hidden=\"true\"></i></p>\n"
                    + "                </div>\n"
                    + "                <div style=\"color: red\" class=\"col-md-4\">\n"
                    + "                    <i class=\"fa fa-ban\" aria-hidden=\"true\"></i>\n"
                    + "                    <div style=\"display: inline-block\">Hủy bỏ</div>\n"
                    + "                    <div style=\"font-size: 40px;\">60%</div>\n"
                    + "                    <p style=\"font-size: 20px;\">60 / 100<i style=\"font-size: 20px;\" class=\"fa fa-cubes\" aria-hidden=\"true\"></i></p>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <div style=\"box-shadow: 5px 5px 5px #e8e8e8; font-weight: bold; color: #1e1e27; text-align: center; background-color: white; width: 98.5%; margin: auto; margin-top: 10px;\">\n"
                    + "            <p style=\"font-size: 50px;\">Doanh thu <i class=\"fa fa-money\" aria-hidden=\"true\"></i></p>\n"
                    + "            <p style=\"font-size: 40px;\"><input id=\"date\" style=\"color: #1e1e27; border-radius: 15px;\" onchange=\"test()\" type=\"date\" value=\"" + localDate + "\"></p>\n"
                    + "            <div style=\"font-size: 25px;\" class=\"row\">\n"
                    + "                <div class=\"col-md-4\">\n"
                    + "                    <p>Ngày</p>\n"
                    + "                    <p id=\"day\">" + localDate.getDayOfMonth() + "</p>\n"
                    + "                    <p style=\"color: #cccccc; font-size: 20px\"><i class=\"fa fa-angle-right\"></i> Tổng doanh thu trong ngày <i class=\"fa fa-angle-left\"></i></p>\n"
                    + "                    <p style=\"font-size: 35px;\"><i class=\"fa fa-angle-double-right\"></i> " + currencyVN.format(10000000) + " <i class=\"fa fa-angle-double-left\"></i></p>\n"
                    + "                </div>\n"
                    + "                <div class=\"col-md-4\">\n"
                    + "                    <p>Tháng</p>\n"
                    + "                    <p id=\"month\">" + localDate.getMonthValue() + "</p>\n"
                    + "                    <p style=\"color: #cccccc; font-size: 20px\"><i class=\"fa fa-angle-right\"></i> Tổng doanh thu trong tháng <i class=\"fa fa-angle-left\"></i></p>\n"
                    + "                    <p style=\"font-size: 35px;\"><i class=\"fa fa-angle-double-right\"></i> " + currencyVN.format(10000000) + " <i class=\"fa fa-angle-double-left\"></i></p>\n"
                    + "                </div>\n"
                    + "                <div class=\"col-md-4\">\n"
                    + "                    <p>Năm</p>\n"
                    + "                    <p id=\"year\">" + localDate.getYear() + "</p>\n"
                    + "                    <p style=\"color: #cccccc; font-size: 20px\"><i class=\"fa fa-angle-right\"></i> Tổng doanh thu trong năm <i class=\"fa fa-angle-left\"></i></p>\n"
                    + "                    <p style=\"font-size: 35px;\"><i class=\"fa fa-angle-double-right\"></i> " + currencyVN.format(10000000) + " <i class=\"fa fa-angle-double-left\"></i></p>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "            <p style=\"font-size: 50px;\">Biểu đồ <i class=\"fa fa-bar-chart\" aria-hidden=\"true\"></i></p>\n"
                    + "            <p style=\"color: #cccccc; font-size: 20px\"><i class=\"fa fa-angle-right\"></i> Doanh thu trong vòng 7 ngày vừa qua <i class=\"fa fa-angle-left\"></i></p>\n"
                    + "            <div style=\"display: inline-block; margin-right: 80%\">" + currencyVN.format(100000000) + "</div>\n"
                    + "            <table style=\"background-color: #fafafa; border-left: 2px solid #1e1e27; border-bottom: 2px solid #1e1e27; height: 650px;\" id=\"line-example-1\" class=\"charts-css line hide-data\">\n"
                    + "                <caption> Line Example #1 </caption> \n"
                    + "                <tbody style=\"height: 650px;\">\n");

            String revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";

            out.println("                    <tr style=\"margin-top: 100px;\">\n");
            double start = 0;
            double size = 0.4;
            String marginBottom = "0";
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td  style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + ";\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr>\n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.4;
            size = 0.6;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + ";\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.6;
            size = 0.3;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + ";\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.3;
            size = 0.8;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + ";\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr > \n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.8;
            size = 0.5;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + ";\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.5;
            size = 0.7;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + "\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-top: 100px;\">\n");
            start = 0.7;
            size = 1;
            if (start < size) {
                revenue = "";
            } else if (start > size) {
                revenue = "style=\"border-right: 2px dashed #f56b6b; height: 100px;\"";
            }
            if (size == 1) {
                marginBottom = "550px";
            } else {
                marginBottom = "0";
            }
            out.println("                        <td style=\"--start:" + start + "; --size:" + size + ";\"><div style=\"border-bottom: 2px  dashed #f56b6b; color: #f56b6b; margin-bottom: " + marginBottom + "\">" + currencyVN.format(10000000) + "</div><div " + revenue + " ></div></td>\n"
                    + "                    </tr>\n"
                    + "                </tbody>\n"
                    + "            </table>\n"
                    + "            <div style=\"margin-right: 81%; display: inline-block\">" + currencyVN.format(0) + "</div>\n"
                    + "            <table style=\"width: 100%; display: inline-block;\">\n"
                    + "                <tbody style=\"display: flex; width: 80%; margin: auto;\">\n"
                    + "                    <tr style=\"margin-left: 12.8%;\">\n");
            statistic = localDate.minusDays(6);
            out.println("                        <td>" + statistic.getDayOfMonth() + "/" + statistic.getMonthValue() + "</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11.4%;\">\n");
            statistic = localDate.minusDays(5);
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11.2%;\">\n");
            statistic = localDate.minusDays(4);
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11%;\">\n");
            statistic = localDate.minusDays(3);
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11.3%;\">\n");
            statistic = localDate.minusDays(2);
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11.4%;\">\n");
            statistic = localDate.minusDays(1);
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                    <tr style=\"margin-left: 11%;\">\n");
            statistic = localDate;
            out.println("                        <td>"+ statistic.getDayOfMonth()+"/"+ statistic.getMonthValue()+"</td>\n"
                    + "                    </tr> \n"
                    + "                </tbody>\n"
                    + "            </table>\n"
                    + "        </div>\n"
                    + "    </body>\n"
                    + "</html>");
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
