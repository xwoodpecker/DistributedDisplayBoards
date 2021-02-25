package htw.vs.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * The type Spring fox config.
 */
@Configuration
public class SpringFoxConfig {

    /**
     * The constant USER.
     */
    public static final String USER = "Users";
    /**
     * The constant BOARD.
     */
    public static final String BOARD = "Boards";
    /**
     * The constant GROUP.
     */
    public static final String GROUP = "Groups";

    /**
     * Api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(USER, "Endpoint to manage users"),
                        new Tag(BOARD, "Endpoint to manage boards"),
                        new Tag(GROUP, "Endpoint to manage groups"));
    }
}
