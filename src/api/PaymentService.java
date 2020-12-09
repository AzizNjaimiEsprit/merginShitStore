package api;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;

import java.util.*;

public class PaymentService {
    Map<String, Object> params;

    public PaymentService() {
        Stripe.apiKey = "sk_test_51HoQVOCdaXOAG894ZbeM7FA6uSddSe14xHwkDQSl50ei8Lb6wm4yYNqfZJNLitwTdjNJnF6PlK40WSh6yXjnTSRB00LipmMa21";
    }


    public Customer getCustomer(String email) {
        List<Customer> customers = null;
        params = new HashMap<String, Object>();
        params.put("limit", 3);
        params.put("email", email);
        try {
            customers = Customer.list(params).getData();
            Iterator<Customer> it = customers.iterator();
            while (it.hasNext()) {
                return it.next();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String chargeCustomer(String customer_id, int amount) {
        try {
            Customer customer = Customer.retrieve(customer_id);

            params = new HashMap<String, Object>();
            params.put("amount", amount);
            params.put("currency", "usd");
            params.put("customer", customer_id);
            params.put("description", "Online Payment For BookStore");

            Map<String, Object> params2 = new HashMap<String, Object>();
            params2.put("amount", amount * -1);
            params2.put("currency", "usd");

            try {
                Charge charge = Charge.create(params);
                System.out.println(charge.getId() + charge.getStatus());
                CustomerBalanceTransaction balanceTransaction = customer.balanceTransactions().create(params2);
                return charge.getId();
            } catch (Exception e) {
                System.out.println("22" + e.getMessage());
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    public boolean checkValidCard(String name, String email, String numCard, int ExpMonth, int ExpYear) {
        Map<String, Object> retrieveParams =
                new HashMap<>();
        List<String> expandList = new ArrayList<>();
        expandList.add("sources");
        retrieveParams.put("expand", expandList);
        Customer customer = null;
        String cus_id = "";
        try {
            cus_id = this.getCustomer(email).getId();
        } catch (NullPointerException ex) {
            System.out.println("No user Found");
            return false;
        }

        try {
            customer = Customer.retrieve(
                    cus_id,
                    retrieveParams,
                    null
            );
            // Checking name
            if (!customer.getName().equals(name)) {
                System.out.println("Customer not found");
                return false;
            }
        } catch (StripeException e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> params = new HashMap<>();
            params.put("object", "card");
            params.put("limit", 1);
            Card card = (Card) customer.getSources().list(params).getData().get(0);

            int month = card.getExpMonth().intValue();
            int year = card.getExpYear().intValue();
            int last4 = Integer.parseInt(card.getLast4());
            if (numCard.length() != 16)
                return false;
            if (Integer.parseInt(numCard.substring(12)) != last4)
                return false;
            if (month != ExpMonth)
                return false;
            if (year != ExpYear)
                return false;
            return true;

        } catch (StripeException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getCharges() {
        Map<String, Object> params = new HashMap<>();
        params.put("limit", 10);

        try {
            ChargeCollection charges = Charge.list(params);
            charges.getData().forEach(charge -> System.out.println(charge.getCustomerObject().getName()));
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
