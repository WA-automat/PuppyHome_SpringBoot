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
@TableName("collect")
public class Collect {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NonNull
	@TableField("userId")
	private Integer userId;

	@NonNull
	@TableField("articleId")
	private Integer articleId;

}
