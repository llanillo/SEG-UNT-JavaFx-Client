package com.seg.view.controller.login.register;

import javafx.event.ActionEvent;

public interface RegisterControl {

    void backBtnClick(final ActionEvent event);

    void resetBtnClick(final ActionEvent event);

    void failedRegister(final String message);
}
