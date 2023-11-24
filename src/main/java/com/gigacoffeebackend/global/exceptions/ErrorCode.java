package com.gigacoffeebackend.global.exceptions;


import lombok.Getter;

@Getter
public enum ErrorCode {
    SERVER_ERROR(500, "S99", "Something went wrong"),

    INVALID_INPUT(400, "I00", ""),
    INVALID_REQUEST(400, "I01", "요청을 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "I02", "존재하지 않는 HttpMethod입니다."),

    USER_NOT_FOUND(404, "U00", "유저가 존재하지 않습니다."),
    USER_DUPLIACTED(400, "U01", "유저의 소셜 아이디와 닉네임이 중복되었습니다."),

    JWT_INVALID(401, "A00", "유효하지 않은 JWT 토큰입니다."),
    JWT_REFRESH_NOT_FOUND(401, "A01", "Refresh 토큰이 없습니다."),
    JWT_EXPIRED(401, "A02", "만료된 JWT 토큰입니다."),
    JWT_INVALID_REF(401, "A03", "유효하지 않은 Refresh 토큰입니다."),
    JWT_EXPIRED_REF(401, "A04", "만료된 Refresh 토큰입니다."),
    JWT_FAIL_TO_MAKE(401, "A405", "JWT 발급에 실패했습니다."),
    INVALID_AUTHORITY(401, "A10", "로그인되지 않은 사용자입니다."),
    INVALID_BEARER(401, "A11", "Bearer를 찾을 수 없습니다."),

    OAUTH_SERVICE_NOT_FOUND(400, "O00", "지원하지 않는 SNS 로그인입니다."),
    OAUTH_INVALID_TOKEN(401, "O01", "유효하지 않은 OAuth 토큰입니다."),

    STORE_NOT_FOUND(404, "S01", "찾을 수 없는 스토어입니다."),
    STORE_NOT_FOUND_ON_UPDATE(404, "S02", "업데이트할 스토어가 없습니다."),
    STORE_NOT_FOUND_ON_ADD_PRODUCT(404, "S02", "상품을 추가할 스토어가 없습니다."),
    STORE_DUPLICATED(400, "S03", "스토어 이름이 중복되었습니다."),
    STORE_DUPLICATED_ON_UPDATE(400, "S04", "스토어 이름이 중복되어 스토어 정보를 변경하지 못했습니다."),

    PRODUCT_DUPLICATE(400, "S04", "스토어에 해당 상품이 이미 추가되었습니다."),
    PRODUCT_NAME_IS_EMPTY(400, "S05", "상품명이 빈 값입니다."),
    PRODUCT_PRICE_IS_INVALID(400, "S06", "가격이 음수거나 100원 단위로 나누어 떨어지지 않습니다."),

    CATEGORY_NOT_FOUND(404, "C01", "찾을 수 없는 카테고리입니다."),
    CATEGORY_NAME_IS_NOT_ALPHABET(400, "C02", "카테고리 이름이 영어가 아닙니다."),
    CATEGORY_NOT_FOUND_IN_STORE(400, "C03", "스토어에 선택한 카테고리가 없습니다."),
    CATEGORY_IS_NOT_CHANGED(400, "C04", "카테고리가 변경되지 않았습니다.");

    private final int status;
    private final String getStatus;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.getStatus = code;
        this.message = message;
    }
}
