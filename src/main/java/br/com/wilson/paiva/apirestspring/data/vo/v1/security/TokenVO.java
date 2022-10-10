package br.com.wilson.paiva.apirestspring.data.vo.v1.security;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class TokenVO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String accessToken;
    private String refreshToken;

    public TokenVO() {
    }

    public TokenVO(
            String username,
            Boolean authenticated,
            Date created,
            Date expiration,
            String accessToken,
            String refreshToken) {
        this.username = username;
        this.authenticated = authenticated;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenVO tokenVO)) return false;

        if (getUsername() != null ? !getUsername().equals(tokenVO.getUsername()) : tokenVO.getUsername() != null)
            return false;
        if (getAuthenticated() != null ? !getAuthenticated().equals(tokenVO.getAuthenticated()) : tokenVO.getAuthenticated() != null)
            return false;
        if (getCreated() != null ? !getCreated().equals(tokenVO.getCreated()) : tokenVO.getCreated() != null)
            return false;
        if (getExpiration() != null ? !getExpiration().equals(tokenVO.getExpiration()) : tokenVO.getExpiration() != null)
            return false;
        if (getAccessToken() != null ? !getAccessToken().equals(tokenVO.getAccessToken()) : tokenVO.getAccessToken() != null)
            return false;
        return getRefreshToken() != null ? getRefreshToken().equals(tokenVO.getRefreshToken()) : tokenVO.getRefreshToken() == null;
    }

    @Override
    public int hashCode() {
        int result = getUsername() != null ? getUsername().hashCode() : 0;
        result = 31 * result + (getAuthenticated() != null ? getAuthenticated().hashCode() : 0);
        result = 31 * result + (getCreated() != null ? getCreated().hashCode() : 0);
        result = 31 * result + (getExpiration() != null ? getExpiration().hashCode() : 0);
        result = 31 * result + (getAccessToken() != null ? getAccessToken().hashCode() : 0);
        result = 31 * result + (getRefreshToken() != null ? getRefreshToken().hashCode() : 0);
        return result;
    }
}
