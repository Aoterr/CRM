package com.zendaimoney.utils;

import java.security.MessageDigest;

public class MD5 
{
	private final static String[] hexDigits = {
      "0", "1", "2", "3", "4", "5", "6", "7", 
      "8", "9", "a", "b", "c", "d", "e", "f"}; 

	public static String byteArrayToHexString(byte[] b){
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b){
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin){
		String resultString = null;
			try {
				resultString=new String(origin);
				MessageDigest md = MessageDigest.getInstance("MD5");
				resultString=byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
			}catch (Exception ex){}
			return resultString;
		}

	public static void main(String[] args) 
	{
		String key = "TyPay$YY#655$club#miy2(t8(";
/*		<?xml version="1.0" encoding="UTF-8"?>
		<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
		<soap:Body>
		<JFRollBack xmlns="http://tempuri.org/">
		<strRequestMsgType>JFRollBackRequest</strRequestMsgType>
		<strPartner>16749</strPartner>
		<strOutOrderId>2011050900003975</strOutOrderId>
		<strPhoneNo>18910229378</strPhoneNo>
		<JFNo>1</JFNo>
		<ProductName>card</ProductName>
		<strRequestIp>132.97.117.122</strRequestIp>
		<strRemark>�쳣����</strRemark>
		<strUserName>tycpkj</strUserName>
		<strPassWord>123456</strPassWord>
		<strSign>3C7A86F43E060E250886AA4C72AE4EA5</strSign>
		</JFRollBack>
		</soap:Body>
		</soap:Envelope>*/

//		87AF1010B0EDEA28159812C3E96F1121
		//7385E1B2448709F21B1448F18119E3D7
		String newSign = MD5.MD5Encode(
				"JFRollBackRequest" + "16749" + "2011050900003975" + "18910229378"
						+ "1" + "card" + "132.97.117.122"
						+ "�쳣����" + "tycpkj" + "123456" + key)
		.toUpperCase();
		
		String newSign1 = MD5.MD5Encode(
				"123456");
		
		System.out.println(newSign);
		System.out.println(newSign1);
	}
	
}
