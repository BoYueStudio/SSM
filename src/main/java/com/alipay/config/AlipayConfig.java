package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016100100639555";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQClYpbr9SmS+G5h84NNOO/5lNWSq+qkE2uYa9GSeS/034jRYc8T7YlqoXKPe/rIQVwrbc5ALtHF7fK5+2ZCDacVzseF6J72VORSz0njqJrGnbSoKoRuDtJZvLP3uYUOxIgnF9q+on+H1Eqw7ugX9XWGj2hQYRoXbU3f2BLiQYuPmO2r031JV1l5I/MoxW1HhBW67ZczmKiYPrdF4cAqu535skuCViarsFsh8nwz7yAGuKaEiIJn6YlMq2ocKCLhDm7GkE9NvriG5TaSanbweIVek0ushNol6HWO/MrLt2RGVRw0YRkC8oTa/zICA0cRjsdDl82QozbIBPnYDCXNRxbnAgMBAAECggEACXDIRq3W8rHFs0ewPg86+5c4Xcf8e+Mz6B53dxH938+GwtT/eha8Gl8e5/vew+GfPkdlV5qUlOTRTzZ/jtOyrvb/wrAVquALrIJyqFEujJnh/xt2NcYY0dCIwWJQqxgi4YBmFy21c+51WQY6L2vuioDTJot0btPpdxahdVLigY7UvIMRrgCYrr4fv0DckjHBTI1AizEpnHUGql1126xxQ/AV3X0KrL4nfUA+Vu038cXVmi+xbzuIgtkvUSN2kBAzsR7BKO4abliHF/ek6RW+QOYqfjw8XdANfy2rwhdIkXpKcEy2EPwvjnCx4pC/sDeowIiaK6nekes0IG2EkygQMQKBgQDOpb7yX7np5RMvm9Qr/mkTKYpi7qh/VK48LlE2eO93Td1qc53iEtg0PSgnl5+t7jpUmW6AkLyk1ZonkjQrbfwrY8njV/jX3OzDHoe0SNyxCtIOTIbxU1H4Tm/gG5ZO84ODlLJ5/JEuu3W/rkcfh2zHO9PEnVvZPIF30+BdyZMFyQKBgQDM4hlR1hfhWxNmL1sPq1mXLchoLY4LdD5a+KAOcPkLwHlDfYQLREaD3RNxVwUjeBtGWiUM7nGu24t3WGyP8injffrjmU4HxpPT1oM9O6YAoF5mEFtYSxDkFoW8YGeFoozltHmP7CdoJWeelYWqhMV7zwMmA+edVko6ZPTmSIBPLwKBgF5INveEf2Fct8U39rTfzXcFs/LxaoU10Iz6oOkWgkNPjl78QyObb3NeF80Am8USdSCWJFmaL8eRAVVemljaRHxTehA02f869/cKHeY+fBp2kmIdvk+qsTJ2Lwrz6z84UV+eX1PSzTaiJtX+oMqc0h+Qh00kcnyJ45QVYj/IMUEpAoGBALmPXZc87EvyjNNTuImsi2vcz1FrUOcW1RLXcz8hGq6GGE4D8/+wSp1LMTzJr6G8OhextTTNELFprhrdPxXwIfgkNYLFamKoe2K1Og/PYj3XASg3UEPbWOT7cQx308q4uhuhJSgwnlA48Gw0x+b0cVf82cEocDGT4Y6ZuSSILY2JAoGBAKiM0HkgzkLtSEi9ShoE/UawnecYqst63VpLdT8SMHFLpze46ygS7pIQqq/ylZPDI13cSxn0V491lzsHhWSNVH5RlI9sZUK2UUNxdMVzK98SlvfK5xx/+25geoedTmf4DmeHHyJCHuhSv5cSJ7NYG8TSjt0Hr13tz7c5vyItOQH2";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyEbCdFukAvjq/j7hWpGigF/LYyyyNbT7ud4K7Pa2q6HfUcu2YLNTSLjub8mgP2N5987ouDdkmflxpg3931MPvDj0H4jro2cHnV4VSlqZVWGQahtl4Bp1G+qOkLw3ZdrhiqIqbnJhfc4Z+9JVU5KJLE99QvjROaUTo4dIGvfI8EO7oP/CjEfD3pU8G2hkJTDLbpWsg+IzuBRMya6Jo9n+tBJdM6Sy+x26sVQCHVewY3P6SAoNt/0BDzu28zL6rwnGckX8Ks1hG88fjWRVnY/vyXk2sJQfLKUoTaeevXnSYBK1ezqL1SbRozBwXUI4EkWiuCLp4JeTBf61kcv/FZMPGwIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8088/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8088/mavenSSMR3/returnNotice.do";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

