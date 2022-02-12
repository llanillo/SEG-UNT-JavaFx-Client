package com.seg.view.concurrent.service.blueprint;

import java.util.List;
import java.util.stream.Collectors;

import com.seg.domain.user.dto.UserBasic;
import com.seg.domain.user.fx.UserBasicFx;

import org.modelmapper.ModelMapper;

public interface UserService extends IService {
    
    default UserBasicFx convertUser(final ModelMapper modelMapper, final UserBasic userBasic){        
        return modelMapper.map(userBasic, UserBasicFx.class);
    }

    default List<UserBasicFx> convertUser(final ModelMapper modelMapper, final List<UserBasic> userBasicList){        
        return userBasicList.stream().map((a) -> convertUser(modelMapper, a)).collect(Collectors.toList());
    }
}
