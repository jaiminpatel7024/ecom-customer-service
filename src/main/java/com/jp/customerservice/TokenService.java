package com.jp.customerservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TokenService {

    private static final Logger log = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    TokenRepository tokenRepository;

    public Token generateToken(String username)
    {
        log.info("Token generation called for username : {}.", username);
        Token token = new Token();
        token.setUsername(username);
        token.setStatus("valid");
        token.setTokenId(String.valueOf(new Random().nextInt(1000000)));
        token.setGenerationTime(new Date());
        tokenRepository.save(token);

        log.info("Generated token for username : {} with token data : {}.", username, token);

        return token;
    }

    public boolean validateToken(String token)
    {
        log.info("Token validation called for token : {}.", token);
        String[] tokenArray = token.split(" ");
        String tokenS = tokenArray[1];

        String tokenId = tokenS.split(":")[1];
        String tokenUsername = tokenS.split(":")[0];

        if(tokenRepository.findById(tokenId).isPresent())
        {
            Token tokenObj = tokenRepository.findById(tokenId).get();
            log.info("Found token object from the database : {}.", token);

            if(!tokenObj.getUsername().equalsIgnoreCase(tokenUsername)){
                log.info("Token data does not match for username : {}.", token);
                return false;
            }
            if(!tokenObj.getStatus().equalsIgnoreCase("valid"))
            {
                log.info("Token status is not valid : {}.", token);
                return false;
            }
            log.info("Token is valid : {}.", token);
            return true;
        }
        else
        {
            return false;
        }

    }

    public void invalidateToken(String token)
    {
        log.info("Token invalidation called for token : {}.", token);
        String[] tokenArray = token.split(" ");
        String tokenS = tokenArray[1];

        String tokenId = tokenS.split(":")[1];

        if(tokenRepository.findById(tokenId).isPresent())
        {
            Token tokenObj = tokenRepository.findById(tokenId).get();
            log.info("Found token object from the database : {}.", token);
            tokenObj.setStatus("invalid");
            tokenRepository.save(tokenObj);
            log.info("Token object invalidated : {}.", token);
        }
    }


    public String getUsername(String token) {
        log.info("Get username called for token : {}.", token);
        String[] tokenArray = token.split(" ");
        String tokenS = tokenArray[1];

        if(tokenRepository.findById(tokenS).isPresent())
        {
            Token tokenObj = tokenRepository.findById(tokenS).get();
            return tokenObj.getUsername();
        }
        else
        {
            return null;
        }
    }
}
