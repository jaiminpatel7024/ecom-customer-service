package com.jp.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    public Token generateToken(String username)
    {
        Token token = new Token();
        token.setUsername(username);
        token.setStatus("valid");
        token.setTokenId(String.valueOf(new Random().nextInt(1000000)));
        token.setGenerationTime(new Date());
        tokenRepository.save(token);

        return token;
    }

    public boolean validateToken(String token)
    {
        String[] tokenArray = token.split(" ");
        String tokenS = tokenArray[1];

        String tokenId = tokenS.split(":")[1];
        String tokenUsername = tokenS.split(":")[0];

        if(tokenRepository.findById(tokenId).isPresent())
        {
            Token tokenObj = tokenRepository.findById(tokenId).get();

            if(!tokenObj.getUsername().equalsIgnoreCase(tokenUsername)){
                return false;
            }
            if(!tokenObj.getStatus().equalsIgnoreCase("valid"))
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }

    }

    public void invalidateToken(String token)
    {
        String[] tokenArray = token.split(" ");
        String tokenS = tokenArray[1];

        String tokenId = tokenS.split(":")[1];

        if(tokenRepository.findById(tokenId).isPresent())
        {
            Token tokenObj = tokenRepository.findById(tokenId).get();
            tokenObj.setStatus("invalid");
            tokenRepository.save(tokenObj);
        }
    }


    public String getUsername(String token) {
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
