package com.pw.skills.clm.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BorrowBook {
    private boolean canBorrow;
    private int bookLeftTOBorrow;
}
