package com.test.util;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsRequest;
import com.aliyuncs.sms.model.v20160927.SingleSendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
public class SmsUtils {
	
	/**
	 * 封装阿里云发送短信服务 
	 * @param signName  签名模板
	 * @param templateCode 短信模板代码
	 * @param paramString 设置模板属性${name} ${word}等
	 * @param recNum 设置接受短信的号码
	 */
	public static Object[] sendSms(String signName,String templateCode,String paramString,String recNum){
		String AccessKeyId = Config.getValues("AccessKeyId");  //阿里云服务密匙ID
		String AccessKeySecret = Config.getValues("AccessKeySecret"); //阿里云服务密匙密码
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AccessKeyId,AccessKeySecret);
         try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Sms",  "sms.aliyuncs.com");
		} catch (ClientException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendSmsRequest request = new SingleSendSmsRequest();
        try {
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setParamString(paramString);
            request.setRecNum(recNum);
            SingleSendSmsResponse httpResponse = client.getAcsResponse(request);
            return new Object[]{true,"验证码发送成功"};
        } catch (ServerException e) {
            e.printStackTrace();
            return new Object[]{false,e.getErrCode()};
        }
        catch (ClientException e) {
            e.printStackTrace();
            return new Object[]{false,e.getErrCode()};
        }
	}
}
