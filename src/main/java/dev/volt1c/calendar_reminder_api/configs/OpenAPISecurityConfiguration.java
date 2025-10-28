package dev.volt1c.calendar_reminder_api.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info =@Info(
                title = "Calendar Reminder API",
                contact = @Contact(
                        name = "volt1c",
                        email = "prgmr.kamil.pawlaczyk@gmail.com",
                        url = "https://github.com/volt1c"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                ),
                description = "Calendar API for task management."
        )
)
public class OpenAPISecurityConfiguration {}