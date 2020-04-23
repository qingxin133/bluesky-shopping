import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class ParseJwtTest {

    @Test
    public void parseJwt(){
        //基于公钥去解析jwt
        String jwt ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJjb21wYW55IjoiYmx1ZXNreSJ9.NHlemCEsdA5I9US1jeobrbUqEHnDc2fV1VdcIFNEz3p8AtIzCKeWWkkPjW6EVZbnG7RLK--gU6nnu-EDnYvxFHLnS5c4gfCuUGV39RGiFNnAG_WANOPDNLRHdgSYIV3WMdXibRfiPidX1OVisMTv8Xma6lo27o_vbtsKToLDcbPXrB41XqKGvJqYGKcifhL57uigG9qfUAqlvBc_gYBiNAg7S52FiukhpjYaVDLrfX5wBR9xQmKvqHcgdjQivJEfYsKioPqhm9t4zSkTjAAHk5cHKw9njDY0X7UIyAe8owQQhMWP-q1xz2QJudY6_NPa2yIkeGCVA4w1_6yAjktWDw";

        String publicKey ="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjwNO3wzxFyxdTJ6ik5ROShmDEGNOfFGKWumon0ENCyIVePfq61o6L0LT2RbhEADAO3d/qNEOYcogLQXxVxk0AzS5BoIl36gdgdef7kfCmolLpWAh47zijh+Y+9+b2REEJOyST2MHrx83DKOSk1KS9983GsJilF90o+MpGmD0NRrp/ds9DXE3l9HbGhM88G93mDhQrTvgue5TqavQupdj6Y1zBhe/pfxp3or1YB4vTZsWJutBpqvlQ/EGD8Dw3WWZkbiZepCBBVh6BGMy/M6YuvAwcj8b8XxLff2TigCWNVNVur9YVHQn66OTA1Jq4T3S0tDDBNsKqZwi1eZVKDtjXwIDAQAB-----END PUBLIC KEY-----";

        Jwt token = JwtHelper.decodeAndVerify(jwt, new RsaVerifier(publicKey));

        String claims = token.getClaims();

        System.out.println(claims);

    }
}
