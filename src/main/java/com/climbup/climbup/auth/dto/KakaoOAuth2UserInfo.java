package com.climbup.climbup.auth.dto;

import java.util.Map;

public class KakaoOAuth2UserInfo {
    private final Map<String, Object> attributes;
    private final Map<String, Object> properties;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        this.properties = (Map<String, Object>) attributes.get("properties");
    }

    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    public String getName() {
        if (properties == null) {
            return getId().substring(0, 4);
        }
        String nickname = (String) properties.get("nickname");
        return nickname != null ? nickname : getId().substring(0, 4);
    }

    public String getProfileImageUrl() {
        if (properties == null) {
            return getDefaultImageUrl();
        }
        String imageUrl = (String) properties.get("profile_image");
        return imageUrl != null ? imageUrl : getDefaultImageUrl();
    }

    private String getDefaultImageUrl() {
        return "https://via.placeholder.com/150?text=User";
    }
}