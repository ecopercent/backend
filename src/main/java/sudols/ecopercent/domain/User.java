package sudols.ecopercent.domain;


public class User {
    private Long user_id;
    private String nickname;
    private String name;
    private String email;
    private String profile_image;
    private Long title_tumbler_id;
    private Long title_ecobag_id;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public Long getTitle_tumbler_id() {
        return title_tumbler_id;
    }

    public void setTitle_tumbler_id(Long title_tumbler_id) {
        this.title_tumbler_id = title_tumbler_id;
    }

    public Long getTitle_ecobag_id() {
        return title_ecobag_id;
    }

    public void setTitle_ecobag_id(Long title_ecobag_id) {
        this.title_ecobag_id = title_ecobag_id;
    }
}
