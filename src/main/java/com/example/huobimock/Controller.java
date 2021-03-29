package com.example.huobimock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * TODO
 *
 * @author 康绍飞
 * @date 2021/3/28 18:32
 */
@RestController
public class Controller {



    String acct = "{\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"id\": 100001,\n" +
            "      \"type\": \"spot\",\n" +
            "      \"subtype\": \"\",\n" +
            "      \"state\": \"working\"\n" +
            "    }\n" +
            "    {\n" +
            "      \"id\": 100002,\n" +
            "      \"type\": \"margin\",\n" +
            "      \"subtype\": \"btcusdt\",\n" +
            "      \"state\": \"working\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 100003,\n" +
            "      \"type\": \"otc\",\n" +
            "      \"subtype\": \"\",\n" +
            "      \"state\": \"working\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    String last = "{\n" +
            "  \"ch\": \"market.btcusdt.trade.detail\",\n" +
            "  \"status\": \"ok\",\n" +
            "  \"ts\": 1617028524985,\n" +
            "  \"tick\": {\n" +
            "    \"id\": 123719414765,\n" +
            "    \"ts\": 1617028524869,\n" +
            "    \"data\": [\n" +
            "      {\n" +
            "        \"id\": 1.2371941476524119e+26,\n" +
            "        \"ts\": 1617028524869,\n" +
            "        \"trade-id\": 102367599966,\n" +
            "        \"amount\": 0.004358,\n" +
            "        \"price\": {PRICE},\n" +
            "        \"direction\": \"sell\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "}";


    private List<String> list = new ArrayList<>();
    private volatile int i = 0;

    private BigDecimal usdt = new BigDecimal("1000.00");

    private BigDecimal btc= BigDecimal.ZERO;

    private BigDecimal curent = BigDecimal.ZERO;

    @GetMapping("/market/trade")
    public String lastTrade() throws IOException {
        String price = list.get(i++);
        curent = new BigDecimal(price);
        return last.replace("{PRICE}", price);

    }

    @GetMapping("/v1/account/accounts")
    public String account() {
        return acct;
    }

    @GetMapping("/v1/account/accounts/{acctId}/balance")
    public String bal() {
        return a.a.replace("{USDT}",usdt.toPlainString()).replace("{btc}", btc.toPlainString());
    }

    @PostMapping("/v1/order/orders/place")
    public Map<String,Object> bal(@RequestBody Map<String,Object> map) {
        if (map.get("type").equals("sell-market")) {
            usdt = usdt.add(btc.multiply(curent));
            btc = BigDecimal.ZERO;
        } else {
            BigDecimal amt = new BigDecimal((String) map.get("amount"));
            usdt = usdt.subtract(amt);
            btc = btc.add(amt.divide(curent, 4, RoundingMode.HALF_DOWN));
        }
        return new HashMap<>();
    }


    public static void main(String[] args) throws IOException {
        new Controller().lastTrade();
    }

    @PostConstruct
    public void init() throws IOException {
        //设置实时行情
        list.add("50000");
        list.add("49000");
        list.add("48000");
        list.add("47000");
        list.add("46000");
        list.add("45000");
//        list.add("49000");
//        list.add("49900");
//        list.add("49999");
//        list.add("50000");
//        list.add("51000");
//        list.add("52000");
//        list.add("53000");
//        list.add("52000");
//        list.add("51000");
    }
}
