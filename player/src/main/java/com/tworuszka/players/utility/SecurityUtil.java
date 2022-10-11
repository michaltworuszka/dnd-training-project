package com.tworuszka.players.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * @author Michał Tworuszka on 11.10.2022
 * @project dnd-training-project
 */
public class SecurityUtil {

    public static String usernameFromAuthHeader(String authorizationHeader) {
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("yesthisismyhandtohandencodeofsecretdonemorethanonceyesthisismyhandtohandencodeofsecretdonemorethanonceyesthisismyhandtohandencodeofsecretdonemorethanonce".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        return decodedJWT.getSubject();
    }
}
