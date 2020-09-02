package com.example.shardingsphere.dao.sharding;

import com.example.shardingsphere.entity.BroadTableDO;
import com.example.shardingsphere.entity.DefaultTableDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DefaultTableDOMapper {
    int deleteByPrimaryKey(Long id);
    int insertGenerator(DefaultTableDO record);
    int insert(DefaultTableDO record);

    int insertSelective(DefaultTableDO record);

    DefaultTableDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DefaultTableDO record);

    int updateByPrimaryKey(DefaultTableDO record);
}