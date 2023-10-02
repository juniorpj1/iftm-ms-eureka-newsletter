package com.iftm.trabalho.models.dtos;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LogDTO<T> implements Serializable {

    private String action;
    private Date date = new Date();

    private T object;
    private String classType;

    public LogDTO() {
    }

    public LogDTO(String action, T object) {
        this.action = action;
        this.object = object;
        this.classType = object.getClass().toString();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogDTO<?> logDTO = (LogDTO<?>) o;
        return Objects.equals(action, logDTO.action) && Objects.equals(date, logDTO.date) && Objects.equals(object, logDTO.object) && Objects.equals(classType, logDTO.classType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, date, object, classType);
    }

    @Override
    public String toString() {
        return "LogDTO{" +
                "action='" + action + '\'' +
                ", date=" + date +
                ", object=" + object +
                ", classType='" + classType + '\'' +
                '}';
    }
}
