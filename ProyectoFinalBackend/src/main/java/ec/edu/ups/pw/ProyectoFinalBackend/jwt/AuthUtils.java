package ec.edu.ups.pw.ProyectoFinalBackend.jwt;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Base64;
import org.joda.time.DateTime;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ec.edu.ups.pw.ProyectoFinalBackend.model.User;

public final class AuthUtils {
	
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	private static final String TOKEN_SECRET = generateSecret();
	public static final String AUTH_HEADER_KEY = "Authorization";
	
	private static String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        return Base64.getEncoder().encodeToString(sharedSecret);
    }
	
	public static String getSerializedToken(String authHeader) {
        return authHeader.split(" ")[1];
    }
	
	public static JWTClaimsSet decodeToken(String authHeader) throws ParseException, JOSEException {
		SignedJWT signedJWT = SignedJWT.parse(getSerializedToken(authHeader));
		if(signedJWT.verify(new MACVerifier(TOKEN_SECRET))) {
			return signedJWT.getJWTClaimsSet();
		} else {
			throw new JOSEException("Signature verification failed");
		}
	}
	
	public static String getSubject(String authHeader) throws ParseException, JOSEException {
		return decodeToken(authHeader).getSubject();
	}
	
	public static Token createToken(String host, User user) throws JOSEException {
		JWTClaimsSet claim = new JWTClaimsSet.Builder()
				.subject(user.getEmail())
				.issuer(host)
				.issueTime(DateTime.now().toDate())
				.expirationTime(DateTime.now().plusDays(1).toDate())
				.claim("user", user.getEmail())
				.claim("role", user.getRol())
				.build();
		JWSSigner signer = new MACSigner(TOKEN_SECRET);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claim);
		jwt.sign(signer);
		return new Token(jwt.serialize());
	}
}
