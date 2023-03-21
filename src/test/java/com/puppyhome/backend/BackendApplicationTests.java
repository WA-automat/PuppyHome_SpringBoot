package com.puppyhome.backend;

import com.puppyhome.backend.service.apply.ApplicationService;
import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NDkyODAwOWU0MzM0ZTVlOWVlYjE0Zjg3ZWIyYTFjMSIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3OTE1Nzg3NiwiZXhwIjoxNjc5NDE3MDc2fQ.W2JCXa75em1921WVvYZzMQdUyy9ngWrB8Y4AMZROzyM";
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

	@Autowired
	private ApplicationService applicationService;

	@Test
	void testApplicationService() {
		applicationService.deleteApply(4);
	}

	@Test
	void testPredictor() throws IOException {
		Process proc = Runtime.getRuntime().exec("python ");
	}

}
