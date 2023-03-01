package com.puppyhome.backend;

import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjMmFlMWVjOGE0Njk0OGFlYWMwOGU4MmY2YWNiNGU4YiIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3NzY0NDgwNSwiZXhwIjoxNjc3OTA0MDA1fQ.2YNxv5Cige9tsbGKCDRM5Z5vGYeRpk-gnNToiLGpYyk";
		System.out.println(jwt);
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

}
