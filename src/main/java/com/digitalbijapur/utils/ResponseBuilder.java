/**
 * Digital Bijapur.
 */
package com.digitalbijapur.utils;

/**
 * @author GURUNAIK
 *
 */
public class ResponseBuilder {
	StringBuilder mResponse;
	private final String BEGIN = "{";
	private final String END = "\n}";
	public ResponseBuilder() {
		mResponse = new StringBuilder();
		mResponse.append(BEGIN);
	}
	
	public void addKeyValuePair(String key, String value) {
		if(null != mResponse) {
			mResponse.append(",\n\""+key+"\":\""+value+"\"");
		}
	}
	
	public String finilizeResponse() {
		if(null != mResponse) {
			mResponse.append(END);
			return mResponse.toString().replaceFirst(",", "");
		}
		throw new NullPointerException(); 
	}
}
