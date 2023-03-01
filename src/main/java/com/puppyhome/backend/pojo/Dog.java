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
@TableName(value = "dog")
public class Dog {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NonNull
	@TableField("dogName")
	private String dogName;

	@NonNull
	@TableField("photo")
	private String photo;

	@NonNull
	@TableField("gender")
	private Integer gender;

	@NonNull
	@TableField("age")
	private Double age;

	@NonNull
	@TableField("type")
	private String type;

	@NonNull
	@TableField("ownerId")
	private Integer ownerId;

}
