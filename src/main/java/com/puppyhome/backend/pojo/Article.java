package com.puppyhome.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "article")
public class Article {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@TableField(value = "userId")
	private Integer userId;

	@TableField(value = "dogId")
	private Integer dogId;

	@TableField(value = "title")
	private String title;

	@TableField(value = "publishTime")
	private Date publishTime;

	@TableField(value = "description")
	private String description;

}
