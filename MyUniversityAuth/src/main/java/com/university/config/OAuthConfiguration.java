/**
 * 
 */
package com.university.config;

import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
@PropertySource({ "classpath:application.properties" })
@PropertySource({ "classpath:jwt.yml" })
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Value("classpath:schema.sql")
	private Resource schemaScript;

	@Value("classpath:config.oauth2.privateKey")
	private String privateKey;

	@Value("classpath:config.oauth2.publicKey")
	private String publicKey;

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Autowired
    private CustomAuthenticationProvider authProvider;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// @formatter:off
      clients.inMemory()
      .withClient("clientId")
      .authorizedGrantTypes("implicit")
      .scopes("read")
      .autoApprove(true)
      .and()
      .withClient(env.getProperty("web.clientID"))
      .secret(env.getProperty("web.clientSecret"))
      .authorizedGrantTypes("password","authorization_code", "refresh_token")
      .scopes("write")
      .and()
      .withClient(env.getProperty("web.clientIDCSC"))
      .secret(env.getProperty("web.clientSecretCSC"))
      .authorizedGrantTypes("password","authorization_code", "refresh_token")
      .scopes("write");
      
/*        clients.jdbc(dataSource())
               .withClient("clientId")
               .authorizedGrantTypes("implicit")
               .scopes("read")
               .autoApprove(true)
               .and()
               .withClient(env.getProperty("web.clientID"))
               .secret(env.getProperty("web.clientSecret"))
               .authorizedGrantTypes("password","authorization_code", "refresh_token")
               .scopes("write");
*/
     // @formatter:on
	}

	/*@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);
	}*/
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtTokenEnhancer()));
		endpoints.tokenStore(tokenStore()).tokenEnhancer(tokenEnhancerChain).authenticationManager(authenticationManager);
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(final DataSource dataSource) {
		final DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		/* initializer.setDatabasePopulator(databasePopulator()); */
		return initializer;
	}

	/*private DatabasePopulator databasePopulator() {
		final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(schemaScript);
		return populator;
	}*/

	@Bean
	public DataSource dataSource() {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("db.driver"));
		dataSource.setUrl(env.getProperty("db.url"));
		dataSource.setUsername(env.getProperty("db.username"));
		dataSource.setPassword(env.getProperty("db.password"));
		return dataSource;
	}

	@Bean
	public TokenStore tokenStore() {
		// return new JdbcTokenStore(dataSource());
		return new JwtTokenStore(jwtTokenEnhancer());
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		//converter.setVerifierKey(publicKey);
		return converter;
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new RoleBasedTokenEnhancer();
	}
}
