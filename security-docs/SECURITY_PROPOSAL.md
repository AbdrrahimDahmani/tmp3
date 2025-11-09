# Proposition de Sécurité pour le Système Distribué

## Vue d'ensemble
Ce document présente une architecture de sécurité complète pour sécuriser le système de microservices bancaires.

## Architecture de Sécurité Proposée

### 1. Authentification et Autorisation

#### OAuth 2.0 + OpenID Connect avec Keycloak

**Composants:**
- **Keycloak**: Serveur d'identité et de gestion d'accès
- **Spring Security OAuth2 Resource Server**: Protection des microservices
- **JWT (JSON Web Tokens)**: Tokens d'accès stateless

**Flux d'authentification:**
```
1. Client → Keycloak: Authentification (login/password)
2. Keycloak → Client: JWT Access Token + Refresh Token
3. Client → Gateway: Requête + JWT Token
4. Gateway → Microservice: Requête + JWT Token validé
5. Microservice: Validation JWT + Vérification des rôles
```

### 2. Sécurité au niveau Gateway

**Spring Cloud Gateway Security:**
- Validation JWT centralisée
- Rate limiting pour prévenir les attaques DDoS
- CORS configuration
- Request/Response filtering
- Logging des requêtes sensibles

**Configuration proposée:**
```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://keycloak:8180/realms/banking
          jwk-set-uri: http://keycloak:8180/realms/banking/protocol/openid-connect/certs
```

### 3. Sécurité Inter-Services

**Options recommandées:**

#### Option A: Service Mesh (Istio)
- mTLS (Mutual TLS) automatique entre services
- Authorization policies
- Traffic encryption
- Service-to-service authentication

#### Option B: JWT Propagation
- Propagation du JWT original via Feign
- Validation JWT dans chaque microservice
- Moins de complexité d'infrastructure

**Configuration Feign avec JWT:**
```java
@Configuration
public class FeignClientConfiguration {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String token = SecurityContextHolder.getContext()
                .getAuthentication().getCredentials().toString();
            template.header("Authorization", "Bearer " + token);
        };
    }
}
```

### 4. Gestion des Rôles et Permissions

**Rôles proposés:**
- `ROLE_ADMIN`: Accès complet au système
- `ROLE_BANKER`: Gestion des bénéficiaires et virements
- `ROLE_USER`: Consultation uniquement
- `ROLE_SYSTEM`: Pour les services internes

**Matrix de permissions:**
```
Service           | Endpoint                    | ADMIN | BANKER | USER
------------------|-----------------------------|-------|--------|------
Beneficiaire      | GET /api/beneficiaires      |   ✓   |   ✓    |  ✓
Beneficiaire      | POST /api/beneficiaires     |   ✓   |   ✓    |  ✗
Beneficiaire      | PUT /api/beneficiaires/{id} |   ✓   |   ✓    |  ✗
Beneficiaire      | DELETE /api/beneficiaires   |   ✓   |   ✗    |  ✗
Virement          | GET /api/virements          |   ✓   |   ✓    |  ✓
Virement          | POST /api/virements         |   ✓   |   ✓    |  ✗
Virement          | DELETE /api/virements       |   ✓   |   ✗    |  ✗
```

### 5. Protection des Données

#### Encryption at Rest
- Database encryption (TDE - Transparent Data Encryption)
- Encrypted configuration files
- Secrets management avec Vault ou Kubernetes Secrets

#### Encryption in Transit
- HTTPS/TLS pour toutes les communications externes
- mTLS pour les communications inter-services
- Certificate management avec Let's Encrypt ou cert-manager

#### Données sensibles
- Masquage des RIB dans les logs
- Chiffrement des données sensibles (PII)
- Audit trail pour les opérations sensibles

### 6. API Security Best Practices

**Headers de sécurité:**
```yaml
- Content-Security-Policy
- X-Content-Type-Options: nosniff
- X-Frame-Options: DENY
- X-XSS-Protection: 1; mode=block
- Strict-Transport-Security
```

**Rate Limiting:**
```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: beneficiaire-service
          filters:
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 10
                redis-rate-limiter.burstCapacity: 20
```

**Input Validation:**
- Bean Validation (JSR 303)
- Sanitization des entrées utilisateur
- Protection contre SQL Injection
- Protection contre XSS

### 7. Monitoring et Audit

**Composants:**
- **ELK Stack**: Centralisation des logs
- **Prometheus + Grafana**: Métriques de sécurité
- **Jaeger**: Distributed tracing
- **Spring Boot Actuator**: Health checks et métriques

**Événements à auditer:**
- Tentatives d'authentification (succès/échec)
- Opérations CRUD sur données sensibles
- Changements de permissions
- Accès non autorisés
- Anomalies de comportement

### 8. Configuration Kubernetes Security

**Pod Security:**
```yaml
securityContext:
  runAsNonRoot: true
  runAsUser: 1000
  capabilities:
    drop:
      - ALL
  readOnlyRootFilesystem: true
```

**Network Policies:**
```yaml
apiVersion: networking.k8s.io/v1
kind: NetworkPolicy
metadata:
  name: deny-all-ingress
spec:
  podSelector: {}
  policyTypes:
    - Ingress
```

**Secrets Management:**
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: db-credentials
type: Opaque
data:
  username: base64encoded
  password: base64encoded
```

## Plan d'Implémentation

### Phase 1: Fondations (2-3 semaines)
1. Déployer Keycloak
2. Configurer les realms, clients et rôles
3. Implémenter la sécurité au niveau Gateway
4. Ajouter Spring Security à tous les microservices

### Phase 2: Sécurité Inter-Services (1-2 semaines)
1. Implémenter JWT propagation via Feign
2. Configurer mTLS (optionnel)
3. Tests de sécurité inter-services

### Phase 3: Encryption et Protection (1 semaine)
1. HTTPS/TLS pour toutes les communications
2. Encryption des secrets
3. Database encryption

### Phase 4: Monitoring et Audit (1 semaine)
1. Déployer ELK Stack
2. Configurer les audit logs
3. Dashboards de sécurité

## Dépendances Maven à ajouter

```xml
<!-- Spring Security OAuth2 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

<!-- JWT -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-oauth2-jose</artifactId>
</dependency>

<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

## Exemple de Configuration de Sécurité

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER", "BANKER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("BANKER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("BANKER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            )
            .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
    
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = 
            new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        
        JwtAuthenticationConverter authenticationConverter = 
            new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(
            grantedAuthoritiesConverter);
        
        return authenticationConverter;
    }
}
```

## Tests de Sécurité Recommandés

1. **Penetration Testing**: OWASP ZAP, Burp Suite
2. **Dependency Scanning**: OWASP Dependency Check
3. **Static Analysis**: SonarQube avec règles de sécurité
4. **Container Scanning**: Trivy, Clair
5. **Secret Scanning**: GitGuardian, TruffleHog

## Conclusion

Cette architecture de sécurité multicouche assure:
- ✅ Authentification robuste avec OAuth2/OIDC
- ✅ Authorization fine-grained basée sur les rôles
- ✅ Protection des communications (TLS/mTLS)
- ✅ Protection des données (encryption)
- ✅ Audit et monitoring complets
- ✅ Conformité aux standards de sécurité bancaire

## Références

- [OAuth 2.0 RFC](https://tools.ietf.org/html/rfc6749)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Keycloak Documentation](https://www.keycloak.org/documentation)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
