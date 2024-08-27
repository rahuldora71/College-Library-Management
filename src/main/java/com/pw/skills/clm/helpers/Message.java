package com.pw.skills.clm.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private boolean isSuccess;
    private String content;
    private String type;
}
