package com.expenditures.dao;

import com.expenditures.entity.Date;

import java.util.List;

public interface DateDAO {
    Date read(int dateID);

    Date readUsingDate(java.sql.Date date);

    List<Date> readAll();

    int insert(Date date);

    void update(Date date);

    void delete(int dateID);

    void deleteUsingDate(java.sql.Date date);
}
