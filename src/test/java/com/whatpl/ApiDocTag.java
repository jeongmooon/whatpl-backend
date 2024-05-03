package com.whatpl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiDocTag {

    MEMBER("Members"),
    PROJECT("Projects"),
    ATTACHMENT("Attachments"),
    WHATPLPEOPLE("Whatpl-People"),
    DOMAIN("Domains");

    private final String tag;
}
