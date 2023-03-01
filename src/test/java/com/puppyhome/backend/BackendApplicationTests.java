package com.puppyhome.backend;

import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = JwtUtil.createJWT("2");
		System.out.println(jwt);
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

}
