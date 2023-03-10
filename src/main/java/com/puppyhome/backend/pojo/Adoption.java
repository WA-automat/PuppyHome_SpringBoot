package com.puppyhome.backend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("adoption")
public class Adoption {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NotNull
	@TableField("userId")
	private Integer userId;

	@NotNull
	@TableField("articleId")
	private Integer articleId;

}
