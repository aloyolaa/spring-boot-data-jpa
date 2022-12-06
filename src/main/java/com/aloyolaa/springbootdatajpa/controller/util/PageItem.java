package com.aloyolaa.springbootdatajpa.controller.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageItem {

    private int number;
    private boolean isCurrent;

}
