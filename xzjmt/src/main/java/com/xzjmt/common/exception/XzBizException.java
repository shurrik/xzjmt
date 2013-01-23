package com.xzjmt.common.exception;

public class XzBizException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	public XzBizException(){
		super();
	}
	public XzBizException(String msg){
		super(msg);
	}
	public XzBizException(String message, Throwable cause){
		super(message, cause);
	}
	public XzBizException(Throwable cause){
		super(cause);
	}
}
