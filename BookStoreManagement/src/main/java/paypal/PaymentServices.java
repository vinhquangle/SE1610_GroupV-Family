/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paypal;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import dto.BookDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
//Quốc Thịnh >>>>>>>>>>
public class PaymentServices {

    private static final String CLIENT_ID = "AfntxN1XzIhI0-E-yaRLHHjvJUNaFO-qX93SdCaFR5uOtJd0NCGWgrHbstoGQNsaGyxBkpy8QaIKEHSU";
    private static final String CLIENT_SECRET = "ECX9jfHMLtZBvsUK-aGtxPlQh_f6XbF818CsTxAj4egIsTcgkcsBehb4FipQfwFpH32aAAqP86St9zzx";
    private static final String MODE = "sandbox";
    private static final double EXCHANGE_RATE = 24000;

    public String authorizePayment(List<BookDTO> listBook) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectUrls();
        List<Transaction> listTransaction = getTransactionInformation(listBook);
        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                .setIntent("authorize");
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);
        System.out.println(approvedPayment);
        return getApprovalLink(approvedPayment);
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
            }
        }
        return approvalLink;
    }

    private List<Transaction> getTransactionInformation(List<BookDTO> listBook) {
        Transaction transaction = new Transaction();
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<Item>();
        double subTotal = 0;
        double ship = 0;
        double total = 0;
        for (BookDTO book : listBook) {
            double price = (double) Math.round((book.getPrice() / EXCHANGE_RATE) * 100) / 100.0;
            Item item = new Item();
            item.setCurrency("USD")
                    .setName(book.getName())
                    .setPrice(String.valueOf(price))
                    .setQuantity(String.valueOf(book.getQuantity()));
            subTotal += price*book.getQuantity();
            items.add(item);
        }
        Details details = new Details();
              if (subTotal < 15) {
            ship = 1.00;
        } else {
            ship = 0.00;
        }
        total = subTotal + ship;
        details.setShipping(String.valueOf(ship));
        details.setSubtotal(String.valueOf(subTotal));
        details.setTax("0");
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(total));
        amount.setDetails(details);
        transaction.setAmount(amount);
        transaction.setDescription("");
        itemList.setItems(items);
        transaction.setItemList(itemList);
        List<Transaction> listTransaction = new ArrayList<Transaction>();
        listTransaction.add(transaction);
        return listTransaction;
    }

    private RedirectUrls getRedirectUrls() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/BookStoreManagement/GetController");
        redirectUrls.setReturnUrl("http://localhost:8080/BookStoreManagement/ReviewPaymentController");
        return redirectUrls;
    }

    public Payment getPaymentDetails(String paymentID) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentID);
    }

    public Payment executePayment(String paymentID, String payerID) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerID);
        Payment payment = new Payment().setId(paymentID);
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, paymentExecution);
    }

    private Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        PayerInfo payerInfor = new PayerInfo();
        payerInfor.setFirstName("Thịnh")
                .setLastName("Phạm")
                .setEmail("thinhphamquoc9999@gmail.com");
        payer.setPayerInfo(payerInfor);
        return payer;
    }
}
//<<<<<<<<<<