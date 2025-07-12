package med.voll.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		/*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456";
		String hashedPassword = passwordEncoder.encode(rawPassword);

		System.out.println("Contraseña sin encriptar: " + rawPassword);
		System.out.println("Contraseña encriptada con BCrypt: " + hashedPassword);*/
	}

}
