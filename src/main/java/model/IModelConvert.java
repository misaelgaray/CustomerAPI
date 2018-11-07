package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IModelConvert {
    void convert(ResultSet resultSet) throws SQLException;
}
