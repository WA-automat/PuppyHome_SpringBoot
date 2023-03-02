package com.puppyhome.backend;

import com.puppyhome.backend.mapper.MessageMapper;
import com.puppyhome.backend.pojo.Dog;
import com.puppyhome.backend.pojo.User;
import com.puppyhome.backend.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.util.function.Tuple3;

import java.util.List;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void testJwtUtil() throws Exception {
		String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJjMmFlMWVjOGE0Njk0OGFlYWMwOGU4MmY2YWNiNGU4YiIsInN1YiI6Im9KWXMxNHljXzJNSUtiRTk1cnNKOWFjRVFGaWciLCJpc3MiOiJzZyIsImlhdCI6MTY3NzY0NDgwNSwiZXhwIjoxNjc3OTA0MDA1fQ.2YNxv5Cige9tsbGKCDRM5Z5vGYeRpk-gnNToiLGpYyk";
		System.out.println(jwt);
		System.out.println(JwtUtil.parseJWT(jwt).getSubject());
	}

	@Autowired
	private MessageMapper messageMapper;

	@Test
	void testMessageMapper(){
		List<User> users = messageMapper.selectFromUserByToID(4);
		System.out.println(users.get(0));
	}

}
