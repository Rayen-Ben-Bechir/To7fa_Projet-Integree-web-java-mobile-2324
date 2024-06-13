package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public class payment {
    public static void main(String[] args) {
// Set your secret key here
        Stripe.apiKey = "sk_test_51Opt80JijSe6dticBpPkJCUoAsrV09Rq7cHJgZGmAayxatS497Dy3Xbih1jjncVNiuvxK990KJPAep2AXzMAEGMo00vwQBrjQY";

        try {
// Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());
// Print other account information as needed
        } catch (StripeException e) {
            e.printStackTrace();
        }

    }
}
