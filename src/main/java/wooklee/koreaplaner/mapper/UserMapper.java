package wooklee.koreaplaner.mapper;


import org.apache.ibatis.annotations.Mapper;
import wooklee.koreaplaner.domain.User.User;

@Mapper
public interface UserMapper {
    User findOne(int id);
}
