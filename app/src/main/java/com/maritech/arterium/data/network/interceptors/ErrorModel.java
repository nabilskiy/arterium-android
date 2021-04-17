package com.maritech.arterium.data.network.interceptors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;

import retrofit2.HttpException;

public class ErrorModel {

    private boolean error;
    private String tag;
    private Errors errors;

    // validation_failed
    // server_error
    // forbidden
    // invalid_credentials
    // logged_in_from_another_device

    public boolean isError() {
        return error;
    }

    public String getTag() {
        return tag;
    }

    public Errors getErrors() {
        return errors;
    }

    public class Errors {

        private List<String> code;
        private List<String> name;
        private List<String> email;
        private List<String> phone;
        private List<String> current_password;
        private List<String> new_password;

        public List<String> getName() {
            return name;
        }

        public List<String> getEmail() {
            return email;
        }

        public List<String> getPhone() {
            return phone;
        }

        public List<String> getCurrent_password() {
            return current_password;
        }

        public List<String> getNew_password() {
            return new_password;
        }

        public List<String> getCode() {
            return code;
        }
    }

    public static String showErrorBody(Throwable throwable) {
        HttpException httpException = (HttpException) throwable;
        String result = null;
        try {
            result = httpException.response().errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result.equals(""))
            return "Ошибка сервера";
        Gson gson = new Gson();
        String errors = "";
        try {
            ErrorModel errorModel = gson.fromJson(result, ErrorModel.class);
            switch (errorModel.tag){
                case "validation_failed":// брать информацию из массива errors
                    errors = getErrors(errorModel);
                    break;
                case "server_error":
                    errors = "Ошибка сервера";
                    break;
                case "forbidden"://нет доступа к запрашиваемому контенту
                    errors = "Нет доступа к запрашиваемому контенту";
                    break;
                case "invalid_credentials"://не верный логин или пароль
                    errors = "Не верный логин или пароль";
                    break;
                case "logged_in_from_another_device":
                    errors = "Вхід був проведений за допомогою іншого пристрою";
                    break;
            }
        } catch (IllegalStateException e) {
            errors = "Ошибка данных";
        } catch (JsonSyntaxException e){
            errors = "Ошибка данных";
        }
        return errors;
    }

    private static String getErrors(ErrorModel errorModel){
        String errors = "";
        if (errorModel.getErrors() != null) {
            if (errorModel.getErrors().getCurrent_password() != null) {
                for (String errorString : errorModel.getErrors().getCurrent_password()) {
                    errors = errors + errorString + "\n";
                }
            }
            if (errorModel.getErrors().getEmail() != null) {
                for (String errorString : errorModel.getErrors().getEmail()) {
                    errors = errors + errorString + "\n";
                }
            }
            if (errorModel.getErrors().getName() != null) {
                for (String errorString : errorModel.getErrors().getName()) {
                    errors = errors + errorString + "\n";
                }
            }
            if (errorModel.getErrors().getPhone() != null) {
                for (String errorString : errorModel.getErrors().getPhone()) {
                    errors = errors + errorString + "\n";
                }
            }
            if (errorModel.getErrors().getNew_password() != null) {
                for (String errorString : errorModel.getErrors().getNew_password()) {
                    errors = errors + errorString + "\n";
                }
            }
            if (errorModel.getErrors().getCode() != null) {
                for (String errorString : errorModel.getErrors().getCode()) {
                    errors = errors + errorString + "\n";
                }
            }
        }
        return errors;
    }

}
