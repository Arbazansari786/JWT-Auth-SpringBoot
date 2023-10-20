package com.lti.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	private static final String SECRET_KEY="Arbaz";
	
    private static final int TOKEN_VALIDITY = 3600 * 5;
    
    public String generateToken(UserDetails userDetails) {

    		Map<String,Object> claims=new HashMap();
    		
    		return Jwts.builder().setClaims(claims)
    				.setSubject(userDetails.getUsername())
    				.setIssuedAt(new Date(System.currentTimeMillis()))
    				.setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY*1000))
					.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
					.compact();
    }
    
    public String getUserNameFromToken(String token) {
    	return getClaimFromToken(token,Claims::getSubject);
    }
    
    

	private <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver) {
		// TODO Auto-generated method stub
		Claims allClaimsFromToken = getAllClaimsFromToken(token);
		return claimResolver.apply(allClaimsFromToken);	
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

	}
	
	public boolean validateTokens(String token,UserDetails userDetail) {
		
		String userName = getUserNameFromToken(token);
		
		return (userName.equals(userDetail.getUsername()) && !isTokenExpired(token));
		
	}
	
	private boolean isTokenExpired(String token){
		Date expirationDate = getTokenExpirationDate(token);
		return expirationDate.before(new Date());
		
	}



	private Date getTokenExpirationDate(String token) {
		// TODO Auto-generated method stub
		
		return getClaimFromToken(token, Claims::getExpiration);
		
	}
	
}
