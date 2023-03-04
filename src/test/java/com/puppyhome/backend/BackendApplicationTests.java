package com.puppyhome.backend;

import com.puppyhome.backend.mapper.MessageMapper;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiNDQ2ZmU3NDU5ZjM0MmIyYjcyNjVlNjQ4ZDA4Yjk4YyIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3Nzk0NTIyNiwiZXhwIjoxNjc4MjA0NDI2fQ.rtexwte43uFtvHjVBvaZ84qu91VnujJbFbSEGJaAHKY";
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

	@Autowired
	private MessageMapper messageMapper;

	@Test
	void testMessageMapper() {
		List<User> users = messageMapper.selectFromUserByToID(4);
		System.out.println(users.get(0));
	}

	@Autowired
	private ApplicationService applicationService;

	@Test
	void testApplicationService() {
		applicationService.deleteApply(4);
	}

}
