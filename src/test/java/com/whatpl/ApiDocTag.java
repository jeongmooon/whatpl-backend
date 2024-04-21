package com.whatpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiDocTag {

    MEMBER("Members"),
    PROJECT("Projects"),
    ATTACHMENT("Attachments"),
	PRODUCT("Product"),
    DOMAIN("Domains");

    private final String tag;
}
