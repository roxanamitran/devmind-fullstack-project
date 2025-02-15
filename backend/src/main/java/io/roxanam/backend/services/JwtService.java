package io.roxanam.backend.services;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.proc.SecurityContext;
import io.roxanam.backend.security.MyUserDetails;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@NoArgsConstructor
public class JwtService {
    public static final int DAY_IN_SECONDS = 3600 * 24;

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.issuer}")
    private String issuer;

    public String generate(MyUserDetails myUserDetails) {
        ImmutableSecret<SecurityContext> secret = new ImmutableSecret<>(secretKey.getBytes());
        NimbusJwtEncoder nimbusJwtEncoder = new NimbusJwtEncoder(secret);

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(DAY_IN_SECONDS))
                .subject(myUserDetails.getUsername())
                .claim("authorities", myUserDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        JwtEncoderParameters params = JwtEncoderParameters.from(header, claims);

        return nimbusJwtEncoder.encode(params).getTokenValue();
    }
}
