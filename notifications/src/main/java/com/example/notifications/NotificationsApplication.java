package com.example.notifications;

import com.corundumstudio.socketio.SocketIOServer;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class NotificationsApplication {



	public static void main(String[] args) {
		SpringApplication.run(NotificationsApplication.class, args);
	}


	@Configuration
//la configuration pour gérer les requêtes CORS.
// CORS est un mécanisme de sécurité qui permet de contrôler les requêtes faites par des navigateurs web depuis un domaine (origine) vers un autre.
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:3002","http://localhost:3000")
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.allowedHeaders("*")
					.allowCredentials(true);


		}
	}

}
