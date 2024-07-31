package com.multi.happytails.community.model.dao;

import com.multi.happytails.community.model.dto.ChatDogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDogDAO {

    List<ChatDogDTO> findAll(String sortOrder);

    ChatDogDTO findById(Long chatdogNo);

    void delete(Long chatdogNo);

    void insert(ChatDogDTO chatdogDTO);

    public int getCurrentChatDogNo();

    void cdcommendCount(Long chatdogNo);

    List<ChatDogDTO> search(String keyword);

    void update(ChatDogDTO chatdog);


}
