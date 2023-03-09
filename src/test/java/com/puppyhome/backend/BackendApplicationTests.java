package com.puppyhome.backend;

import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiNDQ2ZmU3NDU5ZjM0MmIyYjcyNjVlNjQ4ZDA4Yjk4YyIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3Nzk0NTIyNiwiZXhwIjoxNjc4MjA0NDI2fQ.rtexwte43uFtvHjVBvaZ84qu91VnujJbFbSEGJaAHKY";
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

	@Autowired
	private ApplicationService applicationService;

	@Test
	void testApplicationService() {
		applicationService.deleteApply(4);
	}

}
