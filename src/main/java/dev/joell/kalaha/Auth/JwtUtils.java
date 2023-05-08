package dev.joell.kalaha.Auth;

import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import dev.joell.kalaha.player.dtos.PlayerDto;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtils {
    private final static String SECRET = "N$%pLxD*MRV@N5i6#257*&fg&K43hWLETz7@yGDHKuhg&XSCXU2f&@jPGyAem^GE";

    public String createToken(PlayerDto player) throws Exception {
        SecretKeySpec key = new SecretKeySpec(SECRET.getBytes("UTF-8"), "HmacSHA256");
        return Jwts.builder()
                .setSubject(String.valueOf(player.id()))
                .addClaims(Map.of("username", player.username()))
                .signWith(key)
                .compact();
    }

}
