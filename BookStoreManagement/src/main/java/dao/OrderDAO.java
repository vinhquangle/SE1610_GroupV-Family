/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.CustomerDTO;
import dto.OrderDTO;
import dto.PromotionDTO;
import dto.StaffDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import utilities.DBUtils;

/**
 *
 * @author Administrator
 */
public class OrderDAO {

    private static final String LOAD_ORDER_BY_STATUS = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] LIKE ?\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC";
    private static final String LOAD_ORDER_BY_DELIVERER = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL AND o.staffID LIKE ? AND o.[Address] IS NOT NULL\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC";
    private static final String LOAD_ORDER_BY_DELIVERER_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL AND o.staffID LIKE ? AND o.[Address] IS NOT NULL\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String LOAD_ORDER_BY_STATUS_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] LIKE ?\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String LOAD_ORDER_BY_STATUS_NULL = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL";
    private static final String LOAD_ORDER_BY_STATUS_NULL_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String LOAD_ORDER_BY_CUS = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.customerID LIKE ?\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] ASC\n";
    private static final String LOAD_ORDER_BY_ORDER_ID = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.Subtotal, o.Discount AS 'orderDiscount', o.[Date], o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.orderID LIKE ?";
    private static final String SEARCH_ORDER = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.status LIKE ? AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC";
    private static final String SEARCH_ORDER_NULL = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC";
    private static final String SEARCH_ORDER_DELIVERER = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[status] IS NULL AND o.staffID LIKE ? AND o.[Address] IS NOT NULL AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC";
    private static final String SEARCH_ORDER_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.status LIKE ? AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String SEARCH_ORDER_DELIVERER_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.status IS NULL AND o.staffID LIKE ? AND o.[Address] IS NOT NULL AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String SEARCH_ORDER_NULL_9 = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.Subtotal, o.Discount AS 'orderDiscount', o.[Delivery-cost], o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.status IS NULL AND ( o.orderID LIKE ? OR o.customerID LIKE ? OR o.staffID LIKE ? OR c.Phone LIKE ?\n"
            + "OR dbo.ufn_removeMark(c.Name) LIKE ? OR  dbo.ufn_removeMark(s.Name) LIKE ?)\n"
            + "ORDER BY o.[Date] DESC, o.orderID DESC, o.[status] DESC\n"
            + "OFFSET ? ROW FETCH NEXT 9 ROWS ONLY";
    private static final String INSERT_ORDER_ONLINE_STORE = "INSERT INTO [tblOrder](customerID,staffID,promotionID,[Address],[Date],Subtotal,Discount,[Delivery-cost],Total,[Description],[Status],[Delete])\n"
            + "VALUES (?,NULL,?,NULL,?,?,?,?,?,?,NULL,?)\n"
            + "SELECT MAX(orderID) AS 'orderID' FROM tblOrder";
    private static final String INSERT_ORDER_ONLINE_SHIP = "INSERT INTO [tblOrder](customerID,staffID,promotionID,[Address],[Date],Subtotal,Discount,[Delivery-cost],Total,[Description],[Status],[Delete])\n"
            + "VALUES (?,NULL,?,?,?,?,?,?,?,?,NULL,?)\n"
            + "SELECT MAX(orderID) AS 'orderID' FROM tblOrder";
    private static final String INSERT_ORDER_OFFLINE = "INSERT INTO [tblOrder](customerID,staffID,promotionID,[Address],[Date],Subtotal,Discount,[Delivery-cost],Total,[Description],[Status],[Delete])\n"
            + "VALUES (?,?,?,NULL,?,?,?,NULL,?,NULL,?,?)\n"
            + "SELECT MAX(orderID) AS 'orderID' FROM tblOrder";
    private static final String GET_BY_DATE = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.[Delivery-cost], o.Subtotal, o.Discount AS 'orderDiscount', o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE o.[Date] LIKE ?";
    private static final String GET_BY_MONTH = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.[Delivery-cost], o.Subtotal, o.Discount AS 'orderDiscount', o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE MONTH(o.[Date])  LIKE ? AND YEAR(o.[Date]) LIKE ?";
    private static final String GET_BY_YEAR = "SELECT o.orderID, o.customerID, o.staffID, o.promotionID, o.[Address], o.[Date], o.[Delivery-cost], o.Subtotal, o.Discount AS 'orderDiscount', o.Total, o.[Description] AS 'orderDes', o.[status], o.[delete],\n"
            + "c.Name AS 'cusName', c.Email, c.[Address] AS 'cusAddr', c.Phone, c.Point, c.[status] AS 'cusStatus', c.[delete] AS 'cusDelete',\n"
            + "s.Name AS 'staffName', s.[Role], s.Phone AS 'staffPhone', s.[Date-of-birth] AS 'dob', s.[status] AS 'staffStatus', s.[delete] AS 'staffDelete',\n"
            + "p.staffID AS 'staffProID', p.[Date-start] AS 'dateS', p.[Date-end] AS 'dateE', p.[Description] AS 'proDes', p.Condition, p.Discount, p.[status] AS 'proStatus',\n"
            + "t.Name AS 'staffNamePro', t.[Role] AS 'roleP', t.Phone AS 'staffPhoneP', t.[Date-of-birth] AS 'dobP', t.[status] AS 'staffStatusP', t.[delete] AS 'staffDeleteP'\n"
            + "FROM ((((tblOrder o \n"
            + "LEFT JOIN tblCustomer c ON o.customerID LIKE c.customerID)\n"
            + "LEFT JOIN tblStaff s ON o.staffID LIKE s.staffID)\n"
            + "LEFT JOIN tblPromotion p ON o.promotionID LIKE p.promotionID)\n"
            + "LEFT JOIN tblStaff t ON p.staffID LIKE t.staffID)\n"
            + "WHERE YEAR(o.[Date]) LIKE ?";
    private static final String GET_7_DAY = "SELECT TOP(1)  [Date] , SUM(Total) AS 'Total'\n"
            + "FROM tblOrder \n"
            + "WHERE [Date] BETWEEN ? AND ?\n"
            + "GROUP BY [DATE]\n"
            + "ORDER BY Total DESC";
    private static final String UPDATE_STATUS = "UPDATE tblOrder SET [Status] = ?, staffID = ?  WHERE orderID = ?";
    private static final String GET_ORDER = "UPDATE tblOrder SET staffID = ?  WHERE orderID = ? AND staffID IS NULL";

    public List<OrderDTO> loadOrderByStatus(String st) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_STATUS);
                ptm.setString(1, st);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> loadOrderByDeliverer(String deliverID) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_DELIVERER);
                ptm.setString(1, deliverID);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> load9OrderByDeliverer(String delivererID, int page) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_DELIVERER_9);
                ptm.setString(1, delivererID);
                ptm.setInt(2, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> load9OrderByStatus(String st, int page) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_STATUS_9);
                ptm.setString(1, st);
                ptm.setInt(2, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> loadOrderByStatusNull() throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_STATUS_NULL);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> load9OrderByStatusNull(int page) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_STATUS_NULL_9);
                ptm.setInt(1, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> loadOrderByCusID(String cusId) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_CUS);
                ptm.setString(1, cusId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public OrderDTO loadOrderByOrderID(String orderId) throws SQLException {
        OrderDTO order = new OrderDTO();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOAD_ORDER_BY_ORDER_ID);
                ptm.setString(1, orderId);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    order = new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return order;
    }

    public List<OrderDTO> searchOrder(String txtsearch, String st) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER);
                ptm.setString(1, st);
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                ptm.setString(7, "%" + txtsearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> searchOrderNull(String txtsearch) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER_NULL);
                ptm.setString(1, "%" + txtsearch + "%");
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> searchOrderDeliverer(String delivererID, String txtsearch) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER_DELIVERER);
                ptm.setString(1, delivererID);
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                ptm.setString(7, "%" + txtsearch + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> search9Order(String txtsearch, int page, String st) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER_9);
                ptm.setString(1, st);
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                ptm.setString(7, "%" + txtsearch + "%");
                ptm.setInt(8, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> search9OrderNull(String txtsearch, int page) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER_NULL_9);
                ptm.setString(1, "%" + txtsearch + "%");
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                ptm.setInt(7, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> search9OrderDeliverer(String delivererID, String txtsearch, int page) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        if (page <= 0) {
            page = 1;
        }
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ORDER_DELIVERER_9);
                ptm.setString(1, delivererID);
                ptm.setString(2, "%" + txtsearch + "%");
                ptm.setString(3, "%" + txtsearch + "%");
                ptm.setString(4, "%" + txtsearch + "%");
                ptm.setString(5, "%" + txtsearch + "%");
                ptm.setString(6, "%" + txtsearch + "%");
                ptm.setString(7, "%" + txtsearch + "%");
                ptm.setInt(8, (page - 1) * 9);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public int insertOrderOnlineShip(String customerID, String promotionID, String address, double subtotal, double discount, double deliCost, double total, String description) throws SQLException {
        System.out.println(total);
        int orderID = 0;
        String date = java.time.LocalDate.now().toString();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_ORDER_ONLINE_SHIP);
            ptm.setString(1, customerID);
            if (promotionID.equals("")) {
                ptm.setNull(2, Types.NULL);
            } else {
                ptm.setString(2, promotionID);
            }
            ptm.setString(3, address);
            ptm.setString(4, date);
            ptm.setDouble(5, subtotal);
            ptm.setDouble(6, discount);
            ptm.setDouble(7, deliCost);
            ptm.setDouble(8, total);
            ptm.setString(9, description);
            ptm.setString(10, "0");
            rs = ptm.executeQuery();
            while (rs.next()) {
                orderID = rs.getInt("orderID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                ptm.close();
            }
        }
        return orderID;
    }

    public int insertOrderOnlineStore(String customerID, String promotionID, double subtotal, double discount, double deliCost, double total, String description) throws SQLException {
        int orderID = 0;
        String date = java.time.LocalDate.now().toString();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_ORDER_ONLINE_STORE);
            ptm.setString(1, customerID);
            if (promotionID.equals("")) {
                ptm.setNull(2, Types.NULL);
            } else {
                ptm.setString(2, promotionID);
            }
            ptm.setString(3, date);
            ptm.setDouble(4, subtotal);
            ptm.setDouble(5, discount);
            ptm.setDouble(6, deliCost);
            ptm.setDouble(7, total);
            ptm.setString(8, description);
            ptm.setString(9, "0");
            rs = ptm.executeQuery();
            while (rs.next()) {
                orderID = rs.getInt("orderID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                ptm.close();
            }
        }
        return orderID;
    }

    public int insertOrderOffline(String cusID, String staffID, String promotionID, double subtotal, double discount, double total, String status) throws SQLException {
        int orderID = 0;
        String date = java.time.LocalDate.now().toString();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            ptm = conn.prepareCall(INSERT_ORDER_OFFLINE);
            ptm.setString(1, cusID);
            ptm.setString(2, staffID);
            if (promotionID.equals("")) {
                ptm.setNull(3, Types.NULL);
            } else {
                ptm.setString(3, promotionID);
            }
            ptm.setString(4, date);
            ptm.setDouble(5, subtotal);
            ptm.setDouble(6, discount);
            ptm.setDouble(7, total);
            ptm.setString(8, status);
            ptm.setString(9, "0");
            rs = ptm.executeQuery();
            while (rs.next()) {
                orderID = rs.getInt("orderID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                ptm.close();
            }
        }
        return orderID;
    }

    public List<OrderDTO> getByDay(String day) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BY_DATE);
                ptm.setString(1, day);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> getByMonth(String month, String year) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BY_MONTH);
                ptm.setString(1, month);
                ptm.setString(2, year);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public List<OrderDTO> getByYear(String year) throws SQLException {
        List<OrderDTO> listOrder = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_BY_YEAR);
                ptm.setString(1, year);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String orderID = rs.getString("orderID");
                    String customerID = rs.getString("customerID");
                    String staffID = rs.getString("staffID");
                    String promotionID = rs.getString("promotionID");
                    String address = rs.getString("Address");
                    String date = rs.getString("Date");
                    double deliCost = rs.getDouble("Delivery-cost");
                    double subtotal = rs.getDouble("Subtotal");
                    double oDiscount = rs.getDouble("orderDiscount");
                    double total = rs.getDouble("Total");
                    String orderDes = rs.getString("orderDes");
                    String status = rs.getString("status");
                    String delete = rs.getString("delete");
                    String cusName = rs.getString("cusName");
                    String email = rs.getString("Email");
                    String cusAddr = rs.getString("cusAddr");
                    String phone = rs.getString("Phone");
                    int point = rs.getInt("Point");
                    String cusStatus = rs.getString("cusStatus");
                    String cusDelete = rs.getString("cusDelete");
                    String staffName = rs.getString("staffName");
                    String role = rs.getString("Role");
                    String staffPhone = rs.getString("staffPhone");
                    String dob = rs.getString("dob");
                    double condition = rs.getDouble("Condition");
                    String staffStatus = rs.getString("staffStatus");
                    String staffDelete = rs.getString("staffDelete");
                    String staffProID = rs.getString("staffProID");
                    String dateS = rs.getString("dateS");
                    String dateE = rs.getString("dateE");
                    String proDes = rs.getString("proDes");
                    double discount = rs.getDouble("Discount");
                    String proStatus = rs.getString("proStatus");
                    String staffNamePro = rs.getString("staffNamePro");
                    String roleP = rs.getString("roleP");
                    String staffPhoneP = rs.getString("staffPhoneP");
                    String dobP = rs.getString("dobP");
                    String staffStatusP = rs.getString("staffStatusP");
                    String staffDeleteP = rs.getString("staffDeleteP");
                    listOrder.add(new OrderDTO(orderID,
                            new CustomerDTO(customerID, cusName, "***", email, cusAddr, phone, point, cusStatus, cusDelete),
                            new StaffDTO(staffID, staffName, "***", role, staffPhone, dob, staffStatus, staffDelete),
                            new PromotionDTO(promotionID,
                                    new StaffDTO(staffProID, staffNamePro, "***", roleP, staffPhoneP, dobP, staffStatusP, staffDeleteP), dateS, dateE, proDes, condition, discount, proStatus), address, subtotal, oDiscount, deliCost, date, total, orderDes, status, delete));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return listOrder;
    }

    public String get7Day(String dateS, String dateE) throws SQLException {
        String date = new String();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_7_DAY);
                ptm.setString(1, dateS);
                ptm.setString(2, dateE);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    date = rs.getString("Date");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return date;
    }

    public boolean updateStatus(String status, String staffID, String orderId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(UPDATE_STATUS);
                ptm.setString(1, status);
                ptm.setString(2, staffID);
                ptm.setString(3, orderId);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public boolean getOrder(String staffID, String orderId) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareCall(GET_ORDER);
                ptm.setString(1, staffID);
                ptm.setString(2, orderId);
                check = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }
}
