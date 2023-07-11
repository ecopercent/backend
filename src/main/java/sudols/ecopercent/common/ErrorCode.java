package sudols.ecopercent.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // Common
    INVALID_REQUEST(HttpStatus.BAD_REQUEST.value(), "COMMON-001", "Invalid request"),

    // User
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "USER-001", "User already exists"),
    USER_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "USER-002", "Uer not exists"),
    USER_NOT_ITEM_OWNED(HttpStatus.FORBIDDEN.value(), "USER-003", "User not item owned"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT.value(), "USER-004", "Nickname already exists"),

    // Item
    ITEM_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "ITEM-001", "Item not exists"),
    ITEM_CATEGORY_NOT_EXISTS(HttpStatus.NOT_FOUND.value(), "ITEM-002", "Item category not exists"),
    TITLE_ITEM_NOT_EXISTS(HttpStatus.NO_CONTENT.value(), "ITEM-003", "Title item not exists"),
    CATEGORY_MISMATCH(HttpStatus.BAD_REQUEST.value(), "ITEM-004", "Category mismatch"),

    // Token
    INVALID_TOKEN(HttpStatus.FORBIDDEN.value(), "TOKEN-001", "Invalid token"),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN.value(), "TOKEN-002", "Expired token");

    private final Integer status;

    private final String code;

    private final String message;
}
