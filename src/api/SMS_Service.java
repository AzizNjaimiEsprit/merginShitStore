package api;

import Beans.Coupon;
import Beans.Order;
import Beans.OrderItem;
import Utility.Credentials;
import Utility.Global;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;

/**
 * @author Njaimi Med Aziz & Mahdi Riahi
 */
public class SMS_Service implements Credentials {

    NexmoClient client;

    public SMS_Service() {
        client = NexmoClient.builder()
                .apiKey(nexmo_apiKey)
                .apiSecret(nexmo_apiSecret)
                .build();
    }

    public boolean SendSMSRefund(Order order, Coupon coupon) {

        String text = "" +
                "Hi " + Global.getCurrentUser().getFullName() + " \n\n" +
                "Order updated successfully \n\n" +
                "YOUR NEW ORDER INFORMATION: \n\n";

        for (OrderItem i : order.getItems()) {
            text += i.getBook().getTitle() + "   Quantity : " + i.getQuantity() + "\n\n";
        }

        text += "Reimbursed Price : " + coupon.getAmount() + "Dt \n";
        text += "\nCoupon Code : " + coupon.getCode();
        text += "\n\n\n\nRemember that you can always check you orders status on our website www.bookstore.com.tn\n";
        SmsSubmissionResponse responses = client.getSmsClient().submitMessage(new TextMessage(
                "BookStore",
                "216" + order.getNumTel(),
                text));
        for (SmsSubmissionResponseMessage response : responses.getMessages()) {
            System.out.println(response.getStatus());
            if (response.getStatus().equals("OK"))
                return true;
            else
                return false;

        }
        return false;
    }

    public boolean SendSMSCouponGenerated(String numTel, Coupon coupon) {

        String text = "Hi " + coupon.getUser().getFullName() + "\nYou generated a payment coupon : " + coupon.getCode() + "\nAmount : " + coupon.getAmount();
        SmsSubmissionResponse responses = client.getSmsClient().submitMessage(new TextMessage(
                "BookStore",
                "216" + numTel,
                text));
        for (SmsSubmissionResponseMessage response : responses.getMessages()) {
            System.out.println(response.getStatus());
            if (response.getStatus().equals("OK"))
                return true;
            else
                return false;
        }
        return false;
    }

}
