package com.payment.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.payment.utils.Constants.*;
/**
 * Created by ptmind on 2017/1/5.
 */

/**
 * @author <a href="mailto:huanhuan_com@yeah.net">zhanhh</a>
 * @since 2017/1/5 - 23:45
 */
public class GlobalView {

    private static final Logger logger = LoggerFactory.getLogger(GlobalView.class);

    private String status;

    private String message;

    private Object content;

    private String errorCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public void successBack(Object object) {
        this.status = JSON_VIEW_STATUS_SUCCESS;
        this.content = object;
    }

    public void failBack(String message, String errorCode) {
        this.status = JSON_VIEW_STATUS_FAILED;
        this.errorCode = errorCode;
        this.message = message;
    }

    public void errorBack(String message, Exception e) {
        this.status = JSON_VIEW_STATUS_ERROR;
        this.message = message;

        e.printStackTrace();
        logger.error(message, e);
    }

}
