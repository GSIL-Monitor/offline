package common.util;

import com.ctrip.framework.foundation.Foundation;
import com.ctrip.soa.platform.basesystem.emailservice.v1.EmailServiceClient;
import com.ctrip.soa.platform.basesystem.emailservice.v1.SendEmailRequest;
import com.ctrip.soa.platform.basesystem.emailservice.v1.SendEmailResponse;
import common.log.CLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author suyong 2017/11/5 13:51
 */
public class SendMailUtils {
    private static EmailServiceClient client;
    static {
        if(Foundation.server().getEnv().isFAT()){
            client=EmailServiceClient.getInstance("http://ws.email.chnl.fat412.qa.nt.ctripcorp.com/emailservice/");
        }
        else{
            client=EmailServiceClient.getInstance();
        }
    }

    /**
     * send  a mail with normal format
     * param 接受人
     * param
     * param 发送人
     * param 发送人姓名
     * param 标题
     * param 内容
     * return 1 if succeed
     * @throws Exception
     */
    public static int sendNormalEmail(List<String> recipient,List<String> cc,String sender,String senderName,String title,String content)  {
        if(Foundation.server().getEnv().isFAT()){
            recipient=new ArrayList<>();
            recipient.add("suyong@ctrip.com");
            recipient.add("quanliwang@ctrip.com");
            recipient.add("jian_ji@Ctrip.com");
            recipient.add("qlwang@Ctrip.com");
            cc=null;
        }
        SendEmailRequest sendEmailRequest=new SendEmailRequest();
        int appID= Integer.parseInt(Foundation.app().getAppId());
        sendEmailRequest.setAppID(appID);
        sendEmailRequest.setBodyContent(String.format("<entry><content><![CDATA[<p>%s</p>]]></content></entry>",content));//ensure the html format to be supported
        sendEmailRequest.setRecipient(recipient);
        sendEmailRequest.setCc(cc);
        sendEmailRequest.setSubject(title);
        sendEmailRequest.setSender(sender);
        sendEmailRequest.setSenderName(senderName);
        sendEmailRequest.setSendCode("37050002");
        sendEmailRequest.setBodyTemplateID(37050002);
        sendEmailRequest.setCharset("gb2312");
        sendEmailRequest.setIsBodyHtml(true);
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.HOUR,1);
        sendEmailRequest.setExpiredTime(calendar);
        try {
            SendEmailResponse response=client.sendEmail(sendEmailRequest);
            if(response==null){
                return -1;
            }
            return response.getResultCode();
        }
        catch (Exception ex){
            CLogger.error("sendEmail", ex);
        }

        return  -1;
    }
}
