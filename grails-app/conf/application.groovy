//	stateless chain that allows anonymous access when no token is sent. If however a token is on the request, it will be validated.
String ANONYMOUS_FILTERS = 'anonymousAuthenticationFilter,restTokenValidationFilter,restExceptionTranslationFilter,filterInvocationInterceptor'
// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'corebackend.security.SecUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName ='corebackend.security.SecUserRole'
grails.plugin.springsecurity.authority.className = 'corebackend.security.security.Role'
grails.plugin.springsecurity.requestMap.className = 'corebackend.security.Requestmap'
grails.plugin.springsecurity.securityConfigType = 'Requestmap'

//JSON credential extraction
grails.plugin.springsecurity.rest.login.useJsonCredentials = true
grails.plugin.springsecurity.rest.login.usernamePropertyName = "username"
grails.plugin.springsecurity.rest.login.passwordPropertyName = "password"
grails.plugin.springsecurity.logout.handlerNames = ['rememberMeServices','securityContextLogoutHandler', 'customLogoutHandler']
grails.plugin.springsecurity.rest.logout.endpointUrl= "/api/logout"
grails.plugin.springsecurity.useSecurityEventListener = true

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',      filters: 'none'],
        [pattern: '/**/js/**',       filters: 'none'],
        [pattern: '/**/css/**',      filters: 'none'],
        [pattern: '/**/images/**',   filters: 'none'],
        [pattern: '/**/favicon.ico', filters: 'none'],
        [pattern: '/**',filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'],
        [pattern: '/auth/success', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/authenticate/google', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/authenticate/facebook', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/callback/facebook', filters: ANONYMOUS_FILTERS],
        [pattern: '/oauth/callback/google', filters: ANONYMOUS_FILTERS],
        [pattern: '/', filters: ANONYMOUS_FILTERS],
        //Stateless chain
        [pattern: '/api/**', filters: 'JOINED_FILTERS,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter']
]

//-- use for api logout
grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        //pomitted the rest for brevity
        [pattern: '/api/logout', access: ['isAuthenticated()']]
]

grails {
    plugin {
        springsecurity {
            rest {
                token {
                    validation {
                        //user bearer token for validation
                        useBearerToken = true
                        enableAnonymousAccess = true
                    }
                    storage {
                        //user Redis to store token
                        useRedis = true
                    }
                }
            }
            providerNames = ['anonymousAuthenticationProvider','daoAuthenticationProvider'] // <10>
        }
    }
}