package wooklee.koreaplaner.mappers;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import wooklee.koreaplaner.controllers.Examplexy;
import wooklee.koreaplaner.domains.User.User;
import wooklee.koreaplaner.dtos.AddUserDto;
import wooklee.koreaplaner.dtos.FindUserDto;

import java.util.List;

@Mapper
@Repository
public interface CoordinateMapper {
  void add(Examplexy examplexy);
}
