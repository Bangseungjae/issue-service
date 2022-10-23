package com.fastcampus.userservice.config

import com.fastcampus.userservice.model.AuthToken
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Configuration
class WebConfig(
    private val authTokenResolver: AuthTokenResolver,
) : WebFluxConfigurer{

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(authTokenResolver)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .maxAge(3600)
    }

//    @Bean
//    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
//        http
//            .csrf().disable()
//            .authorizeExchange()
//            .pathMatchers("/public").permitAll()
//            .anyExchange().authenticated()
//            .and()
//            .httpBasic().and()
//            .formLogin()
//        return http.build()
//    }
}

@Component
class AuthTokenResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AuthToken::class.java)
    }

    //    supportsParameter가 true가 되면 리졸브 아규먼트가 동작한다.
    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {
        val authHeader = exchange.request.headers["Authorization"]?.first()
        checkNotNull(authHeader)

        val token = authHeader.split(" ")[1]
        return token.toMono()
    }
}

//@Component
//class AppUserDetailService(val appUserRepository: UserRepository) : ReactiveUserDetailsService {
//    override fun findByUsername(username: String?): Mono<UserDetails> {
//        val let: Customer = appUserRepository.findByEmail(username!!).let {
//            Customer(it!!)
//        }
//        return let.toMono()
//    }
//
//    class Customer(user: com.fastcampus.userservice.domain.entity.User) :
//            User(user.email, user.password, AuthorityUtils.createAuthorityList("ADMIN")), UserDetails {
//        override fun isAccountNonExpired(): Boolean {
//            return true
//        }
//
//        override fun isAccountNonLocked(): Boolean {
//            return true
//        }
//
//        override fun isCredentialsNonExpired(): Boolean {
//            return true
//        }
//
//        override fun isEnabled(): Boolean {
//            return true
//        }
//    }
//
//}

