package com.puppyhome.backend.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装了响应报文的信息
 * 后续会使用这个类作为controller的返回值
 *
 * @author CCL
 * @since 2021年12月24日
 */
@Data
@NoArgsConstructor
public class ResponseResult<T> {

	private int code;
	private String message;
	private T data;
	/**
	 * 成功,只能是0
	 */
	private static final int SUCCESS_CODE = 200;
	public static final String SUCCESS_MESSAGE = "操作成功";
	/**
	 * 失败
	 */
	public static final int FAIL_CODE = 400;
	public static final String FAIL_MESSAGE = "操作失败";


	public static <T> ResponseResult<T> success(T data) {
		return new ResponseResult<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
	}

	public static <T> ResponseResult<T> success(String message, T data) {
		return new ResponseResult<>(SUCCESS_CODE, message, data);
	}

	public static <T> ResponseResult<T> fail() {
		return new ResponseResult<>(FAIL_CODE, FAIL_MESSAGE, null);
	}

	public static <T> ResponseResult<T> fail(String message) {
		return new ResponseResult<>(FAIL_CODE, message, null);
	}

	public static <T> ResponseResult<T> fail(int code, String message) {
		return new ResponseResult<>(code, message, null);
	}

	public ResponseResult(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

}
