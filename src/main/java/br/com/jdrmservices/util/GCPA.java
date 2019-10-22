package br.com.jdrmservices.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GCPA {
	
	public GCPA() {
		
	}
	
	public static String getTimestampCodigo() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddHHmmss");
		Date date = new Date();
		
		return simpleDateFormat.format(date).toString();
	}
}
