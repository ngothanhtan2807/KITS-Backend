package com.kits.ecommerce.config;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public abstract class VnPayConstant {
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "2.1.0";
    public static String vnp_TmnCode = "JSWEUT7V";

    public static String vnp_HashSecret = "UYPREPRWLPWAJNRXSOKJMEFSZAGRGQMX";

    public static String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    //    public static String vnp_BankCode = "NCB";
    public static String vnp_CurrCode = "VND";
    //    public static String vnp_IpAddr = "0:0:0:0:0:0:0:1";
    public static String vnp_Locale = "vn";
    public static String vnp_ReturnUrl = "https://kits-frontend-sbcs-group-2.vercel.app/checkout/success";

}