package com.newness.efficient.attendance.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtil {

    public static void out(HttpServletResponse response, R r) {
        out(response, r, HttpStatus.OK.value());
    }

    public static void out(HttpServletResponse response, R r, int code) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(code);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            mapper.writeValue(response.getWriter(), r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
