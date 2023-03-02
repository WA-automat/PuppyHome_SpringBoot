package com.puppyhome.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NonNull
	@TableField("userId")
	private Integer userId;

	@NonNull
	@TableField("name")
	private String name;

	@NonNull
	@TableField("telephone")
	private String telephone;

	@NonNull
	@TableField("description")
	private String description;

}
