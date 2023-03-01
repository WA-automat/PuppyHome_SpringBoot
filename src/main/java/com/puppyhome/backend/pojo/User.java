package com.puppyhome.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user")
public class User {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NonNull
	@TableField("openId")
	private String openId;

	@NonNull
	@TableField("nickName")
	private String nickName;

	@TableField("avatar")
	private String Avatar;

	@NonNull
	@TableField("realName")
	private String realName;

	@NonNull
	@TableField("age")
	private Double age;

	@NonNull
	@TableField("gender")
	private Integer gender;

	@NonNull
	@TableField("telephone")
	private String telephone;

	@TableField("authentication")
	private Integer authentication;

}
