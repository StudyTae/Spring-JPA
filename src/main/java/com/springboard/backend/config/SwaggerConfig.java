package com.springboard.backend.config;

import static java.util.Collections.singletonList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.springboard.backend.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.models.Contact;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;

@RequiredArgsConstructor
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.springboard.backend")
public class SwaggerConfig {

	@Autowired
	private TypeResolver typeResolver;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.auth-server-uri}")
    private String authServer;

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));

    }
	
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).host("localhost:8080").select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                    newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)
                    ))
                .useDefaultResponseMessages(false)
                .ignoredParameterTypes(
                    Pageable.class,
                    PagedResourcesAssembler.class,
                    AuthenticationPrincipal.class,
                        OAuth2Authentication.class
                )
                .securitySchemes(singletonList(securityScheme()))
                .securityContexts(singletonList(securityContext()))
                .apiInfo(getApiInfo());
    }
    
    
    
    private ApiInfo getApiInfo() {
        return new ApiInfo(
//            "jinhee generation Application",
        	"스웨거 테스트 프로젝트 적용중",
            "이부분은 부가적인설명입니다.",
            "API 버전부분", 
            "Terms of service", 
            null,
            "라이센스 부분",
            "http://localhost:8080",
            Collections.emptyList()
        );
      }
    
    @Bean
    public SecurityConfiguration securityConfiguration() {
      return SecurityConfigurationBuilder.builder()
          .clientId(clientId)
              .clientSecret(clientSecret)
          .scopeSeparator("password refresh_token client_credentials")
          .useBasicAuthenticationWithAccessCodeGrant(true)
          .build();
    }
    
    private SecurityScheme securityScheme() {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("/oauth/token");

        return new OAuthBuilder().name("spring_oauth")
            .grantTypes(singletonList(grantType))
            .scopes(Arrays.asList(scopes()))
            .build();
      }

      private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
            .securityReferences(
                singletonList(new SecurityReference("spring_oauth", scopes()))
            )
            .forPaths(PathSelectors.ant("/api/**"))
            .build();
      }

      private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
            new AuthorizationScope("read", "for read operations"),
            new AuthorizationScope("write", "for write operations")};
      }

    
}
