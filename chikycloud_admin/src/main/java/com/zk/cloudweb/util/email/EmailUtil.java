package com.zk.cloudweb.util.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.stereotype.Component;

/**
 * @author xf
 * @version 1.0
 * @date 2020/8/10 17:35
 */
@Component
public class EmailUtil {

    //邮箱服务器端口
    private static int port = 456;
    //邮箱服务器地址
    private static String hostName = "smtp.qq.com";
    //SSL加密
    private static boolean sSLOnConnect = true;
    //编码格式
    private static String charset = "utf-8";
    //发件人邮箱账号
    private static String emailFromAccount = "1696113170@qq.com";
    //发件人邮箱昵称
    private static String emailFromName = "zkCloudAmin";
    //发件人邮箱得到的授权码
    private static String authentication = "hikgvkaddyhpdhhb";

    public static void sendEmail(String emailaddress,String code){
        // 不要使用SimpleEmail,会出现乱码问题
        HtmlEmail email = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：，普通qq号只能是smtp.qq.com ；
            email.setHostName(hostName);
            //设置需要鉴权端口
            email.setSmtpPort(port);
            //开启 SSL 加密
            email.setSSLOnConnect(sSLOnConnect);
            // 字符编码集的设置
            email.setCharset(charset);
            // 收件人的邮箱
            email.addTo(emailaddress);
            // 发送人的邮箱
            email.setFrom(emailFromAccount, emailFromName);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和得到的授权码
            email.setAuthentication(emailFromAccount, authentication);
            email.setSubject("注册验证码");
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            email.setMsg("欢迎注册，您的验证码为："+code+"");
            // 发送
            email.send();
            System.out.println ( "邮件发送成功!" );
        } catch (EmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println ( "邮件发送失败!" );
        }
    }
}
