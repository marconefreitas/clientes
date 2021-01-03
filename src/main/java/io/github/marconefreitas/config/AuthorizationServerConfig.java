package io.github.marconefreitas.config;

import io.github.marconefreitas.exception.UsuarioCadastradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@SuppressWarnings("SpringJavaAutowiringInspection")
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager auth;

    @Value("${security.jwt.signing-key}")
    private String signingKey;

    @Bean
    public TokenStore token(){
        return new JwtTokenStore(tokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter tokenConverter(){
        JwtAccessTokenConverter jwt = new JwtAccessTokenConverter();
        jwt.setSigningKey(signingKey);
        return jwt;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
       clients.inMemory()
               .withClient("my-angular-app")
               .secret("@321")
               .authorizedGrantTypes("password")
               .accessTokenValiditySeconds(1800);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws UsuarioCadastradoException {
        endpoints.tokenStore(token())
                .accessTokenConverter(tokenConverter())
                .authenticationManager(auth);
    }


}
