package base.service.user.model;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN;

    public static String get(Role role){
        return role.name().replaceAll("ROLE_", "");
    }
}
