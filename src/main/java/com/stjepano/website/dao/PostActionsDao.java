package com.stjepano.website.dao;

import com.stjepano.website.model.database.PostAction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Write short comment about this class/interface/whatever ... What is it's responsibility?
 */
@Mapper
public interface PostActionsDao {

    @Select("SELECT * FROM postactions")
    List<PostAction> selectAll();


}
