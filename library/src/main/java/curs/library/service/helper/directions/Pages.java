package curs.library.service.helper.directions;

public enum Pages {
    LOGIN_PAGE("templates/login.html"),
    REGISTER_PAGE("templates/registration.html"),
    INDEX_PAGE("templates/index.html"),
    GENERAL_ERROR("templates/error.html"),

    ADMIN_MAIN_PAGE("templates/admin/requests.html"),
    ADMIN_REQUEST_APPROVE_PAGE("templates/admin/requestManagement.html"),
    ADMIN_BILLS_PAGE("templates/admin/fines.html"),
    ADMIN_ROOMS_PAGE("templates/admin/booksCatalog.html"),
    ADMIN_USERS_PAGE("templates/admin/userManagement.jsp"),

    ADMIN_ROOM_EDIT("templates/admin/bookEditor.html"),

    USER_MAIN_PAGE("templates/user/catalog.html"),
    USER_BILLS_PAGE("templates/user/userFines.html"),
    USER_REQUESTS_PAGE("templates/user/userRequests.html"),

    USER_ROOM("templates/user/bookInfo.html"),
    USER_ROOMS_PAGE ("templates/user/user-roomlist.html");

    private String path;

    Pages(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return path;
    }

    public String getCropPath() {
        int startIndex = "templates/".length();
        int endIndex = path.indexOf(".");
        return path.substring(startIndex, endIndex);
    }
}
