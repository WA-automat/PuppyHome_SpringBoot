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
@TableName(value = "message")
public class Message {

	@TableId(type = IdType.AUTO)
	private Integer id;

	@NonNull
	@TableField("fromId")
	private Integer fromId;

	@NonNull
	@TableField("toId")
	private Integer toId;

	@NonNull
	@TableField("dogId")
	private Integer dogId;

	@NonNull
	@TableField("state")
	private Integer state;

}
