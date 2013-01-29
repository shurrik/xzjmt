package com.xzjmt.tools;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;


public class StringHttpMessageConverter extends
		org.springframework.http.converter.StringHttpMessageConverter {

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException {
		FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET));
	}

	@Override
	public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
		super.setSupportedMediaTypes(supportedMediaTypes);
	}
	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return Arrays.asList(new MediaType("text", "html", DEFAULT_CHARSET),new MediaType("application", "json", DEFAULT_CHARSET));
	}

}
